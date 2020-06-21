package org.apache.maven.plugins.hello;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "copy", aggregator = true, defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class DemoCopyMojo extends AbstractDemoMojo {
	/**
	 * <pre>
	 * pom.xml
	 * <plugin>
	    <groupId>org.apache.maven.plugins</groupId>
	    <artifactId>maven-demo-plugin</artifactId>
	    <version>0.0.1</version>
	    <configuration>
	      <from>${project.basedir}</from>
	      <to>${project.build.outputDirectory}</to>
	    </configuration>
	    <executions>
	      <execution>
	        <id>copyXX</id>
	        <goals>
	          <goal>touch</goal>
	          <goal>copy</goal>
	        </goals>
	      </execution>
	    </executions>
	    
	  plugin configuration
	  <configuration>
        <basedir implementation="java.io.File" default-value="${basedir}"/>
        <from implementation="java.io.File" default-value="${project.build.directory}">${fromDir}</from>
        <to implementation="java.io.File" default-value="${project.build.directory}">${toDir}</to>
      </configuration>
	 * 
	 * </pre>
	 */
	@Parameter(defaultValue = "${project.build.directory}", property = "fromDir", required = true, alias = "from")
	private File from;
	@Parameter(defaultValue = "${project.build.directory}", property = "toDir", required = true, alias = "to")
	private File to;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		this.getLog().info(String.format("from=%s, to=%s", from, to));
		this.getLog().info("copy");
	}
}
