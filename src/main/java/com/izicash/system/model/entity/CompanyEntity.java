package com.izicash.system.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "empresa")
@SequenceGenerator(name="sq_company", sequenceName="sq_company", allocationSize=1, initialValue=0)
public class CompanyEntity extends RelationalEntity {

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sq_company")
    private int id;

    @Column(name = "empresa")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
