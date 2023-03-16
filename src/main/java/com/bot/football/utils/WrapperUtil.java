package com.bot.football.utils;

public class WrapperUtil {

    public static String wrapInStripes(String text) {
        String[] lines = text.split("\n");
        int maxLineLength = 0;
        for (String line : lines) {
            if (line.length() > maxLineLength) {
                maxLineLength = line.length();
            }
        }
        String topBottomStripes = "-".repeat(maxLineLength + 4);
        return topBottomStripes + "\n" + text + topBottomStripes + "\n";
    }
}
