package com.mangago.captracker.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class Manga {
    private String name;
    private String url;
    private String img;
    private List<Chapter> newChapters = new ArrayList<>();

    public Manga() {}

    public Manga(String name, String url, String img) {
        this.name = name;
        this.url = url;
        this.img = img;
    }

    public void addChapter(String name, String date, String link) {
        this.newChapters.add(new Chapter(name, date, link));
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImg() {
        return img;
    }

    public List<Chapter> getNewChapters() {
        return Collections.unmodifiableList(newChapters);
    }

    @Override
    public String toString() {
        return "Manga{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", newChapters=" + newChapters +
                '}';
    }
}
