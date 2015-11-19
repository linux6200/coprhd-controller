/*
 * Copyright (c) 2014 EMC Corporation
 * All Rights Reserved
 */
package com.emc.storageos.systemservices.impl.jobs.backupscheduler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.emc.storageos.systemservices.impl.util.ProcessOutputStream;
import com.emc.storageos.systemservices.impl.util.ProcessRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements upload protocol using FTPS
 */
public class FtpsUploader extends Uploader {
    private static final Logger log = LoggerFactory.getLogger(FtpsUploader.class);

    private final static String CONTENT_LENGTH_HEADER = "Content-Length:";
    private final static String FTPS_URL_PREFIX = "ftps://";
    private final static String FTP_URL_PREFIX = "ftp://";
    private final static int FILE_DOES_NOT_EXIST = 19;
    private static final String BACKUP_TAG_SEPERATOR = "-";

    public FtpsUploader(SchedulerConfig cfg, BackupScheduler cli) {
        super(cfg, cli);
    }

    private ProcessBuilder getBuilder() {
        boolean isExplicit = startsWithIgnoreCase(this.cfg.uploadUrl, FTPS_URL_PREFIX);

        ProcessBuilder builder = new ProcessBuilder("curl", "-sSk", "-u", String.format("%s:%s",
                this.cfg.uploadUserName, this.cfg.getUploadPassword()));
        if (!isExplicit) {
            builder.command().add("--ftp-ssl");
        }

        return builder;
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static boolean isSupported(String url) {
        return startsWithIgnoreCase(url, FTPS_URL_PREFIX) || startsWithIgnoreCase(url, FTP_URL_PREFIX);
    }

    @Override
    public Long getFileSize(String fileName) throws Exception {
        ProcessBuilder builder = getBuilder();

        builder.command().add("-I");
        builder.command().add(this.cfg.uploadUrl + fileName);

        Long length = null;

        try (ProcessRunner processor = new ProcessRunner(builder.start(), false)) {
            StringBuilder errText = new StringBuilder();
            processor.captureAllTextInBackground(processor.getStdErr(), errText);

            for (String line : processor.enumLines(processor.getStdOut())) {
                if (line.startsWith(CONTENT_LENGTH_HEADER)) {
                    String lenStr = line.substring(CONTENT_LENGTH_HEADER.length() + 1);
                    length = Long.parseLong(lenStr);
                }
            }

            int exitCode = processor.join();
            if (exitCode != 0 && exitCode != FILE_DOES_NOT_EXIST) {
                throw new IOException(errText.length() > 0 ? errText.toString() : Integer.toString(exitCode));
            }
        }

        return length;
    }

    @Override
    public OutputStream upload(String fileName, long offset) throws Exception {
        ProcessBuilder builder = getBuilder();

        // We should send a "REST offset" command, but the earliest stage we can --quote it is before PASV/EPSV
        // (then "TYPE I", "STOR ..."), which does not comply to RFC959 that saying REST should be sent
        // just before STOR.
        // Here we assume the file on server is not changed after caller determined the offset - which should be
        // the size of the file on server, so we can just do an append.
        // We'll not do additional check to see if the file on server is really <offset> long right now, because
        // even so there is still a chance someone just appended to that file after our checking, it makes no
        // difference.
        if (offset > 0) {
            builder.command().add("-a");
        }

        builder.command().add("-T");
        builder.command().add("-");
        builder.command().add(this.cfg.uploadUrl + fileName);

        return new ProcessOutputStream(builder.start());
    }

    @Override
    public List<String> listFiles() throws Exception {
        ProcessBuilder builder = getBuilder();
        builder.command().add("-l");
        builder.command().add(this.cfg.uploadUrl);

        List<String> fileList = new ArrayList<String>();
        try (ProcessRunner processor = new ProcessRunner(builder.start(), false)) {
            StringBuilder errText = new StringBuilder();
            processor.captureAllTextInBackground(processor.getStdErr(), errText);

            for (String line : processor.enumLines(processor.getStdOut())) {
                fileList.add(line);
            }

            int exitCode = processor.join();
            if (exitCode != 0) {
                throw new IOException(errText.length() > 0 ? errText.toString() : Integer.toString(exitCode));
            }
        }
        return fileList;
    }

    @Override
    public void rename(String fromFileName, String toFileName) throws Exception {
        ProcessBuilder builder = getBuilder();
        builder.command().add(this.cfg.uploadUrl);
        builder.command().add("-Q");
        builder.command().add("RNFR " + fromFileName);
        builder.command().add("-Q");
        builder.command().add("RNTO " + toFileName);

        try (ProcessRunner processor = new ProcessRunner(builder.start(), false)) {
            StringBuilder errText = new StringBuilder();
            processor.captureAllTextInBackground(processor.getStdErr(), errText);
            int exitCode = processor.join();
            if (exitCode != 0) {
                throw new IOException(errText.length() > 0 ? errText.toString() : Integer.toString(exitCode));
            }
        }
    }

    @Override
    public void markInvalidZipFile(String toUploadedFileName) {
        String noExtendFileName = toUploadedFileName.split(ScheduledBackupTag.ZIP_FILE_SURFIX)[0];
        String toUploadFilePrefix = noExtendFileName.substring(0, noExtendFileName.length() - 2);
        log.info("check with prefix  {}", toUploadFilePrefix);
        try {
            List<String> ftpFiles = this.listFiles();
            for (String file : ftpFiles) {
                if(isIncompletedFile(file,toUploadFilePrefix)) {
                    if (isFullNodeFileName(noExtendFileName) && file.equals(toUploadedFileName)) {
                        continue;
                    }
                    rename(file, ScheduledBackupTag.toInvalidFileName(file));
                }
            }

        } catch (Exception e) {
            log.warn("Mark invalide  uploaded backup zip file failed", e);

        }
    }

    private Boolean isFullNodeFileName(String noExtendFileName) {
        String[] filenames = noExtendFileName.split(BACKUP_TAG_SEPERATOR);
        String availableNodes = filenames[filenames.length - 1];
        String allNodes = filenames[filenames.length - 2];
        return allNodes.equals(availableNodes);
    }
    private Boolean isIncompletedFile(String filename,String prefix){
        Pattern pattern = Pattern.compile("^"+prefix+"-\\d"+ScheduledBackupTag.ZIP_FILE_SURFIX +"$");
        return pattern.matcher(filename).matches();
    }


}