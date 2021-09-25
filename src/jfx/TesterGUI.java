package jfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class TesterGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private final VBox root = new VBox();
    private final TextArea taInput = new TextArea();
    private final TextField tfRegEx = new TextField();
    private final WebView wvAusgabe = new WebView();
    private final HighlightHandler hhding = new HighlightHandler(taInput, tfRegEx, wvAusgabe);

    @Override
    public void start(Stage primaryStage) {
        //Stylegedönse
        taInput.setStyle("-fx-font-family: Monospaced;" +
                "-fx-font-size: 20px;");
        tfRegEx.setStyle("-fx-font-family: Monospaced;" +
                "-fx-font-size: 20px;");
//        root.setSpacing(10.);
        root.setStyle("-fx-padding: 10px;" +
                "-fx-spacing: 10px;");

        taInput.setOnKeyTyped(hhding);
        tfRegEx.setOnKeyTyped(hhding);

        //Zusammenbauen
        root.getChildren().addAll(taInput, tfRegEx, wvAusgabe);

        primaryStage.setTitle("Regex Tester 0.1a");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
