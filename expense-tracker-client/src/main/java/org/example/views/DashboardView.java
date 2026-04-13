package org.example.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.controllers.DashboardController;
import org.example.utils.Utilitie;
import org.example.utils.ViewNavigator;

import java.util.Collection;
import java.util.concurrent.Callable;

public class DashboardView {
    private String email;

    private Label currentBalanceLabel,currentBalance;
    private Label totalIncomeLabel,totalIncome;
    private Label totalExpenseLabel,totalExpense;

    private Button addTransactionButton;
    private VBox recentTransactionBox;
    private ScrollPane recentTransactionsScrollPane;

    private MenuItem createCategoryManuItem,viewCategoriesManuItem;

    public DashboardView(String email) {
        this.email = email;

        currentBalanceLabel = new Label("Current Balance:");
        totalIncomeLabel = new Label("Total Income:");
        totalExpenseLabel = new Label("Total Expense:");

        addTransactionButton = new Button("+");

        currentBalance = new Label("$0.00");
        totalIncome=new Label("$0.00");
        totalExpense = new Label("$0.00");
    }

    public void show(){
        Scene scene=createScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        new DashboardController(this);
        ViewNavigator.switchViews(scene);
    }

    private Scene createScene(){
        MenuBar menuBar = createMenuBar();

        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().addAll("main-background");

        VBox mainContainerWrapper = new VBox();
        mainContainerWrapper.getStyleClass().addAll("dashboard-padding");
        VBox.setVgrow(mainContainerWrapper,Priority.ALWAYS);

        HBox balanceSummaryBox=createBalanceSummaryBox();
        GridPane contentGridPane=createContentGridPane();
        VBox.setVgrow(contentGridPane,Priority.ALWAYS);

        mainContainerWrapper.getChildren().addAll(balanceSummaryBox,contentGridPane);
        mainContainer.getChildren().addAll(menuBar,mainContainerWrapper);
        return new Scene(mainContainer, Utilitie.APP_WIDTH, Utilitie.APP_HEIGHT);
    }

    private MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        createCategoryManuItem = new MenuItem("Create Category");
        viewCategoriesManuItem = new MenuItem("View Categories");

        fileMenu.getItems().addAll(createCategoryManuItem,viewCategoriesManuItem);
        menuBar.getMenus().addAll(fileMenu);
        return menuBar;
    }

    private HBox createBalanceSummaryBox(){
        HBox balanceSummaryBox=new HBox();

        VBox currentBalanceBox=new VBox();
        currentBalanceLabel.getStyleClass().addAll("text-size-lg","text-light-gray");
        currentBalance.getStyleClass().addAll("text-size-lg","text-white");
        currentBalanceBox.getChildren().addAll(currentBalanceLabel,currentBalance);

        Region region1=new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        VBox totalIncomeBox=new VBox();
        totalIncomeLabel.getStyleClass().addAll("text-size-lg","text-light-gray");
        totalIncome.getStyleClass().addAll("text-size-lg","text-white");
        totalIncomeBox.getChildren().addAll(totalIncomeLabel,totalIncome);

        Region region2=new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        VBox totalExpenseBox=new VBox();
        totalExpenseLabel.getStyleClass().addAll("text-size-lg","text-light-gray");
        totalExpense.getStyleClass().addAll("text-size-lg","text-white");
        totalExpenseBox.getChildren().addAll(totalExpenseLabel,totalExpense);

        balanceSummaryBox.getChildren().addAll(currentBalanceBox,region1,totalIncomeBox,region2,totalExpenseBox);
        return balanceSummaryBox;
    }

    private GridPane createContentGridPane(){
        GridPane gridPane=new GridPane();

        //set constraints to the cells in the gridpane
        ColumnConstraints columnConstraint=new ColumnConstraints();
        columnConstraint.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(columnConstraint,columnConstraint);

        //recent transactions
        VBox recentTransactionsVBox=createRecentTransactionsVBox();
        recentTransactionsVBox.getStyleClass().addAll("field-background","rounded-border","padding-10px");
        GridPane.setVgrow(recentTransactionsVBox,Priority.ALWAYS);

        gridPane.add(recentTransactionsVBox,1,0);
        return gridPane;
    }

    private VBox createRecentTransactionsVBox(){
        VBox recentTransactionsVBox=new VBox();

        //lable and add button
        HBox recenTransactionLabelandAddBtnBox=new HBox();
        Label recenTransactionsLabel=new Label("Recent Transactions");
        recenTransactionsLabel.getStyleClass().addAll("text-size-lg","text-light-gray");

        Region labelAndButtonSpaceRegion=new Region();
        HBox.setHgrow(labelAndButtonSpaceRegion, Priority.ALWAYS);

        addTransactionButton.getStyleClass().addAll("field-background","text-size-md","text-light-gray",
                "rounded-border");

        recenTransactionLabelandAddBtnBox.getChildren().addAll(recenTransactionsLabel,labelAndButtonSpaceRegion,
                addTransactionButton);

        //recent transactions box
        recentTransactionBox=new VBox();
        recentTransactionsScrollPane=new ScrollPane(recentTransactionBox);
        recentTransactionsScrollPane.setFitToWidth(true);
        recentTransactionsScrollPane.setFitToHeight(true);

        recentTransactionsVBox.getChildren().addAll(recenTransactionLabelandAddBtnBox,recentTransactionsScrollPane);
        return recentTransactionsVBox;


    }


    public MenuItem getCreateCategoryManuItem() {
        return createCategoryManuItem;
    }

    public void setCreateCategoryManuItem(MenuItem createCategoryManuItem) {
        this.createCategoryManuItem = createCategoryManuItem;
    }

    public String getEmail() {
        return email;
    }

    public MenuItem getViewCategoriesManuItem() {
        return viewCategoriesManuItem;
    }

    public Button getAddTransactionButton() {
        return addTransactionButton;
    }
}
