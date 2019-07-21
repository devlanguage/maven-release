package org.basic.common.util;

public class BasicException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8483693534896991855L;
    public final static int EX_IN_EMS_SYNC = -20200;
    public final static int EX_IN_NBI_SYNC = -20201;

    public enum BasicExceptionType {
        COMMUNICATION_EXCEPTION, //
        BUSINESS_EXCEPTION, //
        INTERNAL_EXCEPTION, //
        DB_EXCEPTION, //
        UNKNOWN_TYPE
    }

    public enum BasicExceptionSubType {
        // Messaging Exceptions
        MESSAGE_TIME_OUT("Message timed out"), //
        MESSAGE_UNDELIVERABLE("Unable to deliver message"), //
        JMS_CONNECTION_LOST("Connection lost to JMS Broker"), //
        WAIT_INTERRUPTED("Thread is interrupted"), //
        NOT_IMPLEMENTED("NOT_IMPLEMENTED"), //
        INTERNAL_ERROR("INTERNAL_ERROR"), //
        INVALID_INPUT("INVALID_INPUT"), //
        OBJECT_IN_USE("OBJECT_IN_USE"), //
        ENTITY_NOT_FOUND("OBJECT_IN_USE"), //
        UNABLE_TO_COMPLY("UNABLE_TO_COMPLY"), //
        USERLABEL_IN_USE("USERLABEL_IN_USE"), //
        COMM_LOSS("COMM_LOSS"), //
        UNKNOWN_TYPE("Unknown");

        private String errorMsg;

        BasicExceptionSubType(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    private BasicExceptionType type = BasicExceptionType.UNKNOWN_TYPE;

    private BasicExceptionSubType subtype = BasicExceptionSubType.UNKNOWN_TYPE;

    private String detailErrorInfo;

    public BasicException(String errorMsg) {
        super(errorMsg);
    }

    public BasicException(BasicExceptionType exType, BasicExceptionSubType exSubType) {
        super(exSubType.getErrorMsg());
        this.type = exType;
        this.subtype = exSubType;
    }

    public BasicException(BasicExceptionType exType, BasicExceptionSubType exSubType, String errorMsg) {
        super(errorMsg);
        this.type = exType;
        this.subtype = exSubType;
    }

    public BasicException(BasicExceptionType exType, Exception ex) {
        super(ex.getMessage());
        this.type = exType;
        super.fillInStackTrace();
    }

    public BasicException(BasicExceptionType exType, BasicExceptionSubType exSubType, Exception ex) {
        super(ex.getMessage());
        this.type = exType;
        this.subtype = exSubType;
        super.fillInStackTrace();
    }

    public BasicException(BasicExceptionType exType, String detailErrorInfo) {
        super(detailErrorInfo);
        this.type = exType;

    }

    public BasicException(Exception ex) {
        super(ex.getMessage());
        super.fillInStackTrace();
    }

    public BasicException(String msg, Throwable t) {
        super(msg, t);
    }

    public BasicException(BasicExceptionType exType, String msg, Throwable t) {
        super(msg, t);
        this.type = exType;
    }

    public BasicException(BasicExceptionType exType, BasicExceptionSubType exSubType, String msg, Throwable t) {
        super(msg, t);
        this.type = exType;
        this.subtype = exSubType;
    }

    public void setDetailErrorInfo(String detailErrorInfo) {
        this.detailErrorInfo = detailErrorInfo;
    }

    public void setType(BasicExceptionType nbiType) {
        this.type = nbiType;
    }

    public void setSubtype(BasicExceptionSubType nbiSubtype) {
        this.subtype = nbiSubtype;
    }

    public String getDetailErrorInfo() {
        return detailErrorInfo;
    }

    /**
     * @return the subtype
     */
    public BasicExceptionSubType getSubtype() {
        return subtype;
    }

    /**
     * @return the type
     */
    public BasicExceptionType getType() {
        return type;
    }

}
