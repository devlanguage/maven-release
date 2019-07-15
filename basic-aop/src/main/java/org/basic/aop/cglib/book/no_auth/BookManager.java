package org.basic.aop.cglib.book.no_auth;

public class BookManager {

    private static BookManager manger = new BookManager();
    private BookManager() {
    }
    public static BookManager getInstance() {
        return manger;
    }

    // ģ���ѯ����
    public void query() {

        System.out.println("query");
    }

    // ģ�ⴴ������
    public void create() {

        System.out.println("create");
    }

    // ģ����²���
    public void update() {

        System.out.println("update");
    }

    // ģ��ɾ�����
    public void delete() {

        System.out.println("delete");
    }

}
