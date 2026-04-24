package org.example.dialogs;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.models.MonthlyFinance;
import org.example.models.User;
import org.example.utils.LocaleManager;

public class ViewChartDialog extends CustomDialog {

    public ViewChartDialog(User user, ObservableList<MonthlyFinance> monthlyFinances) {
        super(user);

        setTitle(LocaleManager.getString("dialog.chart.title"));
        setWidth(900);
        setHeight(595);

        VBox barChartBox = new VBox();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(LocaleManager.getString("chart.x.axis"));
        xAxis.setTickLabelFill(Paint.valueOf("#BEB989"));
        xAxis.setTickLabelRotation(0);
        xAxis.setTickLabelGap(10);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(LocaleManager.getString("chart.y.axis"));
        yAxis.setTickLabelFill(Paint.valueOf("#BEB989"));

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setPrefWidth(850);
        barChart.setPrefHeight(500);
        barChart.getStyleClass().add("text-size-md");

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName(LocaleManager.getString("chart.series.income"));

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName(LocaleManager.getString("chart.series.expense"));

        for (int i = 0; i < monthlyFinances.size(); i++) {
            MonthlyFinance monthlyFinance = monthlyFinances.get(i);

            String chartMonthLabel;
            if (LocaleManager.getCurrentLocale().getLanguage().equals("zh")) {
                chartMonthLabel = (i + 1) + "月";
            } else {
                chartMonthLabel = monthlyFinance.getMonth();
            }

            incomeSeries.getData().add(
                    new XYChart.Data<>(chartMonthLabel, monthlyFinance.getIncome())
            );
            expenseSeries.getData().add(
                    new XYChart.Data<>(chartMonthLabel, monthlyFinance.getExpense())
            );
        }

        barChart.getData().addAll(incomeSeries, expenseSeries);

        incomeSeries.getData().forEach(
                data -> data.getNode().setStyle("-fx-bar-fill: #33ba2f")
        );

        expenseSeries.getData().forEach(
                data -> data.getNode().setStyle("-fx-bar-fill: #ba2f2f")
        );

        barChartBox.getChildren().add(barChart);
        getDialogPane().setContent(barChartBox);
    }
}