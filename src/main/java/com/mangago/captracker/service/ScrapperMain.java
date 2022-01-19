package com.mangago.captracker.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrapperMain {
    public static void main(String[] args) throws IOException {
        ScrapperService scrapperService = new ScrapperService();
        scrapperService.iterateList();
        scrapperService.getMangas().stream().forEach(m -> System.out.println(m.getName()));
    }

}
