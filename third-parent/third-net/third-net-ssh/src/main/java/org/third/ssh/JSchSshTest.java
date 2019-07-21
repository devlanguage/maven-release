package org.third.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.basic.common.util.BasicException;
import org.basic.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JSchSshTest {
	private final static Logger logger = LoggerFactory.getLogger(JSchSshTest.class);

	public static void main(String[] args) {
		JSchSshTest tester = new JSchSshTest();
		tester.executeCommandOnRemote("h0.test1.com", "root", "iso*help", "date; hostname -f; date;dax");
	}

	public static Session openJSchSession(String hostname, String username, String password, String cmd)
			throws JSchException, BasicException {
		String privateKeyContent = "";
		Session session = null;
		if (!StringUtils.hasText(privateKeyContent)) {
			// use private key to connect
			JSch jsch = new JSch();
			jsch.addIdentity(hostname, privateKeyContent.getBytes(), (byte[]) null, (byte[]) null);
			session = jsch.getSession(username, hostname);
			if (session == null) {
				throw new BasicException("connot get session successful");
			}
			logger.info("get session's username is " + session.getUserName());

			// use private key instead of username/password
			session.setConfig("StrictHostKeyChecking", "no");
			session.setConfig("PreferredAuthentications", "publickey,gssapi-with-mic,keyboard-interactive,password");
		} else {
			// use password to connect
			if (!StringUtils.hasText(password)) {
				JSch jsch = new JSch();
				session = jsch.getSession(username, hostname);
				if (session == null) {
					throw new BasicException("connot get session successful");
				}
				logger.info("get session's username is " + session.getUserName());

				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(password);
			}
		}

		if (session == null || !session.isConnected()) {
			System.out.println("faield to connect");
		}
		return session;
	}

	public static CheckNodeResult executeCommandOnRemote(String hostname, String username, String password,
			String cmd) {

		InputStream in = null;
		InputStream stderr = null;
		StringBuffer messageerror = new StringBuffer("");
		StringBuffer message = new StringBuffer("");
		CheckNodeResult checkNodeResult = new CheckNodeResult();
		int exitStatus = 0;
		Session session = null;
		try {
			session = openJSchSession(hostname, username, password, cmd);

			// create the execution channel over the session
			// "session": new ChannelSession();
			// "shell": new ChannelShell();
			// "exec": new ChannelExec();
			// "x11": new ChannelX11();
			// "auth-agent@openssh.com": new ChannelAgentForwarding();
			// "direct-tcpip": new ChannelDirectTCPIP();
			// "forwarded-tcpip": new ChannelForwardedTCPIP();
			// "sftp": new ChannelSftp();
			// "subsystem": new ChannelSubsystem();
//			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
//			channelSftp.st
			ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
			logger.debug("openChannel is OK !!!!");
			// Set the command to execute on the channel and execute the command
			logger.debug("remote command is :  " + cmd);
			channelExec.setCommand(cmd);

			channelExec.setInputStream(null);
			// InputStream stdout = channelExec.getInputStream();
			in = channelExec.getInputStream();
			stderr = channelExec.getErrStream();

			channelExec.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(stderr, "utf-8"));
			String linestderr;
			while ((linestderr = reader.readLine()) != null) {
				messageerror = messageerror.append(linestderr + "\n");
			}
			logger.debug("Remote command exec  content : {}", messageerror.toString());

			BufferedReader outreader = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String line;
			logger.debug("Remote command exec  content : correct");
			while ((line = reader.readLine()) != null) {
				message = message.append(line + "\n");
			}
			logger.debug("Remote command exec  content : {}", message.toString());

			// Get an InputStream from this channel and read messages, generated
			// by the executing command, from the remote side.
			exitStatus = channelExec.getExitStatus();
			logger.debug("Remote command exec error! : {}", exitStatus);
			// Command execution completed here.
			// Retrieve the exit status of the executed command

			if (exitStatus != 0) {
				// checkNodeResult = new CheckNodeResult(checkType,false,exitStatus,new
				// StringBuffer("exec cmd :"+ cmd+ " in " + host + " ,Error Information :
				// "+messageerror.toString()));
				logger.debug("Remote command exec error! : {}", messageerror.toString());
			}
		} catch (JSchException e) {
			logger.error("run remote  scrips occor jsch exception" + e.getMessage().toString());
			// checkNodeResult = new CheckNodeResult(checkType,false,exitStatus,new
			// StringBuffer("exec cmd :"+ cmd+ " in " + host + " ,Error Information :
			// "+e.getMessage()));
		} catch (IOException e) {
			logger.error("run remote  scrips occor io exception" + e.getMessage().toString());
			// checkNodeResult = new CheckNodeResult(checkType,false,exitStatus,new
			// StringBuffer("exec cmd :"+ cmd+ " in " + host + " ,Error Information :
			// "+e.getMessage()));
		} catch (BasicException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (stderr != null) {
				try {
					stderr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (session != null) {
				session.disconnect();
			}

		}
		// checkNodeResult = new CheckNodeResult(checkType,true,exitStatus,new
		// StringBuffer("exec cmd "+host ));
		return null;
	}
}
