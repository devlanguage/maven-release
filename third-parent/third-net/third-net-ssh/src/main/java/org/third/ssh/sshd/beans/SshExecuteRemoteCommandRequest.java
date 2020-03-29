package org.third.ssh.sshd.beans;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SshExecuteRemoteCommandRequest {

    private String command;
    private Charset charset = StandardCharsets.UTF_8;

    private Long timeoutForIoInMilliseconds;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Long getTimeoutForIoInMilliseconds() {
        return timeoutForIoInMilliseconds;
    }

    public void setTimeoutForIoInMilliseconds(Long timeoutForIoInMilliseconds) {
        this.timeoutForIoInMilliseconds = timeoutForIoInMilliseconds;
    }

    @Override
    public String toString() {
        return "SshExecuteRemoteCommandRequest{" + "command='" + command + '\'' + ", charset=" + charset
                + ", timeoutForIoInMilliseconds=" + timeoutForIoInMilliseconds + '}';
    }
}
