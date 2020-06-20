package org.apache.maven.plugins.hello;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractDemoMojo extends AbstractMojo {
    /**
     */
    @Parameter(defaultValue = "${basedir}", readonly = true, required = true)
    private File basedir;
//
//    /**
//     */
//    @Parameter(defaultValue = "${settings}", readonly = true, required = true)
//    private Settings settings;
//
//    /**
//     */
//    @Parameter(defaultValue = "${project}", readonly = true, required = true)
//    protected MavenProject project;

}
