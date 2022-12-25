package com.example.hairdresser;

import java.util.Date;

public class Reservation {
    private int user_Id;
    private int service_Id;
    private String reservation_Name;
    private int reservation_Quantity;
    private double reservation_Price;
    private Date reservation_Date;


    public Reservation(int user_Id,int service_Id,String reservation_Name, int reservation_Quantity,double reservation_Price, Date reservation_Date) {
        this.user_Id = user_Id;
        this.service_Id = service_Id;
        this.reservation_Name = reservation_Name;
        this.reservation_Quantity = reservation_Quantity;
        this.reservation_Price = reservation_Price;
        this.reservation_Date = reservation_Date;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public int getService_Id() {
        return service_Id;
    }

    public void setService_Id(int service_Id) {
        this.service_Id = service_Id;
    }

    public String getReservation_Name() {
        return reservation_Name;
    }

    public void setReservation_Name(String reservation_Name) {
        this.reservation_Name = reservation_Name;
    }

    public int getReservation_Quantity() {
        return reservation_Quantity;
    }

    public void setReservation_Quantity(int reservation_Quantity) {
        this.reservation_Quantity = reservation_Quantity;
    }

    public double getReservation_Price() {
        return reservation_Price;
    }

    public void setReservation_Price(double reservation_Price) {
        this.reservation_Price = reservation_Price;
    }

    public Date getReservation_Date() {
        return reservation_Date;
    }

    public void setReservation_Date(Date reservation_Date) {
        this.reservation_Date = reservation_Date;
    }


}
