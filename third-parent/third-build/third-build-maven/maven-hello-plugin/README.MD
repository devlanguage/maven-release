Concept
Mojo: Maven Old Java Object ---> plugin.goal
POM:  Project Object Model
Create the mavne plugin
mvn archetype:generate \
  -DgroupId=com.sample.plugin \
  -DartifactId=hello-maven-plugin \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DarchetypeArtifactId=maven-archetype-plugin

** Execute the maven plugin
mvn groupId:artifactId:version:goal

mvn com.sample.plugin:hello-maven-plugin:1.0-SNAPSHOT:touch

How to use
  <plugin>
        <groupId>com.sample.plugin</groupId>
        <artifactId>hello-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
          <execution>
            <phase>compile</phase>
            <goals>
              <goal>touch</goal>
            </goals>
          </execution>
        </executions>
      </plugin>

Error:
1) execution not covered by lifecycle:
Add the configuration into <build>/<pluginManagement>
<plugin>
                    <groupId>org.eclipse.m2e</groupId>
                    <artifactId>lifecycle-mapping</artifactId>
                    <version>1.0.0</version>
                    <configuration>
                        <lifecycleMappingMetadata>
                            <pluginExecutions>
                                <pluginExecution>
                                    <pluginExecutionFilter>
                                        <groupId>org.apache.maven.plugins</groupId>
                                        <artifactId>maven-plugin-plugin</artifactId>
                                        <versionRange>[3.2,)</versionRange>
                                        <goals>
                                            <goal>descriptor</goal>
                                            <goal>helpmojo</goal>
                                        </goals>
                                    </pluginExecutionFilter>
                                    <action>
                                        <ignore></ignore>
                                    </action>
                                </pluginExecution>
                            </pluginExecutions>
                        </lifecycleMappingMetadata>
                    </configuration>
                </plugin>

