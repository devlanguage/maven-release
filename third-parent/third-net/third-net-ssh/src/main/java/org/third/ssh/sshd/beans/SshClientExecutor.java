package org.third.ssh.sshd.beans;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.future.OpenFuture;
import org.apache.sshd.client.scp.ScpClient;
import org.apache.sshd.client.scp.ScpClientCreator;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.ChannelListener;
import org.apache.sshd.common.scp.ScpHelper;
import org.apache.sshd.common.scp.ScpTimestamp;
import org.apache.sshd.common.scp.ScpTransferEventListener;
import org.basic.common.util.ExceptionUtils;
import org.basic.common.util.NumberUtils;
import org.basic.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <P>
 * Usage:
 * </P>
 * 
 * <pre>
 * SshClientExecutor sshClientExecutor = null;
 * try {
 *     SshLoginConfig sshLoginConfig = new SshLoginConfig();
 *     sshLoginConfig.setXXX...
 *     ...
 *     sshClientExecutor = new SshClientExecutor(sshLoginConfig);
 *     SshLoginResult sshLoginResult = sshClientExecutor.login();
 *
 *     SshExecuteRemoteCommandRequest sshExecuteRemoteCommandRequest = new SshExecuteRemoteCommandRequest();
 *     sshExecuteRemoteCommandRequest.setCommand("pwd");
 *     SshExecuteRemoteCommandResult sshExecuteRemoteCommandResult = sshClientExecutor.executeRemoteCommand(sshExecuteRemoteCommandRequest);
 *
 *     byte[] data = "aaa".getBytes();
 *     String remoteFilePathAsString = "/root/aaa.txt";
 *     String mode = "0400";
 *     SshScpResult sshScpResult = sshClientExecutor.uploadData(data, remoteFilePathAsString, mode);
 * } finally {
 *     if(null!=sshClientExecutor) {
 *          sshClientExecutor.logout();
 *     }
 * }
 *
 * </pre>
 *
 */
public class SshClientExecutor {

	private static final Logger logger = LoggerFactory.getLogger(SshClientExecutor.class);

	// NOTE !!! Append the LF to the end of the command because some kind of servers
	// using a buffered reader on the server end to read the command
	private static final String LF = "\n";

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	// Set default long timeout value to handle bad network situation, such as
	// remote cloud area,etc which seems to happen before.
	private static final Long DEFAULT_SSH_CONNECT_TIMEOUT_IN_MILLISECONDS = Long.valueOf(120000L);
	private static final Long DEFAULT_SSH_IO_TIMEOUT_IN_MILLISECONDS = Long.valueOf(120000L);

	private SshLoginConfig sshLoginConfig;
	private SshLoginResult sshLoginResult;

	private SshClient sshClient;
	private ClientSession clientSession;

	public SshLoginConfig getSshLoginConfig() {
		return sshLoginConfig;
	}

	public SshLoginResult getSshLoginResult() {
		return sshLoginResult;
	}

	public SshClient getSshClient() {
		return sshClient;
	}

	public ClientSession getClientSession() {
		return clientSession;
	}

	public SshClientExecutor(SshLoginConfig sshLoginConfig) {
		this.sshLoginConfig = sshLoginConfig;
	}

	/**
	 * Connect and do authentication against ssh-server.
	 *
	 */
	public SshLoginResult login() {
		SshLoginResult returnValue = null;
		boolean authSuccess = false;
		String errorMessage = null;
		Throwable caughtException = null;
		try {
			errorMessage = this.validateSshLoginConfig();
			if (StringUtils.isNullOrBlank(errorMessage)) {
				String username = this.sshLoginConfig.getUsername();
				String host = this.sshLoginConfig.getHost();
				int port = this.sshLoginConfig.getPort();
				Long timeoutForConnectInMilliseconds = this.getCalculatedSshConnectionTimeoutInMilliseconds(
						this.sshLoginConfig.getTimeoutForConnectInMilliseconds());

				this.sshClient = SshClient.setUpDefaultClient();
				this.sshClient.start();
				this.clientSession = this.sshClient.connect(username, host, port)
						.verify(timeoutForConnectInMilliseconds, TimeUnit.MILLISECONDS).getSession();

				errorMessage = this.tryToFigureOutAuthenticationMechanism();
				if (StringUtils.isNullOrBlank(errorMessage)) {
					Long timeoutForIoInMilliseconds = this.getCalculatedSshIoTimeoutInMilliseconds(
							this.sshLoginConfig.getTimeoutForIoInMilliseconds());
					this.clientSession.auth().verify(timeoutForIoInMilliseconds, TimeUnit.MILLISECONDS);
					authSuccess = true;
				}
			}
		} catch (Exception e) {
			caughtException = e;
			logger.error("Error occurs when login. sshLoginConfig={} and exception={} ", this.sshLoginConfig,
					ExceptionUtils.getStackTrace(e));
		} finally {
			SshLoginResult sshLoginResult = new SshLoginResult();
			sshLoginResult.setLogined(authSuccess);
			sshLoginResult.setErrorMessage(errorMessage);
			sshLoginResult.setCaughtException(caughtException);
			returnValue = this.sshLoginResult = sshLoginResult;
		}
		return returnValue;
	}

	private Long getCalculatedSshConnectionTimeoutInMilliseconds(Long timeoutForConnectInMilliseconds) {
		Long returnValue = timeoutForConnectInMilliseconds;
		if (null == timeoutForConnectInMilliseconds) {
			String sshConnectTimeoutInSecondsAsStringFromEnv = DEFAULT_SSH_CONNECT_TIMEOUT_IN_MILLISECONDS + "";
			if (NumberUtils.isCreatable(sshConnectTimeoutInSecondsAsStringFromEnv)) {
				Long sshConnectTimeoutInSeconds = NumberUtils
						.createLong(sshConnectTimeoutInSecondsAsStringFromEnv.trim());
				// Convert seconds to milliseconds
				returnValue = Long.valueOf(sshConnectTimeoutInSeconds.longValue() * 1000L);
			} else {
				returnValue = DEFAULT_SSH_CONNECT_TIMEOUT_IN_MILLISECONDS;
			}
		}

		return returnValue;
	}

	private Long getCalculatedSshIoTimeoutInMilliseconds(Long timeoutForIoInMilliseconds) {
		Long returnValue = timeoutForIoInMilliseconds;
		if (null == timeoutForIoInMilliseconds) {
			String sshIoTimeoutInSecondsAsStringFromEnv = DEFAULT_SSH_IO_TIMEOUT_IN_MILLISECONDS + "";
			if (NumberUtils.isCreatable(sshIoTimeoutInSecondsAsStringFromEnv)) {
				Long sshIoTimeoutInSeconds = NumberUtils.createLong(sshIoTimeoutInSecondsAsStringFromEnv.trim());
				// Convert seconds to milliseconds
				returnValue = Long.valueOf(sshIoTimeoutInSeconds.longValue() * 1000L);
			} else {
				returnValue = DEFAULT_SSH_IO_TIMEOUT_IN_MILLISECONDS;
			}
		}

		return returnValue;
	}

	/**
	 * Check to see if it is logined.
	 *
	 * @return true if it is logined.
	 */
	public boolean isLogined() {
		boolean returnValue = false;
		if (null != this.sshLoginResult && this.sshLoginResult.isLogined()) {
			returnValue = true;
		}
		return returnValue;
	}

	/**
	 * Execute remote command. Caution: You need to login successfully first before
	 * call this method.
	 *
	 * @param sshExecuteRemoteCommandRequest
	 * @return
	 */
	public SshExecuteRemoteCommandResult executeRemoteCommand(
			SshExecuteRemoteCommandRequest sshExecuteRemoteCommandRequest) {
		SshExecuteRemoteCommandResult returnValue = null;
		String errorMessage = null;
		Integer exitStatus = null;
		String standardOutputAsString = null;
		String standardErrorOutputAsString = null;
		Throwable caughtException = null;
		try {
			boolean isLogined = this.isLogined();
			if (!isLogined) {
				errorMessage = "You need to login successfully first before executeRemoteCommand. sshExecuteRemoteCommandRequest="
						+ sshExecuteRemoteCommandRequest;
			} else {
				errorMessage = this.validateSshExecuteRemoteCommandRequest(sshExecuteRemoteCommandRequest);
				if (StringUtils.isNullOrBlank(errorMessage)) {
					String command = sshExecuteRemoteCommandRequest.getCommand();
					ChannelExec channel = clientSession.createExecChannel(command + LF);
					channel.setUsePty(true);
					channel.addChannelListener(new ChannelListener() {
					});

					ByteArrayOutputStream standardErrorOutputStream = new ByteArrayOutputStream();
					ByteArrayOutputStream standardOutputStream = new ByteArrayOutputStream();

					channel.setOut(standardOutputStream);
					channel.setErr(standardErrorOutputStream);
					Long timeoutForIoInMilliseconds = this.getCalculatedSshIoTimeoutInMilliseconds(
							sshExecuteRemoteCommandRequest.getTimeoutForIoInMilliseconds());
					OpenFuture f=	channel.open();
					if(f.isOpened()) {
						standardOutputStream.write("admin\n".getBytes());
						standardOutputStream.flush();
						InputStream in = channel.getIn();
						if (in != null) {
							BufferedReader ins = new BufferedReader(new InputStreamReader(channel.getIn()));
							System.out.println(ins);
						}
					}
					f.verify(timeoutForIoInMilliseconds, TimeUnit.MILLISECONDS);
					Collection<ClientChannelEvent> result = channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
							timeoutForIoInMilliseconds.longValue());

			

					if (result.contains(ClientChannelEvent.TIMEOUT)) {
						errorMessage = "Failed to retrieve command result in time: sshExecuteRemoteCommandRequest="
								+ sshExecuteRemoteCommandRequest;
					} else {
						exitStatus = channel.getExitStatus();
						Charset charset = sshExecuteRemoteCommandRequest.getCharset();
						if (null == charset) {
							charset = DEFAULT_CHARSET;
						}
						if (0 < standardErrorOutputStream.size()) {
							byte[] standardErrorOutputByteArray = standardErrorOutputStream.toByteArray();
							standardErrorOutputAsString = new String(standardErrorOutputByteArray, charset);
						}
						if (0 < standardOutputStream.size()) {
							byte[] standardOutputByteArray = standardOutputStream.toByteArray();
							standardOutputAsString = new String(standardOutputByteArray, charset);
						}
					}
				}
//                }
			}
		} catch (Exception e) {
			caughtException = e;
			logger.error("Error occurs when executeRemoteCommand. sshExecuteRemoteCommandRequest={} and exception={} ",
					sshExecuteRemoteCommandRequest, ExceptionUtils.getStackTrace(e));
		} finally {
			returnValue = new SshExecuteRemoteCommandResult();
			returnValue.setErrorMessage(errorMessage);
			returnValue.setExitStatus(exitStatus);
			returnValue.setStandardOutputAsString(standardOutputAsString);
			returnValue.setStandardErrorOutputAsString(standardErrorOutputAsString);
			returnValue.setCaughtException(caughtException);
		}
		return returnValue;
	}

	/**
	 * Upload data to a file on remote server Caution: You need to login
	 * successfully first before call this method.
	 *
	 * @param data
	 * @param remoteFilePathAsString
	 * @param mode                   a four digit string (e.g., 0644, see "man
	 *                               chmod")
	 * @return
	 */
	public SshScpResult uploadData(byte[] data, String remoteFilePathAsString, String mode) {
		// FilenameUtils.concat
		SshScpResult returnValue = null;
		boolean success = false;
		String errorMessage = null;
		Throwable caughtException = null;
		try {
			boolean isLogined = this.isLogined();
			if (!isLogined) {
				errorMessage = "You need to login successfully first before uploadData. remoteFilePathAsString="
						+ remoteFilePathAsString;
			} else {
				errorMessage = this.validateForUploadData(data, remoteFilePathAsString, mode);
				if (StringUtils.isNullOrBlank(errorMessage)) {
					Set<PosixFilePermission> posixFilePermissions = null;
					if (StringUtils.isNotBlank(mode)) {
						posixFilePermissions = ScpHelper.parseOctalPermissions(mode.trim());
					}
					// Set ScpTimestamp to workaround the permission unset bug.
					long now = System.currentTimeMillis();
					ScpTimestamp scpTimestamp = new ScpTimestamp(now, now);
					ScpClient scpClient = this.createScpClient();
					scpClient.upload(data, remoteFilePathAsString, posixFilePermissions, scpTimestamp);
					success = true;
				}
			}
		} catch (Exception e) {
			caughtException = e;
			logger.error("Error occurs when uploadData. remoteFilePathAsString={} , mode={} and exception={} ",
					remoteFilePathAsString, mode, ExceptionUtils.getStackTrace(e));
		} finally {
			returnValue = new SshScpResult();
			returnValue.setSuccess(success);
			returnValue.setErrorMessage(errorMessage);
			returnValue.setCaughtException(caughtException);
		}
		return returnValue;
	}

	private ScpClient createScpClient() {
		ScpClient returnValue = null;
		ScpClientCreator creator = ScpClientCreator.instance();
		returnValue = creator.createScpClient(this.clientSession, ScpTransferEventListener.EMPTY);
		return returnValue;
	}

	/**
	 * Validate data for uploadData
	 *
	 * @return errorMessage blank means valid.
	 */
	private String validateForUploadData(byte[] data, String remoteFilePathAsString, String mode) {
		String errorMessage = null;
		if (null == data) {
			errorMessage = "data should not be null.";
			return errorMessage;
		}
		if (StringUtils.isNullOrBlank(remoteFilePathAsString)) {
			errorMessage = "remoteFilePathAsString should not be blank.";
			return errorMessage;
		}
		return errorMessage;
	}

	/**
	 * Logout and clean all.
	 */
	public void logout() {
		this.closeClientSession();
		this.closeSshClient();
	}

	private void closeClientSession() {
		if (null != this.clientSession) {
			try {
				this.clientSession.close();
			} catch (Exception e) {
				logger.error("Error occurs when close clientSession=" + this.clientSession, e);
			}
		}
	}

	private void closeSshClient() {
		if (null != this.sshClient) {
			try {
				this.sshClient.close();
			} catch (Exception e) {
				logger.error("Error occurs when close sshClient=" + this.sshClient, e);
			}
		}
	}

	/**
	 * Try to find out authentication mechanism
	 *
	 * @return errorMessage blank means authentication mechanism found.
	 * @throws Exception
	 */
	private String tryToFigureOutAuthenticationMechanism() throws Exception {
		String errorMessage = null;
		String verifyWay = this.sshLoginConfig.getVerifyWay();
		String password = this.sshLoginConfig.getPassword();
		String privateKeyAsString = this.sshLoginConfig.getPrivateKeyAsString();
		if (StringUtils.isNullOrBlank(verifyWay)) {
			// Try to guess verifyWay by context data. Prefer password authentication.
			if (StringUtils.isNotBlank(password)) {
				verifyWay = SshLoginConfig.VERIFY_WAY_PWD;
			} else if (StringUtils.isNotBlank(privateKeyAsString)) {
				verifyWay = SshLoginConfig.VERIFY_WAY_CER;
			}
		}
		if (SshLoginConfig.VERIFY_WAY_PWD.equalsIgnoreCase(verifyWay)) {
			this.clientSession.addPasswordIdentity(password);
		} else if (SshLoginConfig.VERIFY_WAY_CER.equalsIgnoreCase(verifyWay)) {
//            String keyName = this.sshLoginConfig.getKeyName();
//            String passphraseOfPrivateKey = this.sshLoginConfig.getPassphraseOfPrivateKey();
//            Charset charset = this.sshLoginConfig.getCharset();
//            try(InputStream privateKeyAsInputStream = StreamUtils.stringToInputStream(privateKeyAsString)) {
//                KeyPair keyPair = SecurityUtils.loadKeyPairIdentities(keyName, privateKeyAsInputStream, getPassword -> {
//                    return passphraseOfPrivateKey;
//                });
//                this.clientSession.addPublicKeyIdentity(keyPair);
//            }
		} else {
			// Should not reach here after validation step
			errorMessage = "Can not figure out authentication mechanism ! verifyWay=" + verifyWay;
		}
		return errorMessage;
	}

	/**
	 * Validate sshLoginConfig data
	 *
	 * @return errorMessage blank means valid.
	 */
	private String validateSshLoginConfig() {
		String errorMessage = null;
		if (null == this.sshLoginConfig) {
			errorMessage = "sshLoginConfig should not be null.";
			return errorMessage;
		}
		String username = this.sshLoginConfig.getUsername();
		if (StringUtils.isNullOrBlank(username)) {
			errorMessage = "username should not be blank.";
			return errorMessage;
		}
		String host = this.sshLoginConfig.getHost();
		if (StringUtils.isNullOrBlank(host)) {
			errorMessage = "host should not be blank.";
			return errorMessage;
		}
		int port = this.sshLoginConfig.getPort();
		if (0 > port) {
			errorMessage = "port should not be >0 .";
			return errorMessage;
		}
		Charset charset = this.sshLoginConfig.getCharset();
		if (null == charset) {
			errorMessage = "charset should not be null.";
			return errorMessage;
		}
		String password = this.sshLoginConfig.getPassword();
		String privateKeyAsString = this.sshLoginConfig.getPrivateKeyAsString();
		String verifyWay = this.sshLoginConfig.getVerifyWay();
		if (StringUtils.isNotBlank(verifyWay)) {
			if (SshLoginConfig.VERIFY_WAY_PWD.equalsIgnoreCase(verifyWay)) {
				if (StringUtils.isNullOrBlank(password)) {
					errorMessage = "Password should not be blank when verifyWay=" + verifyWay;
					return errorMessage;
				}
			} else if (SshLoginConfig.VERIFY_WAY_CER.equalsIgnoreCase(verifyWay)) {
				if (StringUtils.isNullOrBlank(privateKeyAsString)) {
					errorMessage = "privateKeyAsString should not be blank when verifyWay=" + verifyWay;
					return errorMessage;
				}
			} else {
				errorMessage = "Unsupported verifyWay found. verifyWay=" + verifyWay;
				return errorMessage;
			}
		} else {
			if (StringUtils.isNullOrBlank(password) && StringUtils.isNullOrBlank(privateKeyAsString)) {
				errorMessage = "Either password or privateKeyAsString should not be blank when verifyWay is blank.";
				return errorMessage;
			}
		}
		return errorMessage;
	}

	/**
	 * Validate sshExecuteRemoteCommandRequest data
	 *
	 * @return errorMessage blank means valid.
	 */
	private String validateSshExecuteRemoteCommandRequest(
			SshExecuteRemoteCommandRequest sshExecuteRemoteCommandRequest) {
		String errorMessage = null;
		if (null == sshExecuteRemoteCommandRequest) {
			errorMessage = "sshExecuteRemoteCommandRequest should not be null.";
			return errorMessage;
		}
		String command = sshExecuteRemoteCommandRequest.getCommand();
		if (StringUtils.isNullOrBlank(command)) {
			errorMessage = "command should not be blank.";
			return errorMessage;
		}
		Charset charset = sshExecuteRemoteCommandRequest.getCharset();
		if (null == charset) {
			errorMessage = "charset should not be null.";
			return errorMessage;
		}
		return errorMessage;
	}

}
