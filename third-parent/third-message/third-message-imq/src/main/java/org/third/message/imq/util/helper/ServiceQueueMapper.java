package org.third.message.imq.util.helper;

import java.util.HashMap;
import java.util.Map;

import org.third.message.imq.util.bean.QueueName_T;
import org.third.message.imq.util.bean.ServiceName_T;

public class ServiceQueueMapper {

    static Map<ServiceName_T, Map<Integer, QueueName_T>> SERVICE_QUEUE_MAP = new HashMap<ServiceName_T, Map<Integer, QueueName_T>>();
    static {
        Map<Integer, QueueName_T> mtosiMap = new HashMap<Integer, QueueName_T>();
        Map<Integer, QueueName_T> adapterMap = new HashMap<Integer, QueueName_T>();
        Map<Integer, QueueName_T> adminMap = new HashMap<Integer, QueueName_T>();
        Map<Integer, QueueName_T> configMap = new HashMap<Integer, QueueName_T>();
        Map<Integer, QueueName_T> securityMap = new HashMap<Integer, QueueName_T>();

        adapterMap.put(QueueName_T.LOCAL_QUEUE_NO, QueueName_T.ADAPTER_SERVICE_LOCAL_QUEUE);
        adminMap.put(QueueName_T.LOCAL_QUEUE_NO, QueueName_T.ADMIN_SERVICE_LOCAL_QUEUE);
        configMap.put(QueueName_T.LOCAL_QUEUE_NO, QueueName_T.CONFIG_SERVICE_LOCAL_QUEUE);
        securityMap.put(QueueName_T.LOCAL_QUEUE_NO, QueueName_T.CONFIG_SERVICE_LOCAL_QUEUE);

        mtosiMap.put(QueueName_T.LOCAL_QUEUE_NO, QueueName_T.MTOSI_SERVICE_LOCAL_QUEUE);
        mtosiMap.put(QueueName_T.REMOTE_QUEUE_NO, QueueName_T.MTOSI_SERVICE_REMOTE_QUEUE);

        SERVICE_QUEUE_MAP.put(ServiceName_T.ADAPTER_SERVICE, adapterMap);
        SERVICE_QUEUE_MAP.put(ServiceName_T.ADMIN_SERVICE, adminMap);
        SERVICE_QUEUE_MAP.put(ServiceName_T.MTOSI_MEDIATION_SERVICE, mtosiMap);
        SERVICE_QUEUE_MAP.put(ServiceName_T.SECURITY_SERVICE, securityMap);
        SERVICE_QUEUE_MAP.put(ServiceName_T.CONFIGURATION_SERVICE, configMap);
    }

    public static QueueName_T getQueueName(ServiceName_T serviceName, Integer index) {

        Map<Integer, QueueName_T> map = SERVICE_QUEUE_MAP.get(serviceName);
        if (map != null) {
            return map.get(index);
        }
        return null;
    }

    public static Map<Integer, QueueName_T> getQueueMap(ServiceName_T serviceName) {

        return SERVICE_QUEUE_MAP.get(serviceName);
    }
}
