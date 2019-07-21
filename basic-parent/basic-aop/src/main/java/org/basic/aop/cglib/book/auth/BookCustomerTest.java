package org.basic.aop.cglib.book.auth;

public class BookCustomerTest {

    public final static String VALID_USER = "validUser";
    public final static String INVALID_USER = "invalidUser";

    public static void main(String[] args) {

        BookCustomerTest c = new BookCustomerTest();
        c.manageBookWithAuth(VALID_USER);
        c.manageBookWithAuth(INVALID_USER);
        
        c.manageBookWithAuthAndFilter(INVALID_USER);
    }

    public void manageBookWithAuth(String userName) {

        System.out.println("ֻ�кϷ����û����ܵ���BookManager�ķ���");
        BookManager noAuthManager = BookManager.getInstance(new UserAuthInterceptor(userName));
        doCRUD(noAuthManager);
        separatorLine();
    }

    public void manageBookWithAuthAndFilter(String userName) {

        System.out.println("������BookManager��query��������ʹ�û��Ƿ���Ҳ���?ִ��");
        BookManager noAuthManager = BookManager.getInstanceWithFilter(new UserAuthInterceptor(userName));
        doCRUD(noAuthManager);
        separatorLine();
    }

    /**
     * ��Info����ӣ����£�ɾ���ѯ����
     * 
     * @param manager
     */
    private void doCRUD(BookManager manager) {

        manager.create();
        manager.update();
        manager.delete();
        manager.query();
    }

    /**
     * ��һ��ָ��У��������
     */
    private void separatorLine() {

        System.out.println("################################");
    }

}
