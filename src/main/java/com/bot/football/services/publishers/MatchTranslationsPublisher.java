package com.bot.football.services.publishers;

import com.bot.football.services.parsers.MatchTranslationParser;
import com.bot.football.dto.MatchTranslationsDTO;
import com.bot.football.models.MatchTranslationModel;

import java.util.List;
import java.util.Map;

public class MatchTranslationsPublisher {

    private final Map<String, MatchTranslationsDTO> goooolModelMap;

    public MatchTranslationsPublisher(Map<String, MatchTranslationsDTO> siteMap) {
        this.goooolModelMap = siteMap;
    }

    public List<MatchTranslationModel> getMatchesForTelegramBot(String key) {
        MatchTranslationParser matchParser = new MatchTranslationParser(key, goooolModelMap.get(key));
        return matchParser.getMatches();
    }
}
