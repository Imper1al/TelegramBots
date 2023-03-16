package com.bot.football.commands;

import com.bot.football.dto.MatchDTO;
import com.bot.football.dto.MatchTranslationsDTO;

import java.util.HashMap;
import java.util.Map;

public class SiteMap {

    Map<String, MatchDTO> siteValues;
    Map<String, MatchTranslationsDTO> goooolModelMap;

    public SiteMap() {
        this.siteValues = new HashMap<>();
        this.goooolModelMap = new HashMap<>();
    }

    public void addNewFootballSite(String link, String onlineMatch, String offlineMatch, String fullMatch, String eventDate) {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setOnlineMatch(onlineMatch);
        matchDTO.setOfflineMatch(offlineMatch);
        matchDTO.setEventDate(eventDate);
        matchDTO.setFullTimeMatch(fullMatch);
        siteValues.put(link, matchDTO);
    }

    public void addGoooolSite(String link, String teams, String gamesTable, String time, String eventDate, String gameLink) {
        MatchTranslationsDTO model = new MatchTranslationsDTO();
        model.setTeams(teams);
        model.setGamesTable(gamesTable);
        model.setTime(time);
        model.setEventDate(eventDate);
        model.setTranslationLink(gameLink);
        goooolModelMap.put(link, model);
    }

    public Map<String, MatchDTO> getSiteValues() {
        return siteValues;
    }

    public Map<String, MatchTranslationsDTO> getGoooolModelMap() {
        return goooolModelMap;
    }
}
