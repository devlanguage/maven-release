package org.apache.maven.plugins.hello;

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "hello", aggregator = true, defaultPhase = LifecyclePhase.INITIALIZE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class DemoHelloMojo extends AbstractDemoMojo {

//	@Parameter(alias = "clientAlias", defaultValue = "DemoHello.default", name = "clientName", property = "clientProperty", required = false)
//	private String client;
	@Parameter(alias = "clientAlias", defaultValue = "@{prefix} rollback the release of @{releaseLabel}", property = "scmRollbackCommitComment")
	// "@{prefix} rollback the release of @{releaseLabel}"
	private List<String> client;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		this.getLog().info(this.getClass() + " invoke hello()");
		this.getLog().info("client:" + client);
	}

}
