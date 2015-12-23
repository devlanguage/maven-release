package org.basic.net.c11_rmi.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.third.orm.ibatis.account.domain.persistence.UserInfo;

public interface UserInfoInterface extends Remote {

    /*
     * 获取用户名称
     */
    public String getUserName() throws RemoteException;

    /*
     * 获取用户密码
     */
    public String getUserPassword() throws RemoteException;

    /*
     * 获取用户基本信息，此方法返回值是一个JavaBean
     */
    public UserInfo getUserInfo() throws RemoteException;

    /*
     * 获取用户基本信息，此方法返回值是一个map
     */
    public Map<String, String> getUserInfoMap() throws RemoteException;

    /*
     * 获取用户基本信息，此方法返回值是一个List套javabean
     */
    public List<UserInfo> getUserInfoListBean() throws RemoteException;

    /*
     * 获取用户基本信息，此方法返回值是一个List套map的方式
     */
    public List<Map<String, String>> getCityInfoListMap() throws RemoteException;
}