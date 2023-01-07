package com.example.hairdresser;

import java.util.Date;

/**
 * Represent a serice in database
 */
public class Service {
    private String service_Name;
    private double service_Price;
    private Date service_Date;
    private String service_Status;

    /**
     * Create Service object with parametrs
     * @param service_Name
     * @param service_Price
     * @param service_Date
     * @param service_Status
     */
    public Service(String service_Name, double service_Price, Date service_Date,String service_Status) {
        this.service_Name = service_Name;
        this.service_Price = service_Price;
        this.service_Date = service_Date;
        this.service_Status = service_Status;
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

    public String getService_Status() {
        return service_Status;
    }

    public void setService_Status(String service_Status) {
        this.service_Status = service_Status;
    }
}
