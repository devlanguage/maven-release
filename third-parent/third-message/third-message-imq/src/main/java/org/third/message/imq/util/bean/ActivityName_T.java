package org.third.message.imq.util.bean;

public enum ActivityName_T {
	/** SNC operations **/
	ACTIVATE_SNC,
	CREATE_AND_ACTIVATE_SNC,
	DEACTIVATE_AND_DELETED_SNC,	
	
	/** TL operations **/
	CREATE_TOPOLOGICALLINK,
	DELETE_TOPOLOGICALLINK,
	
	/** Discovery service operation **/
	GET_ALL_CAPABILITIES,
	
	/** Fault service operations **/
    GET_ACTIVE_ALARMS,
    GET_ACTIVE_ALARMS_COUNT,
    GET_ACTIVE_ALARMS_ITERATOR,
    GET_ROOT_CAUSE_ALARMS,
    
    /** Equipment Mgr operations **/
    GET_ALL_EQUIPMENT,
    GET_ALL_EQUIPMENT_ITERATOR,
    GET_ALL_EQUIPMENT_NAMES,
    GET_ALL_EQUIPMENT_NAMES_ITERATOR,
    GET_EQUIPMENT,
    GET_ALL_SUPPORTED_PTPS,
    GET_ALL_SUPPORTED_PTPS_ITERATOR,
    GET_ALL_SUPPORTED_PTP_NAMES,
    GET_ALL_SUPPORTED_PTP_NAMES_ITERATOR,
    GET_CONTAINED_EQUIPMENT,
    
    /** Inventory Mgr operations **/
    GET_INVENTORY,
    GET_INVENTORY_ITERATOR,
    
    /** ManagedElement Mgr operations **/
    GET_ALL_ACTIVE_ALARMS,
    GET_ALL_ACTIVE_ALARMS_ITERATOR,
    GET_ALL_MANAGED_ELEMENT_NAMES,
    GET_ALL_MANAGED_ELEMENT_NAMES_ITERATOR,
    GET_ALL_MANAGED_ELEMENTS,
    GET_ALL_MANAGED_ELEMENTS_ITERATOR,
    GET_ALL_PTPS,
    GET_ALL_PTPS_ITERATOR,
    GET_ALL_PTP_NAMES,
    GET_ALL_PTP_NAMES_ITERATOR,
    GET_ALL_PTP_NAMES_WITHOUT_FTPS,
    GET_ALL_PTP_NAMES_WITHOUT_FTPS_ITERATOR,
    GET_ALL_PTPS_WITHOUT_FTPS,
    GET_ALL_PTPS_WITHOUT_FTPS_ITERATOR,
    GET_ALL_FTPS,
    GET_ALL_FTPS_ITERATOR,
    GET_ALL_FTP_NAMES,
    GET_ALL_FTP_NAMES_ITERATOR,
    GET_CONTAINING_TP_NAMES,
    GET_CONTAINING_TPS,
    GET_CONTAINING_SUBNETWORK_NAMES,
    GET_TP,
    GET_MANAGED_ELEMENT,
    GET_ALL_UNACK_ACTIVE_ALARMS,
    GET_ALL_UNACK_ACTIVE_ALARMS_ITERATOR,
    GET_CONTAINEDPOTENTIAL_CTPS,
    GET_CONTAINEDPOTENTIAL_CTPS_ITERATOR,
    GET_CONTAINEDPOTENTIAL_CTPNAMES,
    GET_CONTAINEDPOTENTIAL_CTPNAMES_ITERATOR,
    GET_CONTAINEDINUSE_CTPS,
    GET_CONTAINEDINUSE_CTPS_ITERATOR,
    GET_CONTAINEDINUSE_CTPNAMES,
    GET_CONTAINEDINUSE_CTPNAMES_ITERATOR,
    GET_CONTAINEDCURRENT_CTPS,
    GET_CONTAINEDCURRENT_CTPS_ITERATOR,
    GET_CONTAINEDCURRENT_CTPNAMES,
    GET_CONTAINEDCURRENT_CTPNAMES_ITERATOR,
    GET_ALL_CROSSCONNECTIONS,
    GET_ALL_CROSSCONNECTIONS_ITERATOR,
    GET_ALL_FIXEDCROSSCONNECTIONS,
    GET_ALL_FIXEDCROSSCONNECTIONS_ITERATOR,
    SET_TPDATA,
    CREATE_GROUPTERMINATIONPOINT,
    DELETE_GROUPTERMINATIONPOINT,
    MODIFY_GROUPTERMINATIONPOINT,
    GET_ALL_GROUPTERMINATIONPOINTS,
    GET_ALL_GROUPTERMINATIONPOINTS_ITERATOR,
    GET_GROUPTERMINATIONPOINT,
    
    GET_CONTAINEDEXISTING_CTPS,
    GET_CONTAINEDEXISTING_CTPS_ITERATOR,
    
    /** Performance Mgr operations **/
    GET_ALL_CURRENT_PM_DATA,
    GET_ALL_CURRENT_PM_DATA_ITERATOR,
    
    /** MultiLayerSubnetwork Mgr operations **/
    GET_TOPOLOGICALLINK,
    GET_ALL_TOPOLOGICALLINKS,
    GET_ALL_TOPOLOGICALLINKS_ITERATOR,
    GET_ALL_TOPOLOGICALLINK_NAMES,
    GET_ALL_TOPOLOGICALLINK_NAMES_ITERATOR,    
    GET_ALL_SUBNETWORKCONNECTIONS,
    GET_ALL_SUBNETWORKCONNECTIONS_ITERATOR,
    GET_ALL_SUBNETWORKCONNECTIONNAMES,
    GET_ALL_SUBNETWORKCONNECTIONNAMES_ITERATOR,
    GET_ALL_SUBNETWORKCONNECTIONS_WITHTP,
    GET_ALL_SUBNETWORKCONNECTIONS_WITHTP_ITERATOR,
    GET_ALL_SUBNETWORKCONNECTIONNAMES_WITHTP,
    GET_ALL_SUBNETWORKCONNECTIONNAMES_WITHTP_ITERATOR,
    GET_SNC,
    GET_SNC_BYUSERLABEL,
    GET_ROUTE,
    GET_ROUTEANDTOPOLOGICALLINKS,    
    GET_ALL_MANAGEDELEMENTSWRTMLSN,
    GET_ALL_MANAGEDELEMENTSWRTMLSN_ITERATOR,
    GET_ALL_MANAGEDELEMENTNAMESWRTMLSN,
    GET_ALL_MANAGEDELEMENTNAMESWRTMLSN_ITERATOR,    
    GET_MULTILAYERSUBNETWORK,
    GET_ASSOCIATED_TP,
    DISCOVER_ROUTE_WITHTP,
        
    /** User service operations **/
    USER_LOGIN,
    USER_LOGOUT,
    USER_CHECK, 
        
    /** Synchronize service operations **/
    SYNCHRONIZE_ME,
    SYNCHRONIZE_AUTODISCOVERY,
    SYNCHRONIZE_NBI_START,
    SYNCHRONIZE_ALL,
 
    /** Protection Mgr operations **/
    GET_ALL_PROTECTIONGROUPS,
    GET_ALL_PROTECTIONGROUPS_ITERATOR,
    GET_CONTAINING_PG_NAMES,
    GET_PROTECTIONGROUP,
    CREATE_PROTECTIONGROUP,
    DELETE_PROTECTIONGROUP,    
    /**
     * <p>
     * This service is used by the NMS to get the latest switch status on a SNC or a MSP group.
     * <p>
     * It should be noted that although the term MSP was chosen as the original specific protection
     * scheme to which the related behaviour applied was Multiplex Section Protection, the label is
     * now more generally applied to any 1+1 or 1:N Trail protection scheme.
     * </p>
     */
    RETRIEVE_SWITCH_DATA,
    /**
     * <p>
     * This service is used by the NMS to get the latest switch status on an equipment protection
     * group.<br>
     * 
     * For a retrieval of a revertive M:N group, N ESwitchData_T are returned as a result of
     * retrieveESwitchData (one for each worker equipment instance).<br>
     * 
     * For a retrieval of a non-revertive M:N group, N ESwitchData_T are returned as a result of
     * retrieveESwitchData (one for each active equipment instance).<br>
     */
    RETRIEVE_ESWITCH_DATA,
    /**
     * <p> This service is used to execute a protection switch. The protection
     * switch may be performed via a protection switch command, on a protection 
     * group or on a CTP/FTP involved in an 
     * SNCP. The NMS requests the EMS to move the traffic received from the fromTP
     * to the toTP.  The same command is used to clear all existing commands.</p>
     */
    PERFORM_PROTECTION_COMMAND,
    
}