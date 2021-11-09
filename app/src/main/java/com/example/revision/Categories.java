package com.example.revision;

public class Categories {
    private String catergoryId, catergoryName, catergoryImage;

    public Categories(String catergoryId, String catergoryName, String catergoryImage) {
        this.catergoryId = catergoryId;
        this.catergoryName = catergoryName;
        this.catergoryImage = catergoryImage;
    }

    public Categories(){}

    public String  Categories(){
        return catergoryId;
    }
    public void setCatergoryId(String catergoryId){
        this.catergoryId = catergoryId;
    }

    public String getCatergoryName(){
        return catergoryName;
    }
    public void setCatergoryName(String catergoryName){
        this.catergoryName = catergoryName;
    }

    public String getCatergoryImage() {
        return catergoryImage;
    }

    public void setCatergoryImage(String catergoryImage) {
        this.catergoryImage = catergoryImage;
    }
}
