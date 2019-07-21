package org.third.ssh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.basic.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

class ConnectConfig {
	String host;
	int port;
	String userName;
	private String privateKey;
	private String password;
	int timeout = 0;// Milliseconds. socket.setSoTimeout(timeout)

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}

public class JSchSshUtils {
	private final static Logger logger = LoggerFactory.getLogger(JSchSshUtils.class);

	private final static Logger log = LoggerFactory.getLogger(JSchSshUtils.class);

	/** SFTP */
	public static final String SFTP = "sftp";
	/** 通道 */
	private ChannelSftp channel;
	/** session */
	private Session session;

	/** 规避多线程并发不断开问题 */
	private static ThreadLocal<JSchSshUtils> sftpLocal = new ThreadLocal<JSchSshUtils>();

	/**
	 * 获取sftpchannel
	 * 
	 * @param connectConfig 连接配置
	 * @return
	 * @throws Exception
	 * @throws JSchException
	 */
	private void init(ConnectConfig connectConfig) throws Exception {
		String host = connectConfig.getHost();
		int port = connectConfig.getPort();

		String userName = connectConfig.getUserName();

		// 创建JSch对象
		JSch jsch = new JSch();

		// 添加私钥(信任登录方式)
		if (StringUtils.hasLength(connectConfig.getPrivateKey())) {
			jsch.addIdentity(connectConfig.getPrivateKey());
		}

		session = jsch.getSession(userName, host, port);
		if (log.isInfoEnabled()) {
			log.info(" JSCH Session created,sftpHost = {}, sftpUserName={}", host, userName);
		}

		// 设置密码
		if (StringUtils.hasLength(connectConfig.getPassword())) {
			session.setPassword(connectConfig.getPassword());
		}

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		// 设置超时
		session.setTimeout(connectConfig.getTimeout());
		// 建立连接
		session.connect();
		if (log.isInfoEnabled()) {
			log.info("JSCH Session connected.sftpHost = {}, sftpUserName={}", host, userName);
		}

		// 打开SFTP通道
		channel = (ChannelSftp) session.openChannel(SFTP);
		// 建立SFTP通道的连接
		channel.connect();
		if (log.isInfoEnabled()) {
			log.info("Connected successfully to sftpHost = {}, sftpUserName={}", host, userName);
		}
	}

	/**
	 * 是否已连接
	 * 
	 * @return
	 */
	private boolean isConnected() {
		return null != channel && channel.isConnected();
	}

	/**
	 * 获取本地线程存储的sftp客户端
	 * 
	 * @return
	 * @throws Exception
	 */
	public static JSchSshUtils getJSchSshUtils(ConnectConfig connectConfig) throws Exception {
		JSchSshUtils sftpUtil = sftpLocal.get();
		if (null == sftpUtil || !sftpUtil.isConnected()) {
			sftpLocal.set(new JSchSshUtils(connectConfig));
		}
		return sftpLocal.get();
	}

	/**
	 * 释放本地线程存储的sftp客户端
	 */
	public static void release() {
		if (null != sftpLocal.get()) {
			sftpLocal.get().closeChannel();
			sftpLocal.set(null);
		}
	}

	/**
	 * 构造函数
	 * <p>
	 * 非线程安全，故权限为私有
	 * </p>
	 * 
	 * @throws Exception
	 */
	private JSchSshUtils(ConnectConfig connectConfig) throws Exception {
		super();
		init(connectConfig);
	}

	/**
	 * 关闭通道
	 * 
	 * @throws Exception
	 */
	public void closeChannel() {
		if (null != channel) {
			try {
				channel.disconnect();
			} catch (Exception e) {
				log.error("关闭SFTP通道发生异常:", e);
			}
		}
		if (null != session) {
			try {
				session.disconnect();
			} catch (Exception e) {
				log.error("SFTP关闭 session异常:", e);
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param downDir 下载目录
	 * @param src     源文件
	 * @param dst     保存后的文件名称或目录
	 * @throws Exception
	 */
	public void downFile(String downDir, String src, String dst) throws Exception {
		channel.cd(downDir);
		channel.get(src, dst);
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath 文件全路径
	 * @throws SftpException
	 */
	public void deleteFile(String filePath) throws SftpException {
		channel.rm(filePath);
	}

	@SuppressWarnings("unchecked")
	public List<String> listFiles(String dir) throws SftpException {
		Vector<LsEntry> files = channel.ls(dir);
		if (null != files) {
			List<String> fileNames = new ArrayList<String>();
			Iterator<LsEntry> iter = files.iterator();
			while (iter.hasNext()) {
				String fileName = iter.next().getFilename();
				if (".".equals(fileName) || "..".equals(fileName)) {
					continue;
				}
				fileNames.add(fileName);
			}
			return fileNames;
		}
		return null;
	}
}
