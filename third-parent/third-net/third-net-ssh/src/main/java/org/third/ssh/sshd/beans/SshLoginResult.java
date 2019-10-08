package org.third.ssh.sshd.beans;

/**
 * Created by Robin on 8/3/2018 5:28 PM
 */
public class SshLoginResult {

    private boolean logined;

    private String errorMessage;

    private Throwable caughtException;

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
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

    @Override
    public String toString() {
        return "SshLoginResult{" +
                "logined=" + logined +
                ", errorMessage='" + errorMessage + '\'' +
                ", caughtException=" + caughtException +
                '}';
    }
}
