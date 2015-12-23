package org.third.orm.ibatis.account.domain.persistence;

import java.io.Serializable;

public class User implements Serializable {

    private Integer userId;
    private String userAlias;
    private String password;
    private String realName;
    private String email;
    private String userTypeId;

    public User() {

    }

    public User(Integer userId) {

        this.userId = userId;
    }

    public User(String userAlias) {

        this.userAlias = userAlias;
    }

    public User(String userAlias, String password) {

        this.userAlias = userAlias;
        this.password = password;
    }

    public void setUserId(Integer userId) {

        this.userId = userId;
    }

    public void setUserAlias(String userAlias) {

        this.userAlias = userAlias;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public void setUserTypeId(String userTypeId) {

        this.userTypeId = userTypeId;
    }

    public void setRealName(String realName) {

        this.realName = realName;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public Integer getUserId() {

        return userId;
    }

    public String getUserAlias() {

        return userAlias;
    }

    public String getPassword() {

        return password;
    }

    public String getUserTypeId() {

        return userTypeId;
    }

    public String getRealName() {

        return realName;
    }

    public String getEmail() {

        return email;
    }
}
