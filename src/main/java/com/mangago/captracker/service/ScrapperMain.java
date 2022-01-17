package com.mangago.captracker.service;

import com.mangago.captracker.model.Manga;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

public class ScrapperMain {
    public static void main(String[] args) throws IOException {

        ScrapperService scrapperService = new ScrapperService();
        scrapperService.iterateList();
        List<Manga> mangas = scrapperService.getMangas();
        mangas.stream().forEach(m -> System.out.println(m.getNewChapters().get(0).getDate()));
        /**
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("LLL dd, yyyy")
                .toFormatter()
                .withLocale(Locale.forLanguageTag("en"));
        LocalDate date = LocalDate.parse("Apr 6, 2021", formatter);
        **/
    }

}
