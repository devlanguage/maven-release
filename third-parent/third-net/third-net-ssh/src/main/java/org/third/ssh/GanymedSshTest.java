package org.third.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class GanymedSshTest {
	private final static Logger logger = LoggerFactory.getLogger(GanymedSshTest.class);

/**
 * Supported MACs:
 *   @{link ch.ethz.ssh2.crypto.digest.MAC} "hmac-sha1-96", "hmac-sha1", "hmac-md5-96", "hmac-md5" };
 *     
 * Supported Cipher	
 * @param args
 */
	public static void main(String[] args) {
		GanymedSshTest tester = new GanymedSshTest();
		tester.executeCommandOnRemote("h0.test1.com", "root", "iso*help", "date; hostname -f; date;dax");
	}

	public static CheckNodeResult executeCommandOnRemote(String hostname, String username, String password,
			String cmd) {

		// cmdDiskSpace := `df -m "/tmp"|sed '1d'|awk '{printf "%d", $4/1024}'`
		// tmp 文件夹剩余使用空间，小于3G，输出空间不满足，或者小于RequireNodes
		Connection conn = null;
		Session sess = null;
		StringBuffer stringBuffer = new StringBuffer("");
		CheckNodeResult checkNodeResult = new CheckNodeResult(false, 0, stringBuffer);

		try {
			conn = new Connection(hostname);
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (isAuthenticated == false) {
				throw new IOException("Authentication failed.");
			}

			sess = conn.openSession();
			sess.execCommand(cmd);

			logger.info("Here is some information about the remote host:" + hostname);
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader stdoutReader= new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			while (null != (line = stdoutReader.readLine())) {
				stringBuffer.append(line + System.lineSeparator());
				logger.info("|" + line);
			}

			InputStream stderr = new StreamGobbler(sess.getStderr());
			BufferedReader stderrReader= new BufferedReader(new InputStreamReader(stderr));
			while (null != (line = stderrReader.readLine())) {
				stringBuffer.append(line + System.lineSeparator());
				logger.info("+" + line);
			}

			
			/* Show exit status, if available (otherwise "null") */
			logger.info("ExitCode: " + sess.getExitStatus());

			checkNodeResult.setExitStatus(sess.getExitStatus());
			checkNodeResult.setInfo(stringBuffer);

		} catch (IOException e) {
			e.printStackTrace(System.err);
			checkNodeResult.setCheckType("Authentication");
			checkNodeResult.setExitStatus(2);
			// System.exit(2);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			checkNodeResult.setCheckType("CMDFAILED");
			checkNodeResult.setExitStatus(2);
		} finally {
			/* Close this session */
			if (sess != null) {
				sess.close();
			}
			/* Close the connection */
			if (conn != null) {
				conn.close();
			}

			return checkNodeResult;
		}

	}
}
