package org.third.bytecode.cglib.book.no_auth;

public class BookCustomerTest {

    public static void main(String[] args) {

        BookCustomerTest c = new BookCustomerTest();
        c.manageBookWithoutAuth();
    }

    /**
     * ģ�⣺û���κ�Ȩ��Ҫ���κ��˶����Բ���
     */
    public void manageBookWithoutAuth() {

        System.out.println("####any one can manage the book");
        BookManager manager = BookManager.getInstance();
        doCRUD(manager);
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
