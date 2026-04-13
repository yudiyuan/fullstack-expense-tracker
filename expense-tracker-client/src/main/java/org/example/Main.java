package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.utils.ViewNavigator;
import org.example.views.DashboardView;
import org.example.views.LoginView;
import org.example.views.SignUpView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Expense Tracker");
        //Both ViewNavigator and LoginView are classes. The difference in how they are called comes from the fact that one involves calling a static method, while the other involves calling an instance method.
        ViewNavigator.setMainStage(stage);

        // new SignUpView().show();

        new DashboardView("email@email.com").show();
    }
}