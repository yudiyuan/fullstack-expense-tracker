package org.example.controllers;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import org.example.utils.ApiUtil;
import org.example.utils.SqlUtil;
import org.example.utils.Utilitie;
import org.example.views.DashboardView;
import org.example.views.LoginView;

import javafx.scene.input.MouseEvent;
import org.example.views.SignUpView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;



public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView=loginView;
        initialize();
    }

    private void initialize(){
        loginView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){

                if(!validateUser()) return;

                String email=loginView.getUsernameField().getText();
                String password=loginView.getPasswordField().getText();

                if(SqlUtil.postLoginUser(email,password)){
                    Utilitie.showAlertDialog(Alert.AlertType.INFORMATION,"login successful!");

                    new DashboardView(email).show();
                }else {
                    Utilitie.showAlertDialog(Alert.AlertType.ERROR,"Failed to authenticate");

                }



            }
        });

        loginView.getSignupLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent){
                new SignUpView().show();
            }
        });
    }

    private boolean validateUser(){
        return true;
    }
}
