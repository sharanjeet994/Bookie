package com.example.android.bookie;

public class Word {

private String name;
private String author;
private int price;
private String image;

public  Word(){

}

    public Word(String name, String author, int price,String image) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
