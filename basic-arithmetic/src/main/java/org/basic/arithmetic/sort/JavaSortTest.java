package org.basic.arithmetic.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.basic.arithmetic.UserBean;

public class JavaSortTest {

    public static void main(String[] args) {

        UserBean u1 = new UserBean(1, "test01");
        UserBean u2 = new UserBean(2, "test02");
        UserBean u3 = new UserBean(3, "test03");

        List<UserBean> users = new ArrayList<UserBean>();
        users.add(u3);
        users.add(u1);
        users.add(u2);
        printList(users);
        System.out.println("--------Sort ascendently");
        java.util.Collections.sort(users, new java.util.Comparator<UserBean>() {

            public int compare(UserBean u1, UserBean u2) {

                return u1.getId() - u2.getId();
            }
        });
        printList(users);

        System.out.println("--------Reverse");
        java.util.Collections.reverse(users);
        printList(users);

    }

    private static void printList(Collection<UserBean> list) {

        for (Object obj : list) {
            System.out.println(obj);
        }
    }

}
