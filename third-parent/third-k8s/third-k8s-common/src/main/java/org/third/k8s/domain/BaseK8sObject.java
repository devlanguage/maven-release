package org.third.k8s.domain;

public abstract class BaseK8sObject {
	private String apiVersion;
	private String kind;
	private BaseK8sMetadata metadata;
	private BaseK8sSpec spec;

}
