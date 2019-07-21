package org.basic.io.yaml.chart;

import java.util.List;
import java.util.Map;

public class ChartRelease {
	private String apiVersion = "v1";
	private String appVersion = "2.1.1";
	private String version = "2.1.3";
	private String name = "2.1.3";
	private String home = "2.1.3";
	private String engine = "2.1.3";
	private String deprecated = "2.1.3";
	private String created = "2018-03-05T19:55:33.568152+08:00";
	private String description = "Scales worker nodes within agent pools";
	private String digest = "5c016fefd8942b008abf41f282906d055f4a61f650ad125746843e62efc01bd0";
	private String icon = "https://github.com/kubernetes/kubernetes/blob/master/logo/logo.png";
//	private List<Maintainer> maintainers;
	private Maintainer[] maintainers;
//	private List<Map<String, String>> maintainers;
	private List<String> sources;
	private List<String> urls;
	private List<String> keywords;

	public String getApiVersion() {
		return apiVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getCreated() {
		return created;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public String getDescription() {
		return description;
	}

	public String getDigest() {
		return digest;
	}

	public String getEngine() {
		return engine;
	}

	public String getHome() {
		return home;
	}

	public String getIcon() {
		return icon;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public String getName() {
		return name;
	}

	public List<String> getSources() {
		return sources;
	}

	public List<String> getUrls() {
		return urls;
	}

	public String getVersion() {
		return version;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}


	public Maintainer[] getMaintainers() {
		return maintainers;
	}

	public void setMaintainers(Maintainer[] maintainers) {
		this.maintainers = maintainers;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
