package com.bot.football.services.parsers;

import org.jsoup.nodes.Document;

import java.util.List;

public interface Parser<T> {

    String getCurrentDate();

    List<T> getMatches();

    Document getDocument(String url);
}
