package org.apache.maven.plugins.hello;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "init", aggregator = true, defaultPhase = LifecyclePhase.INITIALIZE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class DemoInitMojo extends AbstractDemoMojo {

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		this.getLog().info(this.getClass() + " invoke init()");
	}

}
