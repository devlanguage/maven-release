package org.third.orm.ibatis.account.dao;

import java.util.List;

import org.third.orm.ibatis.account.domain.persistence.User;

public interface UserDao extends BaseDao  {

    public abstract List getUser(String userAlias);

    public abstract List getUserByEmail(String email);

    public abstract List getUser(String userAlias, String password);

    public abstract void insertUser(User user);

    public abstract void updateUser(User user);

    public abstract boolean existUserId(Integer userid);

    public abstract List<User> listUsers();
}