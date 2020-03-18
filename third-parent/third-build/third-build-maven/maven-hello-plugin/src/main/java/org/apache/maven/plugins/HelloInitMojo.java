package org.apache.maven.plugins;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "init", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class HelloInitMojo extends AbstractHelloMojo {
    /**
     * Location of the file.s
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = false)
    private File outputDirectory;

    @Parameter(property = "hello.guestName", defaultValue = "${project.name}", required = false)
    private String guestName;

    @Parameter(property = "hello.useagent", defaultValue = "true")
    private boolean useAgent;

    @Parameter(defaultValue = "${project.build.directory}/gpg", alias = "outputDirectory")
    private File ascDirectory;

//    @Parameter(defaultValue = "${project}", readonly = true, required = false)
//    protected MavenProject project;

    public void execute() throws MojoExecutionException {
        this.getLog().info(this.getClass() + " invoke init()");
//        this.getLog().info(JSON.toJSONString(this, true));
        this.getLog().info(String.format("guestName=%s, userAgent=%s, ascDirectory=%s", guestName, useAgent, ascDirectory));

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
