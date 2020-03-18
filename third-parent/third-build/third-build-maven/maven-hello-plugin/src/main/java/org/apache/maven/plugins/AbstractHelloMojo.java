package org.apache.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractHelloMojo extends AbstractMojo {
    /**
     * <pre>
     * org.apache.maven.plugin.MojoExecutionException if an unexpected problem occurs. Throwing this exception causes a "BUILD ERROR" message to be displayed.
     * org.apache.maven.plugin.MojoFailureException if an expected problem (such as a compilation failure) occurs. Throwing this exception causes a "BUILD FAILURE" message to be
     * </pre>
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

    }

    @Parameter(defaultValue = "${project.build.directory}", property = "projectName", required = true)
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
