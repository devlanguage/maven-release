package org.third.jms.util.helper;

import java.io.IOException;
import java.io.InputStream;

import org.basic.common.bean.CommonLogger;

public class ConfigurationLoader {

	public static Configuration config;
	private static InputStream in;

	private static CommonLogger logger = CommonLogger
			.getLogger(ConfigurationLoader.class);

	public static Configuration load(String path) {
		if (config == null) {
			in = ConfigurationLoader.class.getClassLoader().getResourceAsStream(path);
			try {
				config = new Configuration();
				config.load(in);
			} catch (IOException e) {
				logger.error(e);
			} finally {
				close();
			}
		}

		return config;

	}

	public static void close() {
		try {
			in.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
