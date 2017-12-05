package com.loveskai.model;

import com.google.gson.Gson;

public class Request {
    private static Gson gson = new Gson();
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String returnDate;
    private String cabinClass;

    public String getDepartureAirport() {
        return departureAirport;
    }

    public Request setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
        return this;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public Request setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
        return this;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public Request setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public Request setReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public Request setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
        return this;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
