package org.example.dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.example.models.User;
import org.example.utils.LocaleManager;

public class CustomDialog extends Dialog {

    protected User user;

    public CustomDialog(User user){
        this.user = user;

        // stylesheet
        getDialogPane().getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
        getDialogPane().getStyleClass().addAll("main-background");

        // dialog title
        setTitle(LocaleManager.getString("dialog.title"));

        // add button
        getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);

        okButton.setText(LocaleManager.getString("dialog.ok"));

        okButton.setVisible(false);
        okButton.setDisable(true);
    }
}