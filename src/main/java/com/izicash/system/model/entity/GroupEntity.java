package com.izicash.system.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "grupo")
public class GroupEntity extends RelationalEntity {

    @Id
    @Column(name = "group_id")
    private int id;

    @Column(name = "grupo")
    private String group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
