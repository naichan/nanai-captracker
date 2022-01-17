package com.mangago.captracker.model;

public class Chapter {
    private String name;
    private String date;
    private String url;

    public Chapter(String name, String date, String url) {
        this.name = name;
        this.date = date;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
