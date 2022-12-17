package com.example.hairdresser;

import java.util.Date;

public class Service {
    private int service_Id;
    private String service_Name;
    private double service_Price;
    private Date service_Date;

    public Service(int service_Id, String service_Name, double service_Price, Date service_Date) {
        this.service_Id = service_Id;
        this.service_Name = service_Name;
        this.service_Price = service_Price;
        this.service_Date = service_Date;
    }

    public int getService_Id() {
        return service_Id;
    }

    public void setService_Id(int service_Id) {
        this.service_Id = service_Id;
    }

    public String getService_Name() {
        return service_Name;
    }

    public void setService_Name(String service_Name) {
        this.service_Name = service_Name;
    }

    public double getService_Price() {
        return service_Price;
    }

    public void setService_Price(double service_Price) {
        this.service_Price = service_Price;
    }

    public Date getService_Date() {
        return service_Date;
    }

    public void setService_Date(Date service_Date) {
        this.service_Date = service_Date;
    }
}
