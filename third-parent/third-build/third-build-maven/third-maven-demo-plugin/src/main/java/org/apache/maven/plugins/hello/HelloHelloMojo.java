package org.apache.maven.plugins.hello;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "hello", aggregator = true, defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class HelloHelloMojo extends AbstractHelloMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("Hello");
    }

}
