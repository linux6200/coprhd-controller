/*
 * Copyright 2017 Dell Inc. or its subsidiaries.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.emc.sa.catalog.primitives;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.springframework.beans.factory.annotation.Autowired;

import com.emc.sa.catalog.CustomServicesPrimitiveManager;
import com.emc.sa.model.dao.ModelClient;
import com.emc.storageos.db.client.DbClient;
import com.emc.storageos.db.client.constraint.NamedElementQueryResultList.NamedElement;
import com.emc.storageos.db.client.model.StringSet;
import com.emc.storageos.db.client.model.StringSetMap;
import com.emc.storageos.db.client.model.uimodels.CustomServicesDBAnsibleResource;
import com.emc.storageos.primitives.db.ansible.CustomServicesAnsiblePrimitive;
import com.emc.storageos.primitives.db.ansible.CustomServicesAnsibleResource;
import com.emc.storageos.svcs.errorhandling.resources.InternalServerErrorException;


/**
 * Data access object for Ansible primitives
 *
 */
public class CustomServicesAnsibleResourceDAO implements CustomServicesResourceDAO<CustomServicesAnsibleResource> {
    
    @Autowired
    private CustomServicesPrimitiveManager primitiveManager;
    @Autowired
    private ModelClient client;
    @Autowired 
    private DbClient dbClient;
    
    @Override 
    public String getType() {
        return CustomServicesAnsiblePrimitive.TYPE;
    }
    

    @Override
    public CustomServicesAnsibleResource getResource(URI id) {
        return CustomServicesDBHelper.getResource(CustomServicesAnsibleResource.class, CustomServicesDBAnsibleResource.class, primitiveManager, id);
    }

    @Override
    public CustomServicesAnsibleResource createResource(final String name,
            final byte[] stream, final URI parentId) {
        final StringSetMap attributes = new StringSetMap();
        attributes.put("playbooks", getPlaybooks(stream));
        return CustomServicesDBHelper.createResource(CustomServicesAnsibleResource.class, CustomServicesDBAnsibleResource.class, 
                primitiveManager, name, stream, attributes, parentId);
    }

    @Override
    public CustomServicesAnsibleResource updateResource(final URI id, final String name,final byte[] stream) {
        final StringSet playbooks = stream == null ? null : getPlaybooks(stream);
        
        final StringSetMap attributes;
        if(playbooks != null) {
            attributes = new StringSetMap();
            attributes.put("playbooks", getPlaybooks(stream));
        } else {
            attributes = null;
        }
        
        return CustomServicesDBHelper.updateResource(CustomServicesAnsibleResource.class, CustomServicesDBAnsibleResource.class, 
                primitiveManager, id, name, stream, attributes);
    }

    @Override
    public void deactivateResource(URI id) {
        CustomServicesDBHelper.deactivateResource(CustomServicesDBAnsibleResource.class, primitiveManager, client, id);
    }

    @Override
    public List<NamedElement> listResources() {
        return client.customServicesPrimitiveResources().list(CustomServicesDBAnsibleResource.class);
    }

    @Override
    public Class<CustomServicesAnsibleResource> getResourceType() {
        return CustomServicesAnsibleResource.class;
    }

    @Override
    public boolean hasResource() {
        return true;
    }

    @Override
    public List<NamedElement> listResourcesByParentId(final URI parentId) {
        return null;
    }

    
    private StringSet getPlaybooks(final byte[] archive) {
        try (final TarArchiveInputStream tarIn = new TarArchiveInputStream(
                new GzipCompressorInputStream(new ByteArrayInputStream(
                        archive)))) {
            TarArchiveEntry entry = tarIn.getNextTarEntry();
            final StringSet playbooks = new StringSet();

            while (entry != null) {
                if (entry.isFile()
                        && entry.getName().toLowerCase().endsWith(".yml")) {
                    final java.nio.file.Path playbookPath = FileSystems
                            .getDefault().getPath(entry.getName()).normalize();
                    if (null != playbookPath && playbookPath.getNameCount() >= 0)
                        playbooks.add(playbookPath.toString());
                }
                entry = tarIn.getNextTarEntry();
            }
            return playbooks;
        } catch (final IOException e) {
            throw InternalServerErrorException.internalServerErrors.genericApisvcError("Invalid ansible archive", e);
        }
    }


}