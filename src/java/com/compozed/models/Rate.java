package com.compozed.models;

import javax.persistence.*;

@Entity
@Table(name = "rates")
@Access(AccessType.PROPERTY)
public class Rate {
    private int id;
    private String field;
    private String value;
    private float amount;

    public Rate() {
    }

    public Rate(float amount, String value, String field) {
        this.amount = amount;
        this.value = value;
        this.field = field;
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
    @Column(name = "field", nullable = false, length = 10)
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 3)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "amount", nullable = false)
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
