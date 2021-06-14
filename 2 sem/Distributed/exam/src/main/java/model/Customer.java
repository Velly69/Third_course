package model;

import java.io.Serializable;

public class Customer implements Serializable, Comparable<Customer> {
    private String id;
    private String name;
    private String surname;
    private String address;
    private Integer cardNumber;
    private String bankAccount;

    public Customer(String id, String name, String surname, String address, Integer cardNumber, String bankAccount) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.cardNumber = cardNumber;
        this.bankAccount = bankAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public int compareTo(Customer o) {
        return this.surname.compareTo(o.getSurname());
    }
}


