package org.third.jms.util.bean;

public enum ServiceName_T {
    
	MESSAGING_SERVICE("MESSAGING"),
	ADAPTER_SERVICE("ADAPTER"),
	MTNM_MEDIATION_SERVICE("MTNM"),
	MTOSI_MEDIATION_SERVICE("MTOSI"),
	MTOSI_MODEL_SERVICE("MTOSIMODEL"),
	REPORTING_SERVICE("REPORTING"),
	PROVISIONING_SERVICE("PROVISIONING"),
	SECURITY_SERVICE("SECURITY"),
	CONFIGURATION_SERVICE("CONFIGURE"),
	SNMP_MEDIATION_SERVICE("SNMP"),
	ADMIN_SERVICE("ADMIN"),
	SYNCHRONIZE_SERVICE("SYNCHRONIZE"),
	LOG_SERVICE("LOG");
    
    private final String serviceName = this.name();
    private String briefName;
 
    private ServiceName_T(String briefName) {        
        this.briefName = briefName;
    }
    /**
     * @return get method for the field briefName
     */
    public String getBriefName() {
    
        return this.briefName;
    }
    
    /**
     * @param briefName the briefName to set
     */
    public void setBriefName(String briefName) {
    
        this.briefName = briefName;
    }
    
    /**
     * @return get method for the field serviceName
     */
    public String getServiceName() {
    
        return this.serviceName;
    }
    
//    /**
//     * @param serviceName the serviceName to set
//     */
//    public void setServiceName(String serviceName) {
//    
//        this.serviceName = serviceName;
//    } 
//    

}

