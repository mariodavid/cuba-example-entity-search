package com.company.cees.entity.email;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "CEES_SYSTEM_EMAIL")
@Entity(name = "cees$SystemEmail")
public class SystemEmail extends StandardEntity {
    private static final long serialVersionUID = 4117527416006431311L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}