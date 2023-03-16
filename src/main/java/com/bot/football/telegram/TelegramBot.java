package com.bot.football.telegram;

import com.bot.football.constants.Constants;
import com.bot.football.data.InitData;
import com.bot.football.dto.MatchDTO;
import com.bot.football.dto.MatchTranslationsDTO;
import com.bot.football.models.MatchTranslationModel;
import com.bot.football.services.publishers.MatchPublisher;
import com.bot.football.services.publishers.MatchTranslationsPublisher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    private Integer page = 1;
    private Integer messageId;

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            Message message = update.getMessage();
            String messageText = message.getText();
            String userName = message.getFrom().getUserName();
            String firstName = message.getFrom().getFirstName();
            String lastName = message.getFrom().getLastName();
            long chatId = message.getChat().getId();
            System.out.println("Message from " + firstName + " (" + userName + ") " + lastName + ": " + messageText);
            System.out.println(update.hasCallbackQuery());
            switch (messageText) {
                case "Список трансляций" -> setGamesLinks(publishGamesLinks(), chatId, page);
                case "next_page" -> setGamesLinks(publishGamesLinks(), chatId, page++);
                case "prev_page" -> setGamesLinks(publishGamesLinks(), chatId, page--);
                case "Список матчей" -> sendMessage(matchPublisher(), chatId);
                case "Выпить пивка" -> sendMessage("\uD83C\uDF7A", chatId);
                case "Забить гол" -> sendMessage("⚽️", chatId);
                case "Пидр" -> sendMessage("Сам пидр", chatId);
                case "Лох" -> sendMessage("Сам лох️", chatId);
                default -> attributes(chatId);
            }
        }

    }

    private List<MatchTranslationModel> publishGamesLinks() {
        InitData initData = new InitData();
        Map<String, MatchTranslationsDTO> gooolDTOMap = initData.initData().getGoooolModelMap();
        MatchTranslationsPublisher matchTranslationsPublisher = new MatchTranslationsPublisher(gooolDTOMap);

        return matchTranslationsPublisher.getMatchesForTelegramBot(Constants.gooool);
    }

    private String matchPublisher() {
        InitData initData = new InitData();
        Map<String, MatchDTO> matchDTOMap = initData.initData().getSiteValues();
        MatchPublisher matchPublisher = new MatchPublisher(matchDTOMap);

        return matchPublisher.getMatchesForTelegramBot(Constants.sport);
    }

    private void sendMessage(String message, long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendMessage(ReplyKeyboardMarkup markup, long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите действие:");
        sendMessage.setReplyMarkup(markup);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendMessage(InlineKeyboardMarkup markup, String date, long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(date);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(markup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    private void attributes(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Список трансляций"));
        row.add(new KeyboardButton("Список матчей"));
        row.add(new KeyboardButton("Выпить пивка"));
        row.add(new KeyboardButton("Забить гол"));
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        sendMessage(replyKeyboardMarkup, chatId);
    }

    private void setGamesLinks(List<MatchTranslationModel> matchTranslationModels, long chatId, int pageNumber) {
        int itemsPerPage = 5;
        int startIndex = (pageNumber - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, matchTranslationModels.size());

        List<MatchTranslationModel> pageItems = matchTranslationModels.subList(startIndex, endIndex);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (MatchTranslationModel model : pageItems) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(model.getGame());
            button.setUrl(model.getLink());
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            rows.add(row);
        }

        // Add pagination buttons
        List<InlineKeyboardButton> paginationRow = new ArrayList<>();
        if (pageNumber > 1) {
            InlineKeyboardButton backButton = new InlineKeyboardButton();
            backButton.setText("Назад");
            backButton.setCallbackData("prev_page");
            paginationRow.add(backButton);
        }
        if (endIndex < matchTranslationModels.size()) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton();
            nextButton.setText("Вперед");
            nextButton.setCallbackData("next_page");
            paginationRow.add(nextButton);
        }
        rows.add(paginationRow);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        sendMessage(markup, matchTranslationModels.get(0).getDate(), chatId);
        //test2
    }
}