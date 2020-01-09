package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main extends Application {



    @Override

    public void start(Stage primaryStage) {

        GridPane pane = new GridPane();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setMinWidth(150.0);
                button.setMinHeight(150.0);
                button.setOnMouseClicked(e->{
                    button.setText("X"); //
                    isWon("X",pane);
                    computerTurn(pane); // znalezc wolne pola i dopisac O
                });
                pane.add(button, i,j);

            }
        }

        Scene scene = new Scene(pane, 600, 600);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void computerTurn(GridPane pane){
        //
        ObservableList<Node> children = pane.getChildren();
        List<Node> emptyButtons = children.stream().filter(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals("X") || button.getText().equals("O")) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        markComputerTurn(emptyButtons);
        isWon("O",pane);
    }

    private void isWon(String symbol, GridPane pane) {
//ta sama zasada co z Shape ->
//button gettext eqals symbol
        ObservableList<Node> children = pane.getChildren();



        //pobrac z grid pane wszytskie buttony ktore maja X lub O

        // sprawdzic czy jest 3 lub wiecej //dodatkowa klasa ze wspolrzednymi X,Y i symbol("x"lub O)
        //mozna zmapowac na li
        //GridPane.getColumnIndex(node);
        //lista position - >sprawdzenie czy sa w jednym wierszu - >zamienic na mape (integer-numer wiersza,integer-ilosc wystapien)
        //ide po liscie i sprawdzam czy istnieje w mapie - >jezeli tak to zwiekszam o 1 ; sprawdznie czy jakakolwiek wartosc jest rowna 3 //nie klucz
        //nastepnie  metodta z nuemrem wiersza
        //sout - > boolean; true lub false

    }

    public class coOrdinates{


    }

    private void markComputerTurn(List<Node> emptyButtons) {
        Random random = new Random();
        int chosenButtonNumber=random.nextInt(emptyButtons.size());
        Button button = (Button)emptyButtons.get(chosenButtonNumber);
        button.setText("O");

    }


    public static void main (String args[]){
        launch(args);
    }
}
