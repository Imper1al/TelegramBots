package com.bot.football.data;

import com.bot.football.commands.SiteMap;
import com.bot.football.constants.Constants;

public class InitData {

    public SiteMap initData() {
        SiteMap siteMap = new SiteMap();
        siteMap.addNewFootballSite(Constants.sport,
                Constants.onlineMatch,
                Constants.offlineMatch,
                Constants.fullTimeMatch,
                Constants.sportDate);
        siteMap.addGoooolSite(Constants.gooool,
                Constants.goooolTeams,
                Constants.goooolTableGames,
                Constants.goooolTime,
                Constants.goooolDate,
                Constants.goooolLink);
        return siteMap;
    }
}
