package com.example.loggerdoc;

public class BodyLocationPhoto implements Photo {

    private String file;

    @Override
    public String getPhoto() {
        return this.file;
    }

    @Override
    public void setPhoto(String newPhoto) {
        this.file = newPhoto;
    }
}