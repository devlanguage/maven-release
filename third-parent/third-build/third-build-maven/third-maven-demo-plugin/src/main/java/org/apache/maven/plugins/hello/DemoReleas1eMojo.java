package org.apache.maven.plugins.hello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "release", defaultPhase = LifecyclePhase.INSTALL)
public class DemoReleas1eMojo extends AbstractDemoMojo {
	/**
	 * Location of the file.s
	 */
	@Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
	private File outputDirectory;

	public void execute() throws MojoExecutionException {
		this.getLog().info(this.getClass() + " invoke release()");

		File f = outputDirectory;

		if (!f.exists()) {
			f.mkdirs();
		}

		File touch = new File(f, "touch.txt");

		FileWriter w = null;
		try {
			w = new FileWriter(touch);

			w.write("touch.txt");
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + touch, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
}
