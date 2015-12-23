/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.ibatis.account.persistence.Account_Map.java is created on 2008-6-25
 */
package org.third.orm.ibatis.account.domain.persistence;

/**
 * 
 */
public class Account_Map {

    private int accountID;
    private String accountName;

    /**
     * @return get method for the field accountID
     */
    public int getAccountID() {

        return this.accountID;
    }

    /**
     * @param accountID
     *            the accountID to set
     */
    public void setAccountID(int accountID) {

        this.accountID = accountID;
    }

    /**
     * @return get method for the field accountName
     */
    public String getAccountName() {

        return this.accountName;
    }

    /**
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName) {

        this.accountName = accountName;
    }

}
