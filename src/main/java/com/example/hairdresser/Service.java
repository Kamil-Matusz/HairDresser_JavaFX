package com.example.hairdresser;

import java.util.Date;

public class Service {
    private int ServiceId;
    private String Name;
    private Double Price;
    private Date Date;

    public Service(int ServiceId, String Name, Double Price, Date Date) {
        this.ServiceId = ServiceId;
        this.Name = Name;
        this.Price = Price;
        this.Date = Date;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}
