package com.bot.football.dto;

public class MatchTranslationsDTO {

    private String teams;
    private String gamesTable;
    private String time;
    private String eventDate;
    private String translationLink;

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public String getGamesTable() {
        return gamesTable;
    }

    public void setGamesTable(String gamesTable) {
        this.gamesTable = gamesTable;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTranslationLink() {
        return translationLink;
    }

    public void setTranslationLink(String translationLink) {
        this.translationLink = translationLink;
    }
}
