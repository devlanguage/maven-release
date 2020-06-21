package org.third;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.release.HelpMojo;

public class ReleaseHelpMojo {
	public static void main(String[] args) {
		org.apache.maven.plugins.release.HelpMojo m = new HelpMojo();
		try {
			m.execute();
		} catch (MojoExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
