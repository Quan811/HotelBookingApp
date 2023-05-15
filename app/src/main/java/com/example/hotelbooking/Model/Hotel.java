package com.example.hotelbooking.Model;


public class Hotel {
    private String img_url,name,location,price,discount,room_available, room_booked;

    public String getRoom_available() {
        return room_available;
    }

    public void setRoom_available(String room_available) {
        this.room_available = room_available;
    }

    public String getRoom_booked() {
        return room_booked;
    }

    public void setRoom_booked(String room_booked) {
        this.room_booked = room_booked;
    }

    public Hotel(String img_url, String name, String location, String price, String discount, String room_available, String room_booked) {
        this.img_url = img_url;
        this.name = name;
        this.location = location;
        this.price = price;
        this.discount = discount;
        this.room_available = room_available;
        this.room_booked = room_booked;
    }

    public Hotel() {
    }

    public Hotel(String name, String img_url, String location, String price, String discount) {
        this.img_url = img_url;
        this.name = name;
        this.location = location;
        this.price = price;
        this.discount = discount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
