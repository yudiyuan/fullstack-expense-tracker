package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.utils.LocaleManager;
import org.example.utils.ViewNavigator;
import org.example.views.LoginView;

import java.util.Locale;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // 测试英文
        System.out.println(LocaleManager.getString("login.title"));

        // 测试中文
        LocaleManager.setLocale(Locale.CHINESE);
        System.out.println(LocaleManager.getString("login.title"));

       // LocaleManager.setLocale(Locale.ENGLISH);

        LocaleManager.setLocale(Locale.CHINESE);

        stage.setTitle("Expense Tracker");
        ViewNavigator.setMainStage(stage);
        new LoginView().show();
    }
}