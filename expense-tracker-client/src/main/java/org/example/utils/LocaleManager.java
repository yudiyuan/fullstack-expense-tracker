package org.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {

    private static Locale currentLocale = Locale.ENGLISH;
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages", currentLocale);

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }

    public static String formatCurrency(BigDecimal amount) {
        String symbol = currentLocale.getLanguage().equals("zh") ? "¥" : "$";
        return symbol + amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static String formatCurrency(double amount) {
        return formatCurrency(BigDecimal.valueOf(amount));
    }
}