package org.third.ssh;

/**
 * Created by liguoq on 8/20/2017.
 */
public class CheckNodeResult {

  private String checkType;

  private boolean checkResult;

  private  int ExitStatus;

  private StringBuffer info;

  public String getCheckType() {
    return checkType;
  }

  public void setCheckType(String checkType) {
    this.checkType = checkType;
  }

  public boolean isCheckResult() {
    return checkResult;
  }

  public void setCheckResult(boolean checkResult) {
    this.checkResult = checkResult;
  }

  public int getExitStatus() {
    return ExitStatus;
  }

  public void setExitStatus(int exitStatus) {
    ExitStatus = exitStatus;
  }

  public StringBuffer getInfo() {
    return info;
  }

  public void setInfo(StringBuffer info) {
    this.info = info;
  }

  public CheckNodeResult(boolean checkResult, int exitStatus, StringBuffer info) {
    this.checkResult = checkResult;
    ExitStatus = exitStatus;
    this.info = info;
  }

  public CheckNodeResult(String checkType, boolean checkResult, int exitStatus,
      StringBuffer info) {
    this.checkType = checkType;
    this.checkResult = checkResult;
    ExitStatus = exitStatus;
    this.info = info;
  }

  public CheckNodeResult() {
  }

  @Override
  public String toString() {
    return "CheckNodeResult{" +
        "checkType='" + checkType + '\'' +
        ", checkResult=" + checkResult +
        ", ExitStatus=" + ExitStatus +
        ", info=" + info +
        '}';
  }
}
