package com.company.cees.entity.email;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "CEES_EMAIL_TEMPLATE")
@Entity(name = "cees$EmailTemplate")
public class EmailTemplate extends StandardEntity {
    private static final long serialVersionUID = 7629249055164456613L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "TITLE")
    protected String title;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}