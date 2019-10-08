package org.basic.grammar.jdk.jdk13;

public class JdkMain {
	public static String getJavaVersionProperty() {
		return System.getProperty("java.version");
	}

	public static int getJavaVersion() {
		//1.8.0_201
		//10.0.1,11.0.1, 12.0.1
		//13
		String versionProperty = getJavaVersionProperty();
		String[] versionSegments = versionProperty.split("\\.");

		if (versionSegments.length < 2) {
			return -1;
		}
		String javaVersionStr = versionSegments[1];

		try {
			return Integer.parseInt(javaVersionStr);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static void main(String[] args) {
		getJavaVersion();

	}
}