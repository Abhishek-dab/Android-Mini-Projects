package com.example.asynctask;

public class Book {
    private String title, authors, imageLink,s,price;

    public Book(String title, String authors, String imageLink, String s, String price) {
        this.title = title;
        this.authors = authors;
        this.imageLink = imageLink;
        this.s = s;
        this.price = price;

    }

    public String getTitle() {
        return title;
    }
    public String getURL() {
        return s;
    }
    public String getPrice() {
        return price;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
