package org.third.hibernate.migrate.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.context.annotation.Primary;

@Entity
public class Account {

    Integer accountId;
    String accountName;

    @Primary
    @Id
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}
