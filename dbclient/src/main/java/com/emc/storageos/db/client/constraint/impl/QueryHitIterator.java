/*
 * Copyright (c) 2012 EMC Corporation
 * All Rights Reserved
 */

package com.emc.storageos.db.client.constraint.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.emc.storageos.db.client.impl.CompositeIndexColumnName;
import com.emc.storageos.db.client.impl.IndexColumnName;
import com.emc.storageos.db.exceptions.DatabaseException;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.query.RowQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * QueryHit iterator
 */
public abstract class QueryHitIterator<T1, T2 extends CompositeIndexColumnName>
        implements Iterator<T1> {
    private static final Logger log = LoggerFactory.getLogger(QueryHitIterator.class);

    protected RowQuery<String, T2> _query;
    protected Iterator<Column<T2>> _currentIt;

    public QueryHitIterator(RowQuery<String, T2> query) {
        _query = query;
    }

    public void prime() {
        runQuery();
    }

    protected void runQuery() {
        _currentIt = null;
        ColumnList<T2> result;
        try {
            // log.info("lbyxx1");
            result = _query.execute().getResult();
            // log.info("lbyxx2");
        } catch (final ConnectionException e) {
            // log.info("lbyxx3");
            throw DatabaseException.retryables.connectionFailed(e);
        }
        // log.info("lbyxx4 result.isEmpty={}", result.isEmpty());
        if (!result.isEmpty()) {
            _currentIt = result.iterator();
        }
    }

    @Override
    public boolean hasNext() {
        //log.info("lbyxx5 _currentIt={}", _currentIt);
        if (_currentIt == null) {
            return false;
        }
        // log.info("lbyxx5 hasNext={}", _currentIt.hasNext());
        if (_currentIt.hasNext()) {
            return true;
        }
        // log.info("lbyxx6");
        runQuery();
        return _currentIt != null;
    }

    @Override
    public T1 next() {
        if (_currentIt == null) {
            throw new NoSuchElementException();
        }
        return createQueryHit(_currentIt.next());
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    protected abstract T1 createQueryHit(Column<T2> column);
}
