package org.third.spring.integration.dao.transaction;

import org.springframework.transaction.TransactionException;


public interface SavepointManager {
    Object createSavepoint() throws TransactionException;
    void rollbackToSavepoint(Object savepoint) throws TransactionException;
    void releaseSavepoint(Object savepoint) throws TransactionException;
}
