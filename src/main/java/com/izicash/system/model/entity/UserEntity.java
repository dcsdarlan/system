package com.izicash.system.model.entity;

import com.izicash.system.model.entity.types.BooleanType;
import com.izicash.system.model.entity.types.StringNullableType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name="sq_user", sequenceName="sq_user", allocationSize=1, initialValue=0)
public class UserEntity extends RelationalEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sq_user")
    private long id;

    @Column(name = "nome")
    private String name;

    //@JsonIgnore
    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String password;

    @Column(name = "ativo")
    private int statusActive;

    @Column(name = "lojas")
    private String stores;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_group",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="user_id")},
            inverseJoinColumns={@JoinColumn(name="group_id", referencedColumnName="group_id")})
    private List<GroupEntity> groups;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id", referencedColumnName="company_id")
    private CompanyEntity company;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(int statusActive) {
        this.statusActive = statusActive;
    }

    public String getStores() {
        return stores;
    }

    public void setStores(String stores) {
        this.stores = stores;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }
}
