package org.example.controllers;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.utils.LocaleManager;
import org.example.utils.SqlUtil;
import org.example.utils.Utilitie;
import org.example.views.DashboardView;
import org.example.views.LoginView;
import org.example.views.SignUpView;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView){
        this.loginView = loginView;
        initialize();
    }

    private void initialize(){
        loginView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!validateUser()) return;

                String email = loginView.getUsernameField().getText();
                String password = loginView.getPasswordField().getText();

                if(SqlUtil.postLoginUser(email, password)){
                    Utilitie.showAlertDialog(
                            Alert.AlertType.INFORMATION,
                            LocaleManager.getString("login.success")
                    );
                    new DashboardView(email).show();
                }else{
                    Utilitie.showAlertDialog(
                            Alert.AlertType.ERROR,
                            LocaleManager.getString("login.failed")
                    );
                }
            }
        });

        loginView.getSignupLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new SignUpView().show();
            }
        });
    }

    private boolean validateUser(){
        if(loginView.getUsernameField().getText().isEmpty()){
            Utilitie.showAlertDialog(
                    Alert.AlertType.ERROR,
                    LocaleManager.getString("login.empty.username")
            );
            return false;
        }

        if(loginView.getPasswordField().getText().isEmpty()){
            Utilitie.showAlertDialog(
                    Alert.AlertType.ERROR,
                    LocaleManager.getString("login.empty.password")
            );
            return false;
        }

        return true;
    }
}