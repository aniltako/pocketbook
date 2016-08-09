package com.pocket.book.model;

import java.util.Date;

/**
 * Created by anil on 8/3/2016.
 */

public class Record {

    private int id;
    private String title;
    private String description;
    private String createdDate;
    private String modifiedDate;
    private String imagePath;

    public Record(){
    }

    public Record(String title, String description, String imagePath){

        this.title = title;
        this.description = description;
        this.imagePath = imagePath;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
