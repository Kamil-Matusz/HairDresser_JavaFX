package com.example.hairdresser;

import java.util.Date;

/**
 * Class which represents a reservation in hairdresser
 *
 * @author Kamil Matusz
 */
public class Reservation {
    private int user_Id;
    private String service_Name;
    private int reservation_Quantity;
    private double reservation_Price;
    private Date reservation_Date;
    private String phone_Number;
    private String email;

    /**
     * Creating new reservations with parametrs
     *
     * @param user_Id
     * @param service_Name
     * @param reservation_Quantity
     * @param reservation_Price
     * @param reservation_Date
     * @param email
     * @param phone_Number
     */
    public Reservation(int user_Id,String service_Name, int reservation_Quantity, double reservation_Price, Date reservation_Date,String phone_Number,String email) {
        this.user_Id = user_Id;
        this.service_Name = service_Name;
        this.reservation_Quantity = reservation_Quantity;
        this.reservation_Price = reservation_Price;
        this.reservation_Date = reservation_Date;
        this.phone_Number = phone_Number;
        this.email = email;
    }

    /**
     * Gets user id
     * @return A int representing user id
     */
    public int getUser_Id() {
        return user_Id;
    }

    /**
     * Sets user id
     * @param user_Id A string contains service name
     */
    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    /**
     * Gets service name
     * @return A string representing reservation name
     */
    public String getReservation_Name() {
        return service_Name;
    }

    /**
     * Sets service name
     * @param service_Name A string contains reservation name
     */
    public void setReservation_Name(String service_Name) {
        this.service_Name = service_Name;
    }

    /**
     * Gets reservation quantity
     * @return A int representing reservation quantity
     */
    public int getReservation_Quantity() {
        return reservation_Quantity;
    }

    /**
     * Sets reservation quantity
     * @param reservation_Quantity A int contains reservation quantity
     */
    public void setReservation_Quantity(int reservation_Quantity) {
        this.reservation_Quantity = reservation_Quantity;
    }

    /**
     * Gets reservation price
     * @return A double representing reservation quantity
     */
    public double getReservation_Price() {
        return reservation_Price;
    }

    /**
     * Sets reservation price
     * @param reservation_Price A double contains reservation price
     */
    public void setReservation_Price(double reservation_Price) {
        this.reservation_Price = reservation_Price;
    }

    /**
     * Gets reservation date
     * @return A date representing reservation date
     */
    public Date getReservation_Date() {
        return reservation_Date;
    }

    /**
     * Sets reservation date
     * @param reservation_Date A date contains reservation date
     */
    public void setReservation_Date(Date reservation_Date) {
        this.reservation_Date = reservation_Date;
    }

    /**
     * Gets phone number
     * @return A string representing client phone number
     */
    public String getPhone_Number() {
        return phone_Number;
    }

    /**
     * Sets phone number
     * @param phone_Number A string contains phone number
     */
    public void setPhone_Number(String phone_Number) {
        this.phone_Number = phone_Number;
    }

    /**
     * Gets email
     * @return A string representing client's email addreess
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user address email
     * @param email A string contains email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

   /* *//**
     * Gets email
     * @return A int representing service identificator
     *//*
    public int getServiceId() {
        return service_Id;
    }

    *//**
     * Sets user address email
     * @param service_Id A string contains service identificator
     *//*
    public void setServiceId(int service_Id) {
        this.service_Id = service_Id;
    }*/
}
