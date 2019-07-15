package org.basic.net.c20_jmx.jdmk.hello;

public class HelloWorld implements HelloWorldMBean {
	public String hello;

	public HelloWorld() {
		this.hello = "Hello World! I am a Standard MBean";
	}

	public HelloWorld(String hello) {
		this.hello = hello;
	}


	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}

	public void sayHello() {
		System.out.println(hello);
	}
}
