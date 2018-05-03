package com.example.admin.infraredcertification;

/**
 * Created by Administrator on 2017/06/01 0001.
 */

public class PadyPicture {
    private String title;
    private boolean isPicture;

    public PadyPicture(String title, boolean isPicture) {
        this.title = title;
        this.isPicture = isPicture;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPicture() {
        return isPicture;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicture(boolean picture) {
        isPicture = picture;
    }
}
