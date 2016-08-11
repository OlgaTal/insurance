package com.compozed.models;

import com.compozed.enums.Gender;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
@Access(AccessType.PROPERTY)
public class Client {
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private Date birthdate;
    private Gender gender;
    private List<Policy> policies;
    private List<Policy> policiesOn;

    public Client(String name, String address, String city, String state, Date birthdate, Gender gender) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.birthdate = birthdate;
        this.gender = gender;
        this.policies = new ArrayList<>();
        this.policiesOn = new ArrayList<>();
    }

    public Client() {
    }

    public Client(String name, Date birthdate, Gender gender) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.policies = new ArrayList<>();
        this.policiesOn = new ArrayList<>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address", length = 45)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "city", length = 45)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "state", length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", nullable = false)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "policy_holder")
    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "drivers")
    public List<Policy> getPoliciesOn() {
        return policiesOn;
    }

    public void setPoliciesOn(List<Policy> policiesOn) {
        this.policiesOn = policiesOn;
    }
}
