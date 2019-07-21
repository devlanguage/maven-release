package org.third.message.imq.util.bean;

import java.io.Serializable;

public class NbiFault implements Serializable {
    private static final long serialVersionUID = 1L;
    private String errorMessage;
    private String messageDetail;
    
    public NbiFault(String errorMessage, String messageDetail) {
		super();
		this.errorMessage = errorMessage;
		this.messageDetail = messageDetail;
	}

	/**
     * default constructor
     *
     */
    public NbiFault() {    	
    }
    
    /**
     * constructor with error message
     * @param errMsg
     */
    public NbiFault(String errMsg) {
    	errorMessage = errMsg;
    }
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}
    
}
