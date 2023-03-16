package com.bot.football.services.publishers;

import com.bot.football.dto.MatchDTO;
import com.bot.football.models.MatchModel;
import com.bot.football.services.parsers.MatchParser;

import java.util.List;
import java.util.Map;

public class MatchPublisher {

    private final Map<String, MatchDTO> siteMap;

    public MatchPublisher(Map<String, MatchDTO> siteMap) {
        this.siteMap = siteMap;
    }

    public String getMatchesForTelegramBot(String key) {
        MatchParser matchParser = new MatchParser(key, siteMap.get(key));
        List<MatchModel> matchModels = matchParser.getMatches();
        String result = matchParser.getCurrentDate() + "\n\n";
        for (MatchModel matchModel : matchModels) {
            result += matchModel.getMatch() + "\n";
        }
        return result;
    }
}
