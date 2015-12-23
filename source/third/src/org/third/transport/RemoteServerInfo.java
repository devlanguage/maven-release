package org.third.transport;

public final class RemoteServerInfo implements java.io.Serializable {
    public enum ProtocolType {
        SFTP(22), FTP(21);

        private int port;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        private ProtocolType(int defaultPort) {
            this.port = defaultPort;
        }
    }

    private static final long serialVersionUID = -501424053773398847L;

    private String userName;
    private String pwd;
    private String ipAddr;
    private int port;
    private String urlPath;
    private ProtocolType protocol;

    public RemoteServerInfo(String ipAddr, int port, String userID, String pwd, String urlPath) {
        this.userName = userID;
        this.pwd = pwd;
        this.ipAddr = ipAddr;
        this.port = port;
        this.urlPath = urlPath;
    }

    public RemoteServerInfo(String ipAddr, String port, String userID, String pwd, String urlPath) {
        this.userName = userID;
        this.pwd = pwd;
        this.ipAddr = ipAddr;
        this.port = Integer.parseInt(port);
        this.urlPath = urlPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public ProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolType protocol) {
        this.protocol = protocol;
    }

    public static boolean isUIDValid(String uid) {
        if (uid != null) {
            String temp = uid.trim();
            if (temp.length() >= 3 && temp.length() <= 32 && temp.indexOf("@") < 0 && temp.indexOf("?") < 0 && temp.indexOf("/") < 0
                    && temp.indexOf("\\") < 0 && temp.indexOf("{") < 0 && temp.indexOf("[") < 0 && temp.indexOf("(") < 0
                    && temp.indexOf(")") < 0 && temp.indexOf("]") < 0 && temp.indexOf("}") < 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPWDValid(String pwd) {
        return isUIDValid(pwd);
    }
}
