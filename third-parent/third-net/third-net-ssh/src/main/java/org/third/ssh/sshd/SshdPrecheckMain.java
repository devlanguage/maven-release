package org.third.ssh.sshd;

import org.third.ssh.sshd.beans.SshClientExecutor;
import org.third.ssh.sshd.beans.SshExecuteRemoteCommandRequest;
import org.third.ssh.sshd.beans.SshExecuteRemoteCommandResult;
import org.third.ssh.sshd.beans.SshLoginConfig;

public class SshdPrecheckMain {
	public static void main(String[] args) {
		SshLoginConfig sshLoginConfig = new SshLoginConfig();
		sshLoginConfig.setHost("h9.test1.com");
		sshLoginConfig.setUsername("admin");
		sshLoginConfig.setPassword("admin");

		SshExecuteRemoteCommandRequest req = new SshExecuteRemoteCommandRequest();
		req.setCommand("sudo /tmp/pre-check.sh");
		SshClientExecutor client = new SshClientExecutor(sshLoginConfig);
		client.login();
		SshExecuteRemoteCommandResult result = client.executeRemoteCommand(req);
		System.out.println(result);
	}

}
