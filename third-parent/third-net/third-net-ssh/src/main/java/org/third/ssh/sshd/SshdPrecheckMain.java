package org.third.ssh.sshd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.sshd.common.util.security.SecurityUtils;
import org.apache.sshd.common.util.security.eddsa.EdDSASecurityProviderRegistrar;
import org.basic.common.util.StreamUtils;
import org.third.ssh.sshd.beans.SshClientExecutor;
import org.third.ssh.sshd.beans.SshExecuteRemoteCommandRequest;
import org.third.ssh.sshd.beans.SshExecuteRemoteCommandResult;
import org.third.ssh.sshd.beans.SshLoginConfig;

public class SshdPrecheckMain {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.getProperties().list(System.out);
        SecurityUtils.registerSecurityProvider(new EdDSASecurityProviderRegistrar());
        SshLoginConfig sshLoginConfig = new SshLoginConfig();
        String privateKey = StreamUtils.streamToString(new FileInputStream(System.getProperty("user.home")+ "/.ssh/id_rsa"));
        sshLoginConfig.setPrivateKeyAsString(privateKey);
        sshLoginConfig.setHost("192.168.56.107");
        sshLoginConfig.setUsername("root");
//        sshLoginConfig.setPassword("iso*.help");
        sshLoginConfig.setVerifyWay("cer");

        SshExecuteRemoteCommandRequest req = new SshExecuteRemoteCommandRequest();
        req.setCommand("sudo date");
        SshClientExecutor client = new SshClientExecutor(sshLoginConfig);
        client.login();
        SshExecuteRemoteCommandResult result = client.executeRemoteCommand(req);
        System.out.println(result);
    }

}
