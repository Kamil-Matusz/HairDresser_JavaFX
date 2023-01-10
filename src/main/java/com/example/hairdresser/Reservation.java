package com.example.hairdresser;

import java.util.Date;

/**
 *  Class which represents a reservation in hairdresser
 * @author Kamil Matusz
 */
public class Reservation {
    private int user_Id;
    private int service_Id;
    private String reservation_Name;
    private int reservation_Quantity;
    private double reservation_Price;
    private Date reservation_Date;

    /**
     * Creating new reservations with parametrs
     * @param user_Id
     * @param service_Id
     * @param reservation_Name
     * @param reservation_Quantity
     * @param reservation_Price
     * @param reservation_Date
     */
    public Reservation(int user_Id,int service_Id,String reservation_Name, int reservation_Quantity,double reservation_Price, Date reservation_Date) {
        this.user_Id = user_Id;
        this.service_Id = service_Id;
        this.reservation_Name = reservation_Name;
        this.reservation_Quantity = reservation_Quantity;
        this.reservation_Price = reservation_Price;
        this.reservation_Date = reservation_Date;
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
     * Gets service id
     * @return A int representing service id
     */
    public int getService_Id() {
        return service_Id;
    }

    /**
     * Sets service id
     * @param service_Id A int contains service id
     */
    public void setService_Id(int service_Id) {
        this.service_Id = service_Id;
    }

    /**
     * Gets reservation name
     * @return A string representing reservation name
     */
    public String getReservation_Name() {
        return reservation_Name;
    }

    /**
     * Sets reservation name
     * @param reservation_Name A string contains reservation name
     */
    public void setReservation_Name(String reservation_Name) {
        this.reservation_Name = reservation_Name;
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


}
