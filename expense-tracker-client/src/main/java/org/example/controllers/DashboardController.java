package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.dialogs.CreateNewCategoryDialog;
import org.example.dialogs.CreateOrEditTransactionDialog;
import org.example.dialogs.ViewOrEditTransactionCategoryDialog;
import org.example.models.User;
import org.example.utils.SqlUtil;
import org.example.views.DashboardView;

public class DashboardController {
    private DashboardView dashboardView;

    private User user;

    public DashboardController(DashboardView dashboardView){
        this.dashboardView=dashboardView;
        fetchUserData();
        intialize();
    }

    private void fetchUserData() {
      user= SqlUtil.getUserByEmail(dashboardView.getEmail());

    }

    private void intialize(){
        addMenuActions();
        addRecentTransactionActions();
    }

    private void addMenuActions(){
        dashboardView.getCreateCategoryManuItem().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                    new CreateNewCategoryDialog(user).showAndWait();
            }
        });

        dashboardView.getViewCategoriesManuItem().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                new ViewOrEditTransactionCategoryDialog(user,DashboardController.this).showAndWait();
            }
        });

    }

    private void addRecentTransactionActions(){
        dashboardView.getAddTransactionButton().setOnMouseClicked(new  EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                new CreateOrEditTransactionDialog(user,false).showAndWait();
            }
        });
    }
}
