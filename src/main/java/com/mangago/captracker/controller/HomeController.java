package com.mangago.captracker.controller;

import com.mangago.captracker.model.Manga;
import com.mangago.captracker.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private ScrapperService scrapperService;

    @GetMapping
    public String home(Model model) throws IOException {
        scrapperService.iterateList();
        List<Manga> mangas = scrapperService.getMangas();
        model.addAttribute("mangas", mangas);
        return "tracker";
    }
}
