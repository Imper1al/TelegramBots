package com.bot.football.services.parsers;

import com.bot.football.dto.MatchTranslationsDTO;
import com.bot.football.models.MatchTranslationModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchTranslationParser implements Parser<MatchTranslationModel> {

    private final Document document;
    private final MatchTranslationsDTO matchTranslationsDTO;

    public MatchTranslationParser(String url, MatchTranslationsDTO matchTranslationsDTO) {
        this.document = getDocument(url);
        this.matchTranslationsDTO = matchTranslationsDTO;
    }

    @Override
    public String getCurrentDate() {
        Elements elements = document.getElementsByClass(matchTranslationsDTO.getEventDate());
        if (elements.size() > 0) {
            return elements.get(0).text();
        }
        return "Today";
    }

    @Override
    public List<MatchTranslationModel> getMatches() {
        Elements gamesTable = document.getElementsByClass(matchTranslationsDTO.getGamesTable());
        List<String> games = getGames(gamesTable.text());
        List<String> links = getLinks(gamesTable.html());
        List<MatchTranslationModel> matches = new ArrayList<>();
        if (games.size() != 0 && games.size() == links.size()) {
            for (int i = 0; i < games.size(); i++) {
                MatchTranslationModel matchTranslationModel = new MatchTranslationModel();
                matchTranslationModel.setGame(games.get(i));
                matchTranslationModel.setLink(links.get(i));
                matchTranslationModel.setDate(getCurrentDate());
                matches.add(matchTranslationModel);
            }
        }
        return matches;
    }

    private List<String> getLinks(String text) {
        Pattern pattern = Pattern.compile("<a href=\"(.*?)\">");
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> links = new ArrayList<>();
        while (matcher.find()) {
            String link = matcher.group(1);
            links.add(link);
        }
        return links;
    }

    private List<String> getGames(String text) {
        Pattern pattern = Pattern.compile("(\\d{2}:\\d{2})\\s+(\\D+)\\s+-\\s+(\\D+)");
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> games = new ArrayList<>();
        while (matcher.find()) {
            String time = matcher.group(1);
            String team1 = matcher.group(2);
            String team2 = matcher.group(3);
            String game = time + " " + team1 + ":" + team2;
            games.add(game);
        }
        return games;
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
