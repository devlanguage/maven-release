package org.third.spring.integration.dao.transaction;

import java.sql.Connection;

public interface TransactionDefinition {

    int PROPAGATION_REQUIRED = 0;
    int PROPAGATION_SUPPORTS = 1;
    int PROPAGATION_MANDATORY = 2;
    int PROPAGATION_REQUIRES_NEW = 3;
    int PROPAGATION_NOT_SUPPORTED = 4;
    int PROPAGATION_NEVER = 5;
    int PROPAGATION_NESTED = 6;
    /* ������룺��ǰ�������������ĸ���ĳ̶ȡ����磬��������ܷ񿴵���������δ�ύ��д���ݣ� */
    int getPropagationBehavior();

    int ISOLATION_DEFAULT = -1;
    int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;
    int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;
    int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;
    int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;
    /* ���񴫲���ͨ����һ��������ִ�е����д��붼����������������С����ǣ����һ�������������Ѿ����ڣ��м���ѡ�����ָ��һ�������Է�����ִ����Ϊ�����磬�򵥵������е������м������У����������������߹����������񣬴���һ���µ�����Spring�ṩEJB
     * CMT�г��������񴫲�ѡ�
     */
    int getIsolationLevel();

    /* ����ʱ: �����ڳ�ʱǰ�����ж�ã��Զ����ײ�����������ʩ�ع����� */
    int TIMEOUT_DEFAULT = -1;

    int getTimeout();

    /* ֻ��״̬: ֻ�������޸��κ����ݡ�ֻ��������ĳЩ����£����統ʹ��Hibernateʱ������һ�ַǳ����õ��Ż��� */
    boolean isReadOnly();

    String getName();
}

