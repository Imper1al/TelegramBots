package com.bot.football.dto;

public class MatchDTO {

    private String onlineMatch;
    private String offlineMatch;
    private String fullTimeMatch;
    private String eventDate;

    public String getOnlineMatch() {
        return onlineMatch;
    }

    public void setOnlineMatch(String onlineMatch) {
        this.onlineMatch = onlineMatch;
    }

    public String getOfflineMatch() {
        return offlineMatch;
    }

    public void setOfflineMatch(String offlineMatch) {
        this.offlineMatch = offlineMatch;
    }

    public String getFullTimeMatch() {
        return fullTimeMatch;
    }

    public void setFullTimeMatch(String fullTimeMatch) {
        this.fullTimeMatch = fullTimeMatch;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
