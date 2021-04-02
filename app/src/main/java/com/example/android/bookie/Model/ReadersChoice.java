package com.example.android.bookie.Model;

public class ReadersChoice {

    //firebase data receiver

    String name;
    String author;
    String imageUrl;
    int price;
    int rating;

    public ReadersChoice() {
    }

    public ReadersChoice(String name,String author, String imageUrl, int price, int rating) {
        this.name = name;
        this.author = author;
        this.imageUrl = imageUrl;
        this.price = price;
        this.rating = rating;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

