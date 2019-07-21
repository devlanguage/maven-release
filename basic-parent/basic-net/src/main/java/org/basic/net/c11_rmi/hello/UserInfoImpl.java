package org.basic.net.c11_rmi.hello;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.basic.net.persistence.UserInfo;
import org.basic.net.persistence.UserInfo.UserRole;

/**
 * 扩展了UnicastRemoteObject类，并实现远程接口 HelloInterface
 */
public class UserInfoImpl //
        extends UnicastRemoteObject // If not inherit this class, related stub class should exported mannually
        implements UserInfoInterface, Serializable {

    protected UserInfoImpl() throws RemoteException {
        super();
    }

    /**
     * 
     */
    private static final long serialVersionUID = 980183237264230139L;

    public UserInfo getUserInfo() throws RemoteException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(111);
        userInfo.setRole(UserRole.Admin);
        userInfo.setPassword("888888");
        userInfo.setEmail("admin@zyujie.com");
        userInfo.setDepartment("信息化建议中心");
        return userInfo;
    }

    public String getUserName() throws RemoteException {
        return "系统管理员";
    }

    public String getUserPassword() throws RemoteException {
        return "888888";
    }

    /*
     * 获取用户基本信息，此方法返回值是一个map
     */
    public Map<String, String> getUserInfoMap() throws RemoteException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserId", "admin");
        map.put("UserPassword", "888888");
        return map;
    }

    /*
     * 获取用户基本信息，此方法返回值是一个List套javabean
     */
    public List<UserInfo> getUserInfoListBean() throws RemoteException {
        List<UserInfo> list = new ArrayList<UserInfo>();
        UserInfo userOne = new UserInfo();
        userOne.setUserId(111);
        userOne.setUserName("用户111");
        userOne.setPassword("111111");
        userOne.setEmail("userOne@zyujie.com");
        userOne.setDepartment("行政部");
        list.add(userOne);

        UserInfo userTwo = new UserInfo();
        userTwo.setUserId(222);
        userTwo.setUserName("用户222");
        userTwo.setPassword("222222");
        userTwo.setEmail("userTwo@zyujie.com");
        userTwo.setDepartment("人力资源部");
        list.add(userTwo);

        UserInfo userThree = new UserInfo();
        userThree.setUserId(333);
        userThree.setUserName("用户333");
        userThree.setPassword("333333");
        userThree.setEmail("userThree@zyujie.com");
        userThree.setDepartment("销售部");
        list.add(userThree);
        return list;
    }

    /*
     * 获取用户基本信息，此方法返回值是一个List套map的方式
     */
    public List<Map<String, String>> getCityInfoListMap() throws RemoteException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> mapSY = new HashMap<String, String>();
        mapSY.put("cityName", "沈阳");
        mapSY.put("cityArea", "东北");
        list.add(mapSY);
        Map<String, String> mapBJ = new HashMap<String, String>();
        mapBJ.put("cityName", "北京");
        mapBJ.put("cityArea", "华北");
        list.add(mapBJ);
        Map<String, String> mapSH = new HashMap<String, String>();
        mapSH.put("cityName", "上海");
        mapSH.put("cityArea", "华东");
        list.add(mapSH);
        Map<String, String> mapGZ = new HashMap<String, String>();
        mapGZ.put("cityName", "广州");
        mapGZ.put("cityArea", "华南");
        list.add(mapGZ);
        Map<String, String> mapXA = new HashMap<String, String>();
        mapXA.put("cityName", "西安");
        mapXA.put("cityArea", "西北");
        list.add(mapXA);
        Map<String, String> mapCD = new HashMap<String, String>();
        mapCD.put("cityName", "成都");
        mapCD.put("cityArea", "西南");
        list.add(mapCD);
        return list;
    }
}
