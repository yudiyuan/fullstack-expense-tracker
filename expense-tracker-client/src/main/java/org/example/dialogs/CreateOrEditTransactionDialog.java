package org.example.dialogs;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.models.User;

public class CreateOrEditTransactionDialog extends CustomDialog{
    private TextField transactionNameField,transactionAmountField;
    private DatePicker transactionDatePicker;
    private ComboBox<String> transactionCategoryBox;
    private ToggleGroup transactionTypeToggleGroup;

    private boolean isEditing;

    public CreateOrEditTransactionDialog(User user, boolean isEditing) {
        super(user);
        this.isEditing = isEditing;

        setTitle(isEditing ? "Edit Transaction" : "Create New Transaction");
        setWidth(700);
        setHeight(595);

        VBox mainContentBox = createMainContentBox();
        getDialogPane().setContent(mainContentBox);
    }

    private VBox createMainContentBox() {
        VBox mainContentBox = new VBox(30);
        mainContentBox.setAlignment(Pos.CENTER);

        transactionNameField = new TextField();
        transactionNameField.setPromptText("Enter Transaction Name");
        transactionNameField.getStyleClass().addAll("field-background","text-light-gray","text-size-md",
                "rounded-border");

        transactionAmountField = new TextField();
        transactionAmountField.setPromptText("Enter Transaction Amount");
        transactionAmountField.getStyleClass().addAll("field-background","text-light-gray","text-size-md",
                "rounded-border");

        transactionDatePicker = new DatePicker();
        transactionDatePicker.setPromptText("Enter Transaction Date");
        transactionDatePicker.getStyleClass().addAll("field-background","text-light-gray","text-size-md",
                "rounded-border");
        transactionDatePicker.setMaxWidth(Double.MAX_VALUE);

        transactionCategoryBox = new ComboBox();
        transactionCategoryBox.setPromptText("Choose Category");
        transactionCategoryBox.getStyleClass().addAll("field-background","text-light-gray","text-size-md",
                "rounded-border");
        transactionCategoryBox.setMaxWidth(Double.MAX_VALUE);


        mainContentBox.getChildren().addAll(transactionNameField,transactionAmountField,transactionDatePicker,
                transactionCategoryBox,createTransactionTypeRadioButtonGroup());
        return mainContentBox;
    }

    private HBox createTransactionTypeRadioButtonGroup() {
        HBox radioButtonBox = new HBox(50);
        radioButtonBox.setAlignment(Pos.CENTER);

        transactionTypeToggleGroup = new ToggleGroup();

        RadioButton incomeRadioButton = new RadioButton("Income");
        incomeRadioButton.setToggleGroup(transactionTypeToggleGroup);
        incomeRadioButton.getStyleClass().addAll("text-size-md","text-light-gray");

        RadioButton expenseRadioButton = new RadioButton("Expense");
        expenseRadioButton.setToggleGroup(transactionTypeToggleGroup);
        expenseRadioButton.getStyleClass().addAll("text-size-md","text-light-gray");

        radioButtonBox.getChildren().addAll(incomeRadioButton,expenseRadioButton);

        return radioButtonBox;

    }


}
