package com.example.storyteller.Model;

public class ItemData {
    private String image,name;

    public ItemData(){

    }
    public ItemData(String image, String name) {
        this.image = image;
        this.name = name;

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
}
