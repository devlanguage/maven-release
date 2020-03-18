package org.basic.jdk.core.jdk.jdk6.executor;

public class ResultType {

	private String content;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public final static String TASK_IS_DONE = "Task is done, return: ";

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ResultType() {
		this(TASK_IS_DONE);
	}

	public ResultType(String content) {
		this.content = TASK_IS_DONE + content;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":[content=" + this.content + "]";
	}
}
