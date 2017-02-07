package com.example.model;

import java.util.Date;

/**
 * Created by shijian on 06/02/2017.
 */
public class Employee {
    private int id;
    private String name;
    private String address;
    private int salary;
    private Date join_date;

    public int getId() { return id; }

    public Employee(int id, String name, String address, int salary, Date join_date) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.join_date = join_date;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", join_date=" + join_date +
                '}';
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Date join_date) {
        this.join_date = join_date;
    }
}
