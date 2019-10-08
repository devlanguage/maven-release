package org.third.ssh.sshd.beans;

import org.basic.common.util.StringUtils;

/**
 * Created by Robin on 8/3/2018 5:53 PM
 */
public class SshScpResult {

    private boolean success;

    private String errorMessage;

    private Throwable caughtException;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getCaughtException() {
        return caughtException;
    }

    public void setCaughtException(Throwable caughtException) {
        this.caughtException = caughtException;
    }

    public String getAllAccumulatedErrorMessage() {
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNullOrBlank(this.errorMessage)) {
            sb.append(this.errorMessage).append(System.lineSeparator());
        }
        if(null!=this.caughtException) {
            sb.append(this.caughtException.getMessage()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "SshScpResult{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                ", caughtException=" + caughtException +
                '}';
    }
}
