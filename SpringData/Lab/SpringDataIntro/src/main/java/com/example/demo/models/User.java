package com.example.demo.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    private int age;

    @OneToMany(targetEntity = Account.class, mappedBy = "user")
    private Set<Account> accounts;

    public User() {
        this.accounts = new HashSet<>();
    }

    public User(String username, int age) {
        this();

        this.username = username;
        this.age = age;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
