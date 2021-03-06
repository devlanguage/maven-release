package org.basic.gui.swing.common;

import java.util.Locale;
import java.util.ResourceBundle;

import org.basic.common.bean.CommonResourceBundle;

/**
 * <pre>
 *
 * </pre>
 */
public class MenuResources extends CommonResourceBundle {

    public static MenuResources getResources() {
        return getResources(null);
    }

    public static MenuResources getResources(Locale locale) {
        if (locale == null)
            locale = Locale.getDefault();
        if (resources == null || !locale.equals(resources.getLocale())) {
            ResourceBundle prb = ResourceBundle.getBundle("com.sun.messaging.jmq.admin.resources.AdminConsoleResources", locale);
            resources = new MenuResources(prb);
        }
        return resources;
    }

    private MenuResources(ResourceBundle rb) {
        super(rb);
    }

    private static MenuResources resources = null;

    public static final String M_SAMPLE_MESSAGE = "A0000";
    public static final String I_MENU_CONSOLE = "A1000";
    public static final String I_MENU_PREFERENCES = "A1001";
    public static final String I_MENU_EXIT = "A1002";
    public static final String I_MENU_EDIT = "A1003";
    public static final String I_MENU_ACTIONS = "A1004";
    public static final String I_MENU_PROPERTIES = "A1005";
    public static final String I_MENU_VIEW = "A1006";
    public static final String I_MENU_EXPAND_ALL = "A1007";
    public static final String I_MENU_COLLAPSE_ALL = "A1008";
    public static final String I_MENU_REFRESH = "A1009";
    public static final String I_MENU_HELP = "A1010";
    public static final String I_MENU_ABOUT = "A1011";
    public static final String I_MENU_ADD = "A1012";
    public static final String I_MENU_ADD_OBJSTORE = "A1013";
    public static final String I_MENU_ADD_OBJSTORE_DEST = "A1014";
    public static final String I_MENU_ADD_OBJSTORE_CF = "A1015";
    public static final String I_MENU_ADD_BROKER = "A1016";
    public static final String I_MENU_ADD_BROKER_DEST = "A1017";
    public static final String I_MENU_DELETE = "A1018";
    public static final String I_MENU_DELETE_OBJSTORE = "A1019";
    public static final String I_MENU_DELETE_OBJSTORE_DEST = "A1020";
    public static final String I_MENU_DELETE_OBJSTORE_CF = "A1021";
    public static final String I_MENU_DELETE_BROKER = "A1022";
    public static final String I_MENU_DELETE_BROKER_DEST = "A1023";
    public static final String I_MENU_CONNECT = "A1024";
    public static final String I_MENU_CONNECT_OBJSTORE = "A1025";
    public static final String I_MENU_CONNECT_BROKER = "A1026";
    public static final String I_MENU_DISCONNECT = "A1027";
    public static final String I_MENU_DISCONNECT_OBJSTORE = "A1028";
    public static final String I_MENU_DISCONNECT_BROKER = "A1029";
    public static final String I_MENU_PAUSE = "A1030";
    public static final String I_MENU_PAUSE_BROKER = "A1031";
    public static final String I_MENU_PAUSE_SERVICE = "A1032";
    public static final String I_MENU_RESUME = "A1033";
    public static final String I_MENU_RESUME_BROKER = "A1034";
    public static final String I_MENU_RESUME_SERVICE = "A1035";
    public static final String I_MENU_SHUTDOWN_BROKER = "A1036";
    public static final String I_MENU_RESTART_BROKER = "A1037";
    public static final String I_MENU_PURGE_BROKER_DEST = "A1038";
    public static final String I_CONSOLE_MNEMONIC = "A1039";
    public static final String I_PREFERENCES_MNEMONIC = "A1040";
    public static final String I_EXIT_MNEMONIC = "A1041";
    public static final String I_EDIT_MNEMONIC = "A1042";
    public static final String I_ACTIONS_MNEMONIC = "A1043";
    public static final String I_PROPERTIES_MNEMONIC = "A1044";
    public static final String I_VIEW_MNEMONIC = "A1045";
    public static final String I_EXPAND_ALL_MNEMONIC = "A1046";
    public static final String I_COLLAPSE_ALL_MNEMONIC = "A1047";
    public static final String I_REFRESH_MNEMONIC = "A1048";
    public static final String I_HELP_MNEMONIC = "A1049";
    public static final String I_ABOUT_MNEMONIC = "A1050";
    public static final String I_ADD_MNEMONIC = "A1051";
    public static final String I_DELETE_MNEMONIC = "A1052";
    public static final String I_CONNECT_MNEMONIC = "A1053";
    public static final String I_DISCONNECT_MNEMONIC = "A1054";
    public static final String I_PAUSE_MNEMONIC = "A1055";
    public static final String I_RESUME_MNEMONIC = "A1056";
    public static final String I_SHUTDOWN_MNEMONIC = "A1057";
    public static final String I_RESTART_MNEMONIC = "A1058";
    public static final String I_PURGE_MNEMONIC = "A1059";
    public static final String I_PREFERENCES_KBD_XCEL = "A1060";
    public static final String I_EXIT_KBD_XCEL = "A1061";
    public static final String I_PROPERTIES_KBD_XCEL = "A1062";
    public static final String I_EXPAND_ALL_KBD_XCEL = "A1063";
    public static final String I_COLLAPSE_ALL_KBD_XCEL = "A1064";
    public static final String I_REFRESH_KBD_XCEL = "A1065";
    public static final String I_ABOUT_KBD_XCEL = "A1066";
    public static final String I_ADD_KBD_XCEL = "A1067";
    public static final String I_DELETE_KBD_XCEL = "A1068";
    public static final String I_CONNECT_KBD_XCEL = "A1069";
    public static final String I_DISCONNECT_KBD_XCEL = "A1070";
    public static final String I_PAUSE_KBD_XCEL = "A1071";
    public static final String I_RESUME_KBD_XCEL = "A1072";
    public static final String I_SHUTDOWN_KBD_XCEL = "A1073";
    public static final String I_RESTART_KBD_XCEL = "A1074";
    public static final String I_PURGE_KBD_XCEL = "A1075";
    public static final String I_DIALOG_OK = "A1076";
    public static final String I_DIALOG_APPLY = "A1077";
    public static final String I_DIALOG_CLEAR = "A1078";
    public static final String I_DIALOG_RESET = "A1079";
    public static final String I_DIALOG_CANCEL = "A1080";
    public static final String I_DIALOG_CLOSE = "A1081";
    public static final String I_DIALOG_HELP = "A1082";
    public static final String I_DIALOG_ADD = "A1083";
    public static final String I_DIALOG_DELETE = "A1084";
    public static final String I_DIALOG_CHANGE = "A1085";
    public static final String I_DIALOG_DO_NOT_SHOW_AGAIN = "A1086";
    public static final String I_OBJSTORE_LIST = "A1087";
    public static final String I_OBJSTORE = "A1088";
    public static final String I_OBJSTORE_DEST_LIST = "A1089";
    public static final String I_OBJSTORE_DEST = "A1090";
    public static final String I_OBJSTORE_CF_LIST = "A1091";
    public static final String I_OBJSTORE_CF = "A1092";
    public static final String I_BROKER_LIST = "A1093";
    public static final String I_BROKER = "A1094";
    public static final String I_BROKER_SVC_LIST = "A1095";
    public static final String I_BROKER_SVC = "A1096";
    public static final String I_BROKER_DEST_LIST = "A1097";
    public static final String I_BROKER_DEST = "A1098";
    public static final String I_BROKER_LOG_LIST = "A1099";
    public static final String I_BROKER_LOG = "A1100";
    public static final String I_PURGE_MESSAGES = "A1101";
    public static final String I_OBJSTORE_REFRESH = "A1102";
    public static final String I_OBJSTORE_REFRESH_DEST = "A1103";
    public static final String I_OBJSTORE_REFRESH_CF = "A1104";
    public static final String I_BROKER_REFRESH = "A1105";
    public static final String I_MENU_OVERVIEW = "A1106";
    public static final String I_BROKER_NAME2 = "A1107";
    public static final String I_BROKER_HOST2 = "A1108";
    public static final String I_PRIMARY_PORT = "A1109";
    public static final String I_CONN_STATUS = "A1110";
    public static final String I_SVC_NAME = "A1111";
    public static final String I_PORT_NUMBER = "A1112";
    public static final String I_SVC_STATE = "A1113";
    public static final String I_PURGE = "A1114";
    public static final String I_MENU_PAUSE_DEST = "A1115";
    public static final String I_MENU_RESUME_DEST = "A1116";
    public static final String I_MENU_PAUSE_ALL_DESTS = "A1117";
    public static final String I_MENU_RESUME_ALL_DESTS = "A1118";
    public static final String I_PREFERENCES = "A1120";
    public static final String I_EXIT = "A1121";
    public static final String I_PROPERTIES = "A1122";
    public static final String I_EXPAND_ALL = "A1123";
    public static final String I_COLLAPSE_ALL = "A1124";
    public static final String I_REFRESH = "A1125";
    public static final String I_ABOUT = "A1126";
    public static final String I_ADD = "A1127";
    public static final String I_ADD_OBJSTORE = "A1128";
    public static final String I_ADD_OBJSTORE_DEST = "A1129";
    public static final String I_ADD_OBJSTORE_CF = "A1130";
    public static final String I_ADD_BROKER = "A1131";
    public static final String I_ADD_BROKER_DEST = "A1132";
    public static final String I_DELETE = "A1133";
    public static final String I_DELETE_OBJSTORE = "A1134";
    public static final String I_DELETE_OBJSTORE_DEST = "A1135";
    public static final String I_DELETE_OBJSTORE_CF = "A1136";
    public static final String I_DELETE_BROKER = "A1137";
    public static final String I_DELETE_BROKER_DEST = "A1138";
    public static final String I_CONNECT = "A1139";
    public static final String I_CONNECT_OBJSTORE = "A1140";
    public static final String I_CONNECT_BROKER = "A1141";
    public static final String I_DISCONNECT = "A1142";
    public static final String I_DISCONNECT_OBJSTORE = "A1143";
    public static final String I_DISCONNECT_BROKER = "A1144";
    public static final String I_PAUSE = "A1145";
    public static final String I_PAUSE_BROKER = "A1146";
    public static final String I_PAUSE_SERVICE = "A1147";
    public static final String I_RESUME = "A1148";
    public static final String I_RESUME_BROKER = "A1149";
    public static final String I_RESUME_SERVICE = "A1150";
    public static final String I_SHUTDOWN_BROKER = "A1151";
    public static final String I_RESTART_BROKER = "A1152";
    public static final String I_PURGE_BROKER_DEST = "A1153";
    public static final String I_OVERVIEW = "A1154";
    public static final String I_PAUSE_DEST = "A1155";
    public static final String I_RESUME_DEST = "A1156";
    public static final String I_PAUSE_ALL_DESTS = "A1157";
    public static final String I_RESUME_ALL_DESTS = "A1158";
    public static final String I_QUERY_BROKER = "A1159";
    public static final String I_ADMIN_CONSOLE = "A1200";
    public static final String I_QUEUE = "A1201";
    public static final String I_TOPIC = "A1202";
    public static final String I_QCF = "A1203";
    public static final String I_TCF = "A1204";
    public static final String I_NAME = "A1205";
    public static final String I_VALUE = "A1206";
    public static final String I_CONTENTS = "A1207";
    public static final String I_COUNT = "A1208";
    public static final String I_CONNECT_UPON_ADDING = "A1209";
    public static final String I_CONNECT_AFTER_UPDATES = "A1210";
    public static final String I_CONNECTED = "A1211";
    public static final String I_DISCONNECTED = "A1212";
    public static final String I_OTHER_ITEM = "A1216";
    public static final String I_FOR_EXAMPLE = "A1217";
    public static final String I_ERROR_CODE = "A1218";
    public static final String I_MEGABYTES = "A1219";
    public static final String I_KILOBYTES = "A1220";
    public static final String I_BYTES = "A1221";
    public static final String I_NO_HELP = "A1222";
    public static final String I_HELP_TEXT = "A1223";
    public static final String I_QUIT_ACCELERATOR = "A1224";
    public static final String I_ADD_ACCELERATOR = "A1225";
    public static final String I_MILLISECONDS = "A1226";
    public static final String I_SECONDS = "A1227";
    public static final String I_MINUTES = "A1228";
    public static final String I_HOURS = "A1229";
    public static final String I_DAYS = "A1230";
    public static final String I_INFORMATION_CODE = "A1231";
    public static final String I_XAQCF = "A1232";
    public static final String I_XATCF = "A1233";
    public static final String I_XACF = "A1234";
    public static final String I_CF = "A1235";
    public static final String I_OBJSTORE_DEST_PROPS = "A1300";
    public static final String I_OBJSTORE_CF_PROPS = "A1301";
    public static final String I_OBJSTORE_LOOKUP_NAME = "A1302";
    public static final String I_OBJSTORE_FACTORY_TYPE = "A1303";
    public static final String I_OBJSTORE_JNDI_INFO1 = "A1304";
    public static final String I_OBJSTORE_JNDI_INFO2 = "A1305";
    public static final String I_OBJSTORE_JNDI_INFO3 = "A1306";
    public static final String I_OBJSTORE_DEST_TYPE = "A1307";
    public static final String I_OBJSTORE_DEST_NAME = "A1308";
    public static final String I_OBJSTORE_PROPS = "A1309";
    public static final String I_OBJSTORE_NAME = "A1310";
    public static final String I_OBJSTORE_PROVIDER_URL = "A1311";
    public static final String I_OBJSTORE_JNDI_PROPS = "A1312";
    public static final String I_OBJSTORE_CONN_STATUS = "A1313";
    public static final String I_OBJSTORE_LABEL = "A1314";
    public static final String I_READONLY = "A1315";
    public static final String I_BROKER_NAME = "A1401";
    public static final String I_BROKER_USE_HOST_PORT = "A1402";
    public static final String I_BROKER_HOST = "A1403";
    public static final String I_BROKER_PORT = "A1404";
    public static final String I_BROKER_USERNAME = "A1405";
    public static final String I_BROKER_PASSWD = "A1406";
    public static final String I_SAVE_USERNAME_PASSWD = "A1407";
    public static final String I_BROKER_DEST_PROPS = "A1408";
    public static final String I_BROKER_SVC_PROPS = "A1409";
    public static final String I_BROKER_PROPS = "A1410";
    public static final String I_BROKER_INSTANCE_NAME = "A1411";
    public static final String I_BROKER_ACREATE_TOPICS = "A1412";
    public static final String I_BROKER_ACREATE_QUEUES = "A1413";
    public static final String I_BROKER_LOG_LEVEL = "A1414";
    public static final String I_BROKER_LOG_ROLLOVER_SIZE = "A1415";
    public static final String I_BROKER_LOG_ROLLOVER_INTERVAL = "A1416";
    public static final String I_BROKER_METRIC_INTERVAL = "A1417";
    public static final String I_BROKER_MAX_MSGS_IN_MEM_DSK = "A1420";
    public static final String I_BROKER_MAX_TTL_SIZE_MSGS_IN_MEM_DSK = "A1421";
    public static final String I_BROKER_MAX_MSG_SIZE = "A1422";
    public static final String I_BROKER_LABEL = "A1423";
    public static final String I_BROKER_DEST_NAME = "A1424";
    public static final String I_BROKER_DEST_TYPE = "A1425";
    public static final String I_BROKER_MSG_DELIVERY_MODEL = "A1426";
    public static final String I_BROKER_SINGLE = "A1427";
    public static final String I_BROKER_ROUNDROBIN = "A1428";
    public static final String I_BROKER_FAILOVER = "A1429";
    public static final String I_BROKER_MAX_TTL_SIZE_MSGS = "A1430";
    public static final String I_BROKER_MAX_NUM_MSGS = "A1431";
    public static final String I_BROKER_MAX_SIZE_PER_MSG = "A1432";
    public static final String I_BROKER_UNLIMITED = "A1433";
    public static final String I_BROKER_DEST_NUM_CONSUMERS = "A1434";
    public static final String I_BROKER_DEST_NUM_MSGS = "A1435";
    public static final String I_BROKER_DEST_TTL_SIZE_MSGS = "A1436";
    public static final String I_BROKER_UNLIMITED_WITH_ARG = "A1437";
    public static final String I_JAVA_VERSION = "A1438";
    public static final String I_JAVA_CLASSPATH = "A1439";
    public static final String I_VERSION = "A1440";
    public static final String I_COMPILE = "A1441";
    public static final String I_RIGHTS = "A1442";
    public static final String I_VERSION_INFO = "A1443";
    public static final String I_IMPLEMENTATION = "A1444";
    public static final String I_PROTOCOL_VERSION = "A1445";
    public static final String I_TARGET_JMS_VERSION = "A1446";
    public static final String I_RSA_CREDIT = "A1447";
    public static final String I_PATCHES = "A1448";
    public static final String BLOCK_OFF = "A1450";
    public static final String I_DYNAMIC_CAP = "A1451";
    public static final String I_STATIC_CAP = "A1452";
    public static final String I_BROKER_OFF = "A1453";
    public static final String I_BROKER_TAB_BASIC = "A1454";
    public static final String I_BROKER_TAB_LOGS = "A1455";
    public static final String I_BROKER_TAB_MSG_CAPACITY = "A1456";
    public static final String I_BROKER_VERSION_STR = "A1457";
    public static final String I_BROKER_VERSION_NOT_AVAIL = "A1458";
    public static final String I_BROKER_ACTIVE_CONSUMER = "A1459";
    public static final String I_BROKER_FAILOVER_CONSUMER = "A1460";
    public static final String I_BROKER_AUTOCREATED_ACTIVE_CONSUMER = "A1461";
    public static final String I_BROKER_AUTOCREATED_FAILOVER_CONSUMER = "A1462";
    public static final String I_BROKER_DEST_STATE = "A1463";
    public static final String I_BROKER_CUR_NUM_ACTIVE = "A1464";
    public static final String I_BROKER_CUR_NUM_FAILOVER = "A1465";
    public static final String I_BROKER_MAX_PRODUCERS = "A1466";
    public static final String I_BROKER_DEST_NUM_PRODUCERS = "A1467";
    public static final String I_BROKER_CUR_NUM_CONSUMERS = "A1468";
    public static final String I_BROKER_LIMIT_BEHAVIOR = "A1469";
    public static final String I_BROKER_USE_DMQ = "A1470";
    public static final String I_REFRESH_SVCLIST = "A1500";
    public static final String I_REFRESH_DESTLIST = "A1501";
    public static final String I_BROKER_UPDATE = "A1502";
    public static final String I_DEST_PROP_BASIC = "A1503";
    public static final String I_DEST_PROP_SUB = "A1504";
    public static final String I_MENU_QUERY_BROKER = "A1505";
    public static final String I_QUERY_BROKER_MNEMONIC = "A1506";
    public static final String I_BROKER_ALT_SHUTDOWN = "A1507";
    public static final String I_USAGE_HELP = "A1508";
    public static final String I_ARG_EXPECTED = "A1509";
    public static final String I_UNRECOGNIZED_OPT = "A1510";
    public static final String I_STATUS_RECV = "A1511";
    public static final String I_UNKNOWN_STATUS = "A1512";
    public static final String I_BUSY_WAIT_FOR_REPLY = "A1513";
    public static final String I_DELETE_DURABLE = "A1514";
    public static final String I_PURGE_DURABLE = "A1515";
    public static final String I_WARNING_CODE = "A1516";
    public static final String I_LOAD_BKR_LIST = "A1520";
    public static final String I_LOAD_OBJSTORE_LIST = "A1521";
    public static final String I_ONLINE_HELP_INIT = "A1522";
    public static final String W_SAVE_AS_CLEAR_TEXT = "A2000";
    public static final String W_OS_NOT_EDITABLE_TEXT = "A2001";
    public static final String W_BKR_NOT_EDITABLE_TEXT = "A2002";
    public static final String W_INCOMPATIBLE_OBJ = "A2003";
    public static final String W_PROVIDER_URL = "A2004";
    public static final String E_NO_LOOKUP_NAME = "A3000";
    public static final String E_NO_PROP_VALUE = "A3001";
    public static final String E_OBJSTORE_NAME_IN_USE = "A3002";
    public static final String E_PROP_VALUE_EXISTS = "A3003";
    public static final String E_NO_OBJSTORE_NAME = "A3004";
    public static final String E_NO_PROVIDER_URL = "A3005";
    public static final String E_OBJSTORE_NOT_CONNECTED = "A3006";
    public static final String E_OBJSTORE_LIST = "A3007";
    public static final String E_INSUFFICIENT_INFO = "A3008";
    public static final String E_OS_ALREADY_CONNECTED = "A3009";
    public static final String E_OS_ALREADY_DISCONNECTED = "A3010";
    public static final String E_OS_UNABLE_CONNECT = "A3011";
    public static final String E_OS_UNABLE_DISCONNECT = "A3012";
    public static final String E_DELETE_DEST_OBJ = "A3013";
    public static final String E_LOAD_OBJSTORE_LIST = "A3014";
    public static final String E_SAVE_OBJSTORE_LIST = "A3015";
    public static final String E_INVALID_VALUE = "A3016";
    public static final String E_DELETE_CF_OBJ = "A3017";
    public static final String E_NO_BROKER_NAME = "A3018";
    public static final String E_NO_BROKER_HOST_PORT = "A3019";
    public static final String E_NO_BROKER_DEST_NAME = "A3020";
    public static final String E_NO_JNDI_PROPERTY_VALUE = "A3021";
    public static final String E_CANNOT_INSTANTIATE = "A3022";
    public static final String E_OS_PROCESS = "A3023";
    public static final String E_PASSWORD = "A3024";
    public static final String E_BROKER_EXISTS = "A3025";
    public static final String E_RECONNECT = "A3026";
    public static final String E_SERVICE_PAUSE = "A3027";
    public static final String E_SERVICE_RESUME = "A3028";
    public static final String E_BROKER_PAUSE = "A3029";
    public static final String E_BROKER_RESUME = "A3030";
    public static final String E_BROKER_SHUTDOWN = "A3031";
    public static final String E_REFRESH_SVCLIST = "A3032";
    public static final String E_REFRESH_DESTLIST = "A3033";
    public static final String E_RETRIEVE_SVC = "A3034";
    public static final String E_RETRIEVE_DEST = "A3035";
    public static final String E_RETRIEVE_DUR = "A3036";
    public static final String E_RETRIEVE_OBJECT = "A3037";
    public static final String E_INVALID_PORT = "A3038";
    public static final String E_INVALID_HOSTNAME = "A3039";
    public static final String E_INVALID_LOGIN = "A3040";
    public static final String E_LOGIN_FORBIDDEN = "A3041";
    public static final String E_INVALID_PROP_NAME = "A3042";
    public static final String E_INVALID_PROP_VALUE = "A3043";
    public static final String E_NO_STATIC_PORT = "A3044";
    public static final String E_BROKER_CONNECT = "A3045";
    public static final String E_BROKER_NOT_CONNECTED = "A3046";
    public static final String E_BROKER_ERR_SHUTDOWN = "A3047";
    public static final String E_BROKER_CONN_ERROR = "A3048";
    public static final String E_BROKER_NO_MORE_DEST = "A3049";
    public static final String E_UNKNOWN_ERROR = "A3050";
    public static final String E_BROKER_ADD_BROKER = "A3051";
    public static final String E_BROKER_QUERY = "A3052";
    public static final String E_SERVICE_QUERY = "A3053";
    public static final String E_ALL_SERVICES_QUERY = "A3054";
    public static final String E_DEST_QUERY = "A3055";
    public static final String E_LOAD_BKR_LIST = "A3056";
    public static final String E_SAVE_BKR_LIST = "A3057";
    public static final String E_BROKER_DEST_DELETE = "A3058";
    public static final String E_REPLY_NOT_RECEIVED = "A3059";
    public static final String E_BROKER_DEST_PURGE = "A3060";
    public static final String E_BROKER_DESTROY_DUR = "A3061";
    public static final String E_BAD_RECV_TIMEOUT_VAL = "A3062";
    public static final String E_CONNECT_BROKER = "A3063";
    public static final String E_UPDATE_BROKER = "A3064";
    public static final String E_UPDATE_SERVICE = "A3065";
    public static final String E_RECONNECT_BROKER = "A3066";
    public static final String E_SHUTDOWN_BROKER = "A3067";
    public static final String E_RESTART_BROKER = "A3068";
    public static final String E_ADD_DEST_BROKER = "A3069";
    public static final String E_DISCONNECT_BROKER_NOT_POSSIBLE = "A3070";
    public static final String E_ADMIN_MAX_THREAD = "A3071";
    public static final String E_UPDATE_DEST = "A3072";
    public static final String E_NO_REPLY_GIVEUP = "A3073";
    public static final String E_BROKER_PURGE_DUR = "A3074";
    public static final String E_BAD_NUM_RETRIES_VAL = "A3075";
    public static final String E_DEST_PAUSE = "A3076";
    public static final String E_DEST_RESUME = "A3077";
    public static final String E_DEST_ALL_PAUSE = "A3078";
    public static final String E_DEST_ALL_RESUME = "A3079";
    public static final String E_BAD_INT_BKR_LIST_VER = "A3080";
    public static final String E_BAD_FILE_BKR_LIST_VER = "A3081";
    public static final String E_BAD_INT_OBJSTORE_LIST_VER = "A3082";
    public static final String E_BAD_FILE_OBJSTORE_LIST_VER = "A3083";
    public static final String E_ONLINE_HELP_INIT_FAILED = "A3084";
    public static final String X_SAMPLE_EXCEPTION = "A4000";
    public static final String Q_OBJSTORE_DELETE = "A5000";
    public static final String Q_DEST_OBJ_DELETE = "A5001";
    public static final String Q_LOOKUP_NAME_EXISTS = "A5002";
    public static final String Q_CF_OBJ_DELETE = "A5003";
    public static final String Q_BROKER_DELETE = "A5004";
    public static final String Q_SERVICE_PAUSE = "A5005";
    public static final String Q_SERVICE_RESUME = "A5006";
    public static final String Q_BROKER_RESUME = "A5007";
    public static final String Q_BROKER_SHUTDOWN = "A5008";
    public static final String Q_BROKER_RESTART = "A5009";
    public static final String Q_BROKER_DELETE_DEST = "A5010";
    public static final String Q_BROKER_PAUSE = "A5011";
    public static final String Q_BROKER_PURGE_DEST = "A5012";
    public static final String Q_BROKER_DELETE_DUR = "A5013";
    public static final String Q_SET_MAX_THREAD_ZERO = "A5014";
    public static final String Q_BROKER_PURGE_DUR = "A5015";
    public static final String Q_DEST_PAUSE = "A5016";
    public static final String Q_DEST_RESUME = "A5017";
    public static final String Q_DEST_PAUSE_ALL = "A5018";
    public static final String Q_DEST_RESUME_ALL = "A5019";
    public static final String S_OBJSTORE_ADD = "A6000";
    public static final String S_OBJSTORE_UPDATE = "A6001";
    public static final String S_OBJSTORE_CONNECT = "A6002";
    public static final String S_OBJSTORE_DISCONNECT = "A6003";
    public static final String S_OBJSTORE_DELETE = "A6004";
    public static final String S_OBJSTORE_DELETE_DEST = "A6005";
    public static final String S_OBJSTORE_UPDATE_DEST = "A6006";
    public static final String S_OBJSTORE_ADD_DEST = "A6007";
    public static final String S_OBJSTORE_ADD_CF = "A6008";
    public static final String S_OBJSTORE_UPDATE_CF = "A6009";
    public static final String S_OBJSTORE_DELETE_CF = "A6010";
    public static final String S_BROKER_REFRESH_SVCLIST = "A6011";
    public static final String S_BROKER_REFRESH_DESTLIST = "A6012";
    public static final String S_SERVICE_PAUSE = "A6013";
    public static final String S_SERVICE_RESUME = "A6014";
    public static final String S_BROKER_PAUSE = "A6015";
    public static final String S_BROKER_RESUME = "A6016";
    public static final String S_BROKER_SHUTDOWN = "A6017";
    public static final String S_BROKER_RESTART = "A6018";
    public static final String S_BROKER_DEST_ADD = "A6019";
    public static final String S_BROKER_DEST_DELETE = "A6020";
    public static final String S_BROKER_DEST_PURGE = "A6021";
    public static final String S_BROKER_CONNECT = "A6022";
    public static final String S_BROKER_DISCONNECT = "A6023";
    public static final String S_BROKER_UPDATE = "A6024";
    public static final String S_BROKER_DESTROY_DUR = "A6025";
    public static final String S_BROKER_UPDATE_SVC = "A6026";
    public static final String S_BROKER_UPDATE_DEST = "A6027";
    public static final String S_OS_REFRESH = "A6028";
    public static final String S_OS_DESTLIST_REFRESH = "A6029";
    public static final String S_OS_CFLIST_REFRESH = "A6030";
    public static final String S_OS_DEST_REFRESH = "A6031";
    public static final String S_OS_CF_REFRESH = "A6032";
    public static final String S_BROKER_ENTRY_UPDATE = "A6033";
    public static final String S_BROKER_REFRESH = "A6034";
    public static final String S_BROKER_PURGE_DUR = "A6035";
    public static final String S_DEST_PAUSE = "A6036";
    public static final String S_DEST_RESUME = "A6037";
    public static final String S_DEST_ALL_PAUSE = "A6038";
    public static final String S_DEST_ALL_RESUME = "A6039";

}
