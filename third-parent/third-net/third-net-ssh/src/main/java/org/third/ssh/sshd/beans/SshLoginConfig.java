package org.third.ssh.sshd.beans;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SshLoginConfig {

	public static final String VERIFY_WAY_PWD = "pwd";
	public static final String VERIFY_WAY_CER = "cer";

	public SshLoginConfig() {
		verifyWay = "pwd";
	}

	private String host;
	private int port = 22;

	private String username;

	private Long timeoutForConnectInMilliseconds;

	private Long timeoutForIoInMilliseconds;

	private Charset charset = StandardCharsets.UTF_8;

	private String verifyWay;

	/**
	 * For password authentication
	 */
	private String password;

	/**
	 * For privateKey authentication
	 */
	private String keyName;
	private String privateKeyAsString;
	private String passphraseOfPrivateKey;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getTimeoutForConnectInMilliseconds() {
		return timeoutForConnectInMilliseconds;
	}

	public void setTimeoutForConnectInMilliseconds(Long timeoutForConnectInMilliseconds) {
		this.timeoutForConnectInMilliseconds = timeoutForConnectInMilliseconds;
	}

	public Long getTimeoutForIoInMilliseconds() {
		return timeoutForIoInMilliseconds;
	}

	public void setTimeoutForIoInMilliseconds(Long timeoutForIoInMilliseconds) {
		this.timeoutForIoInMilliseconds = timeoutForIoInMilliseconds;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getPrivateKeyAsString() {
		return privateKeyAsString;
	}

	public void setPrivateKeyAsString(String privateKeyAsString) {
		this.privateKeyAsString = privateKeyAsString;
	}

	public String getPassphraseOfPrivateKey() {
		return passphraseOfPrivateKey;
	}

	public void setPassphraseOfPrivateKey(String passphraseOfPrivateKey) {
		this.passphraseOfPrivateKey = passphraseOfPrivateKey;
	}

	public String getVerifyWay() {
		return verifyWay;
	}

	public void setVerifyWay(String verifyWay) {
		this.verifyWay = verifyWay;
	}

	@Override
	public String toString() {
		return "SshLoginConfig{" + "host='" + host + '\'' + ", port=" + port + ", username='" + username + '\''
				+ ", timeoutForConnectInMilliseconds=" + timeoutForConnectInMilliseconds
				+ ", timeoutForIoInMilliseconds=" + timeoutForIoInMilliseconds + ", charset=" + charset
				+ ", verifyWay='" + verifyWay + '\'' + ", keyName='" + keyName + '\'' + '}';
	}
}
