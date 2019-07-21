package org.third.net.ftps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

public class ApacheNetFtpTest {
	private FTPClient ftpClient;

	public static void main(String agrs[]) {
		ApacheNetFtpTest fu = new ApacheNetFtpTest();
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
//		   config.setXXX(YYY); // change required options
		// for example config.setServerTimeZoneId("Pacific/Pitcairn")
		ftp.configure(config);
		boolean error = false;
		try {
			int reply;
			String server = "h9.test1.com";
			ftp.connect(server);
			ftp.login("test1", "test1");
			System.out.println("Connected to " + server + ".");
			System.out.print(ftp.getReplyString());

			// After connection attempt, you should check the reply code to verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}
			// transfer files
			ftp.logout();
		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
			System.exit(error ? 1 : 0);
		}
		// 下载测试
		String filepath[] = { "aa.xlsx", "bb.xlsx" };
		String localfilepath[] = { "E:/lalala/aa.xlsx", "E:/lalala/bb.xlsx" };
		for (int i = 0; i < filepath.length; i++) {
			fu.download(filepath[i], localfilepath[i]);
		}

		// 上传测试
		String localfile = "E:/lalala/tt.xlsx";
		String remotefile = "tt.xlsx"; // 上传
		fu.upload(localfile, remotefile);
		fu.closeConnect();

	}

	public void upload(String localFile, String remoteFile) {
		File file_in = new File(localFile);
		try (FileInputStream is = new FileInputStream(file_in)) {
			ftpClient.appendFile(remoteFile, is);
			System.out.println("upload success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download(String remoteFile, String localFile) {
		File file_in = new File(localFile);
		try (InputStream is = ftpClient.retrieveFileStream(remoteFile);
				FileOutputStream os = new FileOutputStream(file_in)) {

			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			System.out.println("download success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void closeConnect() {
		try {
			ftpClient.disconnect();
			System.out.println("disconnect success");
		} catch (IOException ex) {
			System.out.println("not disconnect");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
