package com.bot.football;

import com.bot.football.telegram.BotConfig;
import com.bot.football.telegram.BotInitializer;
import com.bot.football.telegram.TelegramBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        try {
            BotConfig botConfig = new BotConfig("Football stats", "6112319315:AAE-haR2svHmJk0zEWiUPITShmsU47omP5A");
            TelegramBot telegramBot = new TelegramBot(botConfig);
            BotInitializer botInitializer = new BotInitializer(telegramBot);
            botInitializer.initBot();
        }catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}