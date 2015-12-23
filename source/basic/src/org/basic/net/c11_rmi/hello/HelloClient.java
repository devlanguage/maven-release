package org.basic.net.c11_rmi.hello;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

import org.third.orm.ibatis.account.domain.persistence.UserInfo;

public class HelloClient {

    public static void main(String[] args) {
        try {

            // 返回指定的 host 和 port 上对远程对象 Registry 的引用。如果 host 为 null，则使用本地主机。
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", HelloServer.rmiregistry_port);
            // 返回注册表中绑定到指定 name 的远程引用
            UserInfoInterface userInfoInterface = (UserInfoInterface) registry.lookup("Hello");

            System.out.println("/*****************RMI String 返回********************/");
            System.out.println("用户名称：" + userInfoInterface.getUserName());
            System.out.println("用户密码：" + userInfoInterface.getUserPassword());

            System.out.println("/*****************RMI JavaBean 返回********************/");
            UserInfo userInfo = userInfoInterface.getUserInfo();
            System.out.println("用户部门：" + userInfo.getDepartment());
            System.out.println("用户邮箱号：" + userInfo.getEmail());

            System.out.println("/*****************RMI Map 返回********************/");
            Map<String, String> map = userInfoInterface.getUserInfoMap();
            System.out.println("Map用户编号：" + map.get("UserId"));
            System.out.println("Map用户密码：" + map.get("UserPassword"));

            System.out.println("/*****************RMI List<Object> List套JavaBean 返回********************/");
            List<UserInfo> userList = userInfoInterface.getUserInfoListBean();
            for (int i = 0; i < userList.size(); i++) {
                UserInfo users = (UserInfo) userList.get(i);
                System.out.println("用户名称：" + users.getUserName() + "--部门：" + users.getDepartment() + "--邮箱：" + users.getEmail());
            }

            System.out.println("/*****************RMI List套Map 返回********************/");
            List<Map<String, String>> cityList = userInfoInterface.getCityInfoListMap();
            for (int i = 0; i < cityList.size(); i++) {
                Map<String, String> mapCity = (Map<String, String>) cityList.get(i);
                System.out.println("城市名称：" + mapCity.get("cityName") + "----区域：" + mapCity.get("cityArea"));
            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
