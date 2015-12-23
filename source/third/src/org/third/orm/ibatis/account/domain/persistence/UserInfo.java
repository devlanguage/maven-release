/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.pojo.UserInfo.java is created on 2008-6-18
 */
package org.third.orm.ibatis.account.domain.persistence;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    public enum UserRole implements Serializable {

        Admin, GUEST, UNKOWN;
        private static final long serialVersionUID = -5543247869269809877L;
    }

    private static final long serialVersionUID = -5543247869269809877L;
    private Integer userId;
    private Date regDate;
    private Date lastLogDate;
    private Integer logTimes;

    private String userName = "Guest";
    private String password = "Guess";
    private String email;
    private String department;
    private UserRole role = UserRole.UNKOWN;

    public UserInfo() {

    }

    public void setUserId(Integer userId) {

        this.userId = userId;
    }

    public void setRegDate(Date regDate) {

        this.regDate = regDate;
    }

    public void setLastLogDate(Date lastLogDate) {

        this.lastLogDate = lastLogDate;
    }

    public void setLogTimes(Integer logTimes) {

        this.logTimes = logTimes;
    }

    public Integer getUserId() {

        return userId;
    }

    public Date getRegDate() {

        return regDate;
    }

    public Date getLastLogDate() {

        return lastLogDate;
    }

    public Integer getLogTimes() {

        return logTimes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}