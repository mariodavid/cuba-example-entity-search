package com.company.cees.entity.email;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "CEES_EMAIL_MESSAGE")
@Entity(name = "cees$EmailMessage")
public class EmailMessage extends StandardEntity {
    private static final long serialVersionUID = -1658606568551534538L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}