package org.example.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;

public class Utilitie {
     public static final int APP_WIDTH = 1614;
     public static final int APP_HEIGHT = 900;

     public static void showAlertDialog(Alert.AlertType alertType, String message){
     Alert alert = new Alert(alertType);
     alert.setContentText(message);
     alert.showAndWait();}

    public static  String getHexColorValue(ColorPicker colorPicker){
         String color=colorPicker.getValue().toString();
         return color.substring(2,color.length()-2);
    }
}
