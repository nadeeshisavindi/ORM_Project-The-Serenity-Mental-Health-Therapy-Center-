package org.example.orm_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/org/example/orm_project/fxml/login.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ORM Project");
        stage.setScene(scene);
        stage.show();
    }
}