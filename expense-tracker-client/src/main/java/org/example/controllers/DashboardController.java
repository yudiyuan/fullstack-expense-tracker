package org.example.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.example.components.TransactionComponent;
import org.example.dialogs.CreateNewCategoryDialog;
import org.example.dialogs.CreateOrEditTransactionDialog;
import org.example.dialogs.ViewOrEditTransactionCategoryDialog;
import org.example.models.MonthlyFinance;
import org.example.models.Transaction;
import org.example.models.User;
import org.example.utils.SqlUtil;
import org.example.views.DashboardView;
import org.example.views.LoginView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DashboardController {
    private final int recentTransactionSize=10;

    private DashboardView dashboardView;

    private User user;
    private List<Transaction> recentTransactions,currentTransactionsByYear;

    private int currentPage;
    private int currentYear;

    public DashboardController(DashboardView dashboardView){
        this.dashboardView=dashboardView;
        currentYear=dashboardView.getYearComboBox().getValue();
        fetchUserData();
        intialize();
    }

    public void fetchUserData() {
        //load the loading animation
        dashboardView.getLoadingAnimationPane().setVisible(true);

        //remove all children from the dashboard view
        dashboardView.getRecentTransactionBox().getChildren().clear();

       // System.out.println("email = " + dashboardView.getEmail());
        user= SqlUtil.getUserByEmail(dashboardView.getEmail());


        //get the transactions for the year
        currentTransactionsByYear=SqlUtil.getAllTransactionsByUserId(user.getId(),currentYear);
        dashboardView.getYearComboBox().getItems().addAll(SqlUtil.getAllDistinctYears(user.getId()));

        dashboardView.getTransactionTable().setItems(calculateMonthlyFinances());
        createRecentTransactionComponents();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    dashboardView.getLoadingAnimationPane().setVisible(false);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void createRecentTransactionComponents(){
        recentTransactions=SqlUtil.getRecentTransactionsByUserId(
                user.getId(),
                0,
                currentPage,
                recentTransactionSize
        );

        if(recentTransactions==null) return;

        for(Transaction transaction:recentTransactions){
            dashboardView.getRecentTransactionBox().getChildren().add(
                    new TransactionComponent(this,transaction)
            );
        }
    }

    private ObservableList<MonthlyFinance> calculateMonthlyFinances(){
        double[] incomeCounter=new double[12];
        double[] expenseCounter=new double[12];

        for(Transaction transaction:currentTransactionsByYear){
            LocalDate transactionDate=transaction.getTransactionDate();
            if(transaction.getTransactionType().equalsIgnoreCase("Income")){
                incomeCounter[transactionDate.getMonth().getValue()-1]+=transaction.getTransactionAmount();
            }else {
                expenseCounter[transactionDate.getMonth().getValue()-1]+=transaction.getTransactionAmount();
            }
        }

        ObservableList<MonthlyFinance> monthlyFinances= FXCollections.observableArrayList();
        for(int i=0;i<12;i++){
            MonthlyFinance monthlyFinance=new MonthlyFinance(
                    Month.of(i+1).name(),
                    new BigDecimal(String.valueOf(incomeCounter[i])),
                    new BigDecimal(String.valueOf(expenseCounter[i]))
            );
                    monthlyFinances.add(monthlyFinance);
        }


        return monthlyFinances;
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

        dashboardView.getLogoutManuItem().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                new LoginView().show();
            }
        });

    }

    private void addRecentTransactionActions(){
        dashboardView.getAddTransactionButton().setOnMouseClicked(new  EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                new CreateOrEditTransactionDialog(DashboardController.this,false).showAndWait();
            }
        });
    }

    public User getUser() {
        return user;
    }

}
