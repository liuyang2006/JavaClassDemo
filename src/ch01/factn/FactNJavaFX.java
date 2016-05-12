package ch01.factn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FactNJavaFX extends Application {

    TextField inputText;
    TextArea outputText;

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a pane and set its properties
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        // Place nodes in the pane
        pane.add(new Label("请输入N:"), 0, 0);
        inputText = new TextField();
        inputText.setText("10");
        pane.add(inputText, 0, 1);
        Button btAdd = new Button("计算N!");
        pane.add(btAdd, 0, 2);
        GridPane.setHalignment(btAdd, HPos.LEFT);
        outputText = new TextArea();
        outputText.setPrefColumnCount(30);
        outputText.setWrapText(true);
        pane.add(outputText, 0, 3);

        btAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int n = Integer.valueOf(inputText.getText());
                FactTool tool = new FactTool();
                outputText.setText(tool.getAllResults(n));
            }
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("高精度求阶乘演示程序"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}