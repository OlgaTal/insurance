package com.compozed.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by localadmin on 8/11/16.
 */
@Entity
@Table(name = "policies")
@Access(AccessType.PROPERTY)
public class Policy {
    private int id;
    private float premium;
    private Client policy_holder;
    private List<Client> drivers;


    public Policy() {
    }

    public Policy(Client policy_holder) {
        this.policy_holder = policy_holder;
        this.premium = 100.1f; //TODO replace value with Rates.calculateRates(Client)
        this.drivers = new ArrayList<>();
        this.drivers.add(policy_holder);
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
    @Column(name = "premium", nullable = false)
    public float getPremium() {
        return premium;
    }

    public void setPremium(float premium) {
        this.premium = premium;
    }
//    @ManyToOne
//    @JoinColumn(name = "shelter_id", referencedColumnName = "id")

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_holder_id", nullable = false, referencedColumnName = "id")
    public Client getPolicy_holder() {
        return policy_holder;
    }

    public void setPolicy_holder(Client policy_holder) {
        this.policy_holder = policy_holder;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "policies_clients",
                joinColumns = {
                    @JoinColumn(name = "p_id",
                            nullable = false,
                            referencedColumnName = "id")
                },
                inverseJoinColumns = {
                    @JoinColumn(name = "c_id",
                            nullable = false,
                            referencedColumnName = "id")
                })
    public List<Client> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Client> drivers) {
        this.drivers = drivers;
    }
}