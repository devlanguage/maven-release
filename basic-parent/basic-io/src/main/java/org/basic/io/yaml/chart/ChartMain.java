package org.basic.io.yaml.chart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Collections;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class ChartMain {
	public static final void main(String[] args) throws YamlException {
		ChartMain tester = new ChartMain();
		tester.testYamlBeans();
//		tester.testSnakeYaml();

	}

	private void testSnakeYaml() {
		LoaderOptions loaderOptions = new LoaderOptions();
		loaderOptions.setWrappedToRootException(true);

		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setExplicitStart(false);
		dumperOptions.setExplicitEnd(false);
		dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
		dumperOptions.setTags(Collections.EMPTY_MAP);
//		dumperOptions.setPrettyFlow(false);

		Yaml yaml = new Yaml(dumperOptions);

		InputStream in = ChartMain.class.getResourceAsStream("index.yaml");
		ChartIndex config = yaml.loadAs(in, ChartIndex.class);
		System.out.println(config.getEntries().values().iterator().next());
		System.out.println(yaml.dumpAsMap(config));

	}

	private void testYamlBeans() throws YamlException {
		YamlConfig config = new YamlConfig();
		config.readConfig.setIgnoreUnknownProperties(true);
		Reader reader1 = new BufferedReader(new InputStreamReader(ChartMain.class.getResourceAsStream("index.yaml")));
		YamlReader reader = new YamlReader(reader1, config);
		ChartIndex index = reader.read(ChartIndex.class);

		config.writeConfig.setAutoAnchor(false);
		config.writeConfig.setIndentSize(2);
//		config.writeConfig.setWriteRootTags(false);
//		config.writeConfig.setWriteRootElementTags(false);
//		config.writeConfig.setCanonical(true);
		StringWriter writer = new StringWriter();
		YamlWriter yamlWriter = new YamlWriter(writer, config);
		yamlWriter.write(index);
		System.out.println(writer.toString());
	}

}
