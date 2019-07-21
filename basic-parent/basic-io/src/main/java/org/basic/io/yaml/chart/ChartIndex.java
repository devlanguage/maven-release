package org.basic.io.yaml.chart;

import java.util.Map;

public class ChartIndex {
	private String apiVersion;
	private String generated;

	private Map<String, ChartRelease[]> entries;

	public String getApiVersion() {
		return apiVersion;
	}

	public Map<String, ChartRelease[]> getEntries() {
		return entries;
	}

	public String getGenerated() {
		return generated;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public void setEntries(Map<String, ChartRelease[]> entries) {
		this.entries = entries;
	}

	public void setGenerated(String generated) {
		this.generated = generated;
	}

	@Override
	public String toString() {
		return super.toString() + ":" + apiVersion;
	}

}
