package com.example.hairdresser;

import java.util.Date;

/**
 * Represent a serice in database
 *
 * @author Kamil Matusz
 */
public class Service {
    private String service_Name;
    private double service_Price;
    private Date service_Date;
    private String service_Status;

    /**
     * Create Service object with parametrs
     *
     * @param service_Name   A string contains service name
     * @param service_Price  A double contains service price
     * @param service_Date   A date contains service date
     * @param service_Status A string contains service status
     */
    public Service(String service_Name, double service_Price, Date service_Date, String service_Status) {
        this.service_Name = service_Name;
        this.service_Price = service_Price;
        this.service_Date = service_Date;
        this.service_Status = service_Status;
    }

    /**
     * Gets service name
     *
     * @return A string representing service name
     */
    public String getService_Name() {
        return service_Name;
    }

    /**
     * Sets the service name
     *
     * @param service_Name A string contains service name
     */
    public void setService_Name(String service_Name) {
        this.service_Name = service_Name;
    }

    /**
     * Gets service price
     *
     * @return A double representing service price
     */
    public double getService_Price() {
        return service_Price;
    }

    /**
     * Sets the service price
     *
     * @param service_Price A double contains service price
     */
    public void setService_Price(double service_Price) {
        this.service_Price = service_Price;
    }

    /**
     * Gets service date
     *
     * @return A date representing service date
     */
    public Date getService_Date() {
        return service_Date;
    }

    /**
     * Sets service date
     *
     * @param service_Date A date contains service date
     */
    public void setService_Date(Date service_Date) {
        this.service_Date = service_Date;
    }

    /**
     * Gets service status
     *
     * @return A string representing servide status
     */
    public String getService_Status() {
        return service_Status;
    }

    /**
     * Sets service status
     *
     * @param service_Status A string contains service status
     */
    public void setService_Status(String service_Status) {
        this.service_Status = service_Status;
    }
}
