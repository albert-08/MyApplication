package com.example.myapplication.database;

public class Companies {

    private String name ;
    private String description;
    private String rating;
    private String image_url;

    public Company() {
    }

    public Company(String name, String description, String rating, String image_url) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.image_url = image_url;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getImage_url() {
        return image_url;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
