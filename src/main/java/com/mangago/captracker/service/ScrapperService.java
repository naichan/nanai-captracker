package com.mangago.captracker.service;

import com.mangago.captracker.model.Manga;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScrapperService {
    private static final String url = "https://www.mangago.me/home/mangalist/1683307/?filter=&page=";
    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("LLL d, yyyy")
            .toFormatter()
            .withLocale(Locale.forLanguageTag("en"));
    private static final Comparator<Manga> comp = (o1, o2) -> {
        LocalDate o1Date = LocalDate.parse(o1.getNewChapters().get(0).getDate(), formatter);
        LocalDate o2Date = LocalDate.parse(o2.getNewChapters().get(0).getDate(), formatter);
        return o1Date.isAfter(o2Date) ? -1 : o1Date.isBefore(o2Date) ? 1 : 0;
    };
    private static final List<Manga> mangas = new ArrayList<>();

    public void iterateList() throws IOException {
        for (int i = 1 ; i<= 15; i++) {
            createMangas(url + i);
        }
    }

    public void createMangas(String pageUrl) throws IOException {

        Connection connection = Jsoup.connect(pageUrl)
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .data("query", "Java")
                .userAgent("Mozilla/5.0")
                .cookie("auth", "token")
                .timeout(3000);
        Connection.Response resp = connection.execute();

       if (resp.statusCode() == 200) {
           Document doc = connection
                   .get();
           Elements titles = new Elements(doc.getElementsByClass("title"));
           Elements imgs = new Elements(doc.getElementsByClass("showdesc"));
           Iterator<Element> imgIterator = imgs.stream().iterator();
           for (Element title : titles) {
               String url = title.getElementsByTag("a").attr("abs:href");
               String img = imgIterator.next().getElementsByTag("img").attr("data-src");
               Manga manga = new Manga(title.text(), url, img);
               Document mangaDoc = Jsoup.connect(url)
                       .data("query", "Java")
                       .userAgent("Mozilla/5.0")
                       .cookie("auth", "token")
                       .timeout(3000)
                       .get();
               List<Element> elements = mangaDoc.getElementsByTag("tr");
               for (Element e : elements) {
                   if (e.text().contains("new") && !e.text().contains("Latest")) {
                       String date = e.getElementsByTag("td").get(2).text();
                       LocalDate parsedDate = LocalDate.parse(date, formatter);
                       if (LocalDate.now().compareTo(parsedDate) < 7) {
                           String name = e.getElementsByTag("td").get(0).text();
                           String link = e.getElementsByTag("a").attr("abs:href");
                           manga.addChapter(name, date, link);
                       }
                   }
               }
               if (!manga.getNewChapters().isEmpty()) {
                   mangas.add(manga);
               }
           }
       } else { throw new IOException("Erro conectando à lista: "
               + resp.statusMessage()
               + " " + resp.statusCode()); }
   }

   public List<Manga> getMangas() {
        List<Manga> sortedMangas = mangas.stream().sorted(comp).collect(Collectors.toList());
        return Collections.unmodifiableList(sortedMangas);
   }

   /**
   public void sendMangas() throws IOException {
       for (String json : mangasJson) {
           CloseableHttpClient client = HttpClients.createDefault();
           HttpPost httpPost = new HttpPost("");
           StringEntity entity = new StringEntity(json);
           httpPost.setEntity(entity);
           httpPost.setHeader("Accept", "application/json");
           httpPost.setHeader("Content-type", "application/json");
           CloseableHttpResponse response = client.execute(httpPost);
           client.close();
       }
   }

    public String converterToJson(Manga manga) {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return mapper.writeValueAsString(manga);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    **/
}

