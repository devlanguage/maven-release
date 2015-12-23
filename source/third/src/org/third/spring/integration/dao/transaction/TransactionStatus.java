package org.third.spring.integration.dao.transaction;

public interface TransactionStatus extends SavepointManager {
    boolean isNewTransaction();
    boolean hasSavepoint();
    void setRollbackOnly();
    boolean isRollbackOnly();   
    boolean isCompleted();
}
