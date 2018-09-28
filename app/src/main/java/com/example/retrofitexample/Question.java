package com.example.retrofitexample;

/**
 * Created by nvtamcntt on 2018/09/28.
 */

public class Question {
    private String title;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString(){
        return (title);
    }
}
