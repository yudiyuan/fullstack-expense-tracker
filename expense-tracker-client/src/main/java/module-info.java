module expense.tracker.client {
    requires javafx.controls;
    requires com.google.gson;
    requires java.desktop;
    requires java.net.http;

    //this is crucial to be able to read data from models and store them into tables
    opens org.example.models to javafx.base;

    exports org.example;

}