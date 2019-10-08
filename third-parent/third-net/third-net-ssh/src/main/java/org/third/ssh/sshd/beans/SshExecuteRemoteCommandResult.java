package org.third.ssh.sshd.beans;

import org.basic.common.util.StringUtils;

public class SshExecuteRemoteCommandResult {

    private String errorMessage;
    private Integer exitStatus;
    private String standardOutputAsString;
    private String standardErrorOutputAsString;
    private Throwable caughtException;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(Integer exitStatus) {
        this.exitStatus = exitStatus;
    }

    public String getStandardOutputAsString() {
        return standardOutputAsString;
    }

    public void setStandardOutputAsString(String standardOutputAsString) {
        this.standardOutputAsString = standardOutputAsString;
    }

    public String getStandardErrorOutputAsString() {
        return standardErrorOutputAsString;
    }

    public void setStandardErrorOutputAsString(String standardErrorOutputAsString) {
        this.standardErrorOutputAsString = standardErrorOutputAsString;
    }

    public Throwable getCaughtException() {
        return caughtException;
    }

    public void setCaughtException(Throwable caughtException) {
        this.caughtException = caughtException;
    }

    public String getAllAccumulatedMessage() {
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotBlank(this.standardOutputAsString)) {
            sb.append(this.standardOutputAsString).append("\n");
        }
        String allAccumulatedErrorMessage = this.getAllAccumulatedErrorMessage();
        sb.append(allAccumulatedErrorMessage);
        return sb.toString();
    }

    public String getAllAccumulatedErrorMessage() {
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotBlank(this.errorMessage)) {
            sb.append(this.errorMessage).append(System.lineSeparator());
        }
        if(StringUtils.isNotBlank(this.standardErrorOutputAsString)) {
            sb.append(this.standardErrorOutputAsString).append(System.lineSeparator());
        }
        if(null!=this.caughtException) {
            sb.append(this.caughtException.getMessage()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return StringUtils.toString(this);
    }
}
