package rockpaperscissors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameLayout extends Application {

    private Button rock, paper, scissors, playAgain, displayRules, okButton;
    private Scene play, displayResult, rulesScene;
    private Text welcomeMessage, rulesMessage, startMessage, resultMessage;
    private int userSelection;
    private double compSelection;
    private String result;
    private String player1Pick;
    private String computerPick;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Sasso Carta Forbice Client");

        welcomeMessage = new Text("Benvenuto a Sasso Carta Forbice!");
        welcomeMessage.setFont(Font.font("", FontPosture.ITALIC, 18));
        rulesMessage = new Text(
                "Ricorda: sasso batte forbice, carta batte sasso e forbice batte carta.");
        startMessage = new Text("Selezionare:");
        startMessage.setFont(Font.font("", FontPosture.ITALIC, 12));
        resultMessage = new Text();
        resultMessage.setFont(Font.font("", FontPosture.ITALIC, 18));

        Image rockImage = new Image("rock.png");
        Image paperImage = new Image("paper.png");
        Image scissorsImage = new Image("scissors.png");
        displayRules = new Button("Regole");
        displayRules.setPrefWidth(550);
        rock = new Button("  Sasso  ");
        rock.setGraphic(new ImageView(rockImage));
        paper = new Button("  Carta  ");
        paper.setGraphic(new ImageView(paperImage));
        scissors = new Button("Forbice");
        scissors.setGraphic(new ImageView(scissorsImage));
        VBox vbox = new VBox();
        vbox.getChildren().addAll(welcomeMessage, rulesMessage, startMessage);
        vbox.setAlignment(Pos.CENTER);
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(rock, paper, scissors);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setPadding(new Insets(5, 5, 5, 5));
        hbox1.setSpacing(15);
        VBox main = new VBox(5);
        main.getChildren().addAll(vbox, hbox1, displayRules);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(15, 15, 15, 15));
        BorderPane border = new BorderPane();
        border.setCenter(main);

        playAgain = new Button("Gioca ancora");
        BorderPane border1 = new BorderPane();
        VBox results = new VBox(10);
        results.getChildren().addAll(resultMessage, playAgain);
        results.setAlignment(Pos.CENTER);
        border1.setCenter(results);

        play = new Scene(border, 600, 220);
        displayResult = new Scene(border1, 600, 220);

        rock.setOnAction(e -> {
            userSelection = 1;
            player1Pick = "Sasso";
            passa(1.0);
            displayResults();
            primaryStage.setScene(displayResult);
        });

        paper.setOnAction(e -> {
            userSelection = 2;
            player1Pick = "Carta";
            passa(2.0);
            displayResults();
            primaryStage.setScene(displayResult);
        });

        scissors.setOnAction(e -> {
            userSelection = 3;
            player1Pick = "Forbice";
            passa(3.0);
            displayResults();
            primaryStage.setScene(displayResult);
        });

        playAgain.setOnAction(e -> {
            primaryStage.setScene(play);
        });

        displayRules.setOnAction(e -> {
            displayRulesList();
        });

        primaryStage.setScene(play);
        primaryStage.show();

    }

    public String runGame() {
        compSelection = ricevi();
        if (compSelection == 1) {
            computerPick = "Sasso";
        } else if (compSelection == 2) {
            computerPick = "Carta";
        } else if (compSelection == 3) {
            computerPick = "Forbice";
        }
        if ((userSelection == 1 && compSelection == 3)
                || (userSelection == 2 && compSelection == 1)
                || (userSelection == 3 && compSelection == 2)) {
            result = "Congratulazioni hai vinto!";
        } else if ((userSelection == 1 && compSelection == 2)
                || (userSelection == 2 && compSelection == 3)
                || (userSelection == 3 && compSelection == 1)) {
            result = "L'avversario ha vinto!";
        } else {
            result = "Pareggio!";
        }
        return result;
    }

    public void displayResults() {
        runGame();
        resultMessage.setText("Hai selezionato " + player1Pick
                + " l'avversario ha scelto " + computerPick + ".\n" + result);
    }

    public void displayRulesList() {
        Stage ruleList = new Stage();
        ruleList.initModality(Modality.APPLICATION_MODAL);
        ruleList.setTitle("Regole del gioco");

        Label rules = new Label();
        rules.setText("Le regole sono: \n"
                + "sasso batte forbice, carta batte sasso e forbice batte carta. \n"
                + "Scegli 'elemento' cliccando il bottone. \n");
        rules.setFont(Font.font("", FontPosture.ITALIC, 15));
        okButton = new Button("OK");

        okButton.setOnAction(e -> ruleList.close());

        VBox setRules = new VBox(5);
        setRules.getChildren().addAll(rules, okButton);
        setRules.setAlignment(Pos.CENTER);
        setRules.setPadding(new Insets(5, 5, 5, 5));

        rulesScene = new Scene(setRules);
        ruleList.setScene(rulesScene);
        ruleList.sizeToScene();
        ruleList.showAndWait();
    }

    private double ricevi() {
        double temp;
        try {
            Socket s = new Socket("127.0.0.1", 1564);
            System.out.println("connesso");
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println("risposta");
            temp = Double.parseDouble(br.readLine());
            s.close();
            return temp;
        } catch (UnknownHostException e) {
            System.out.println("ip non trovato");
        } catch (IOException e) {
            System.out.println("no data");
        }
        return 0;
    }

    private void passa(double temp) {
        try {
            ServerSocket ss = new ServerSocket(1564);
            System.out.println("wait");
            Socket s = ss.accept();
            System.out.println("accettata");

            PrintStream ps = new PrintStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("input");
            ps.print(temp);
            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println("no data");
        }
    }
}
