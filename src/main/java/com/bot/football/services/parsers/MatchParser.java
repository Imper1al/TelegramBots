package com.bot.football.services.parsers;

import com.bot.football.models.MatchModel;
import com.bot.football.dto.MatchDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MatchParser implements Parser<MatchModel> {

    private final Document document;
    private final MatchDTO matchDTO;

    public MatchParser(String url, MatchDTO matchDTO) {
        this.document = getDocument(url);
        this.matchDTO = matchDTO;
    }

    @Override
    public String getCurrentDate() {
        Elements elements = document.getElementsByClass(matchDTO.getEventDate());
        if(elements.size() > 0) {
            return elements.get(0).text();
        }
        return "Today";
    }

    @Override
    public List<MatchModel> getMatches() {
        Elements online = document.getElementsByClass(matchDTO.getOnlineMatch());
        Elements offline = document.getElementsByClass(matchDTO.getOfflineMatch());
        Elements fullTime = document.getElementsByClass(matchDTO.getFullTimeMatch());
        List<String> matches = getMatches(online.text() + " " + offline.text() + " " + fullTime.text());
        List<MatchModel> matchModels = new ArrayList<>();
        if (matches.size() != 0) {
            for(String match : matches) {
                MatchModel matchModel = new MatchModel();
                matchModel.setMatch(match);
                matchModels.add(matchModel);
            }
        }
        return matchModels;
    }

    public static List<String> getMatches(String text) {
        List<String> matches = Arrays.asList(text.split("\\s(?=\\d+’)|(?=\\d+2:\\d+)|(?=FT)|(?=перерва)"));
        return matches.stream()
                .map(line -> line.replaceAll("Анонс", ""))
                .map(line -> line.replaceAll("Онлайн", ""))
                .map(line -> line.replaceAll("Огляд", ""))
                .map(line -> line.replaceAll("Прес-конференція", ""))
                .map(line -> line.replaceAll("Відео", ""))
                .map(line -> line.replaceAll("Проходить далі", ""))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public Document getDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return document;
    }
}
