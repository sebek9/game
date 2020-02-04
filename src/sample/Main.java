package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Main extends Application {


    @Override

    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();


        final Menu menu1 = new Menu("Plik");
        final Menu menu2 = new Menu("Informacje");
        final Menu menu3=new Menu("Stan gry");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu3,menu2);

        MenuItem menuItem1 = new MenuItem("Nowa gra");
        MenuItem menuItem2 = new MenuItem("Wyjdż");
        MenuItem menuItem3 = new MenuItem("Autorzy");
        MenuItem menuItem4 = new MenuItem("Zapisz grę");
        MenuItem menuItem5 = new MenuItem("Wczytaj grę");

        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);
        menu2.getItems().add(menuItem3);
        menu3.getItems().add(menuItem4);
        menu3.getItems().add(menuItem5);

        GridPane pane = new GridPane();
        createGridPane(pane);

        root.setTop(menuBar);
        root.setCenter(pane);

        Scene scene = new Scene(root, 450, 450);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();


        menuItem3.setOnAction(e -> {
            displayCredentials();
        });

        menuItem2.setOnAction(e -> {
            primaryStage.close();

        });
        menuItem1.setOnAction(e -> {
            createGridPane(pane);

        });
        menuItem4.setOnAction(e -> {
            saveMap();

        });
        menuItem5.setOnAction(e -> {
            loadMap();

        });

    }

    private void createGridPane(GridPane pane) {
        pane.getChildren().clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setMinWidth(150.0);
                button.setMinHeight(150.0);
                button.setOnMouseClicked(e -> {
                    button.setText("X"); //
                    isWon("X", pane);
                    computerTurn(pane); // znalezc wolne pola i dopisac O
                });
                pane.add(button, i, j);

            }

        }
    }


    private void computerTurn(GridPane pane) {
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
        isWon("O", pane);
    }

    private void isWon(String symbol, GridPane pane) {
//ta sama zasada co z Shape ->
//button gettext eqals symbol
        ObservableList<Node> children = pane.getChildren();
        List<Node> allButtons = children.stream().filter(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals(symbol)) {
                    return true;
                }
                return false;
            }
            return false;
        })
                .collect(Collectors.toList());

        if (allButtons.size() > 2) {
            checkPositions(allButtons, symbol);
            displayMessage();
        }


        //pobrac z grid pane wszytskie buttony ktore maja X lub O

        // sprawdzic czy jest 3 lub wiecej //dodatkowa klasa ze wspolrzednymi X,Y i symbol("x"lub O)
        //mozna zmapowac na li
        //GridPane.getColumnIndex(node);
        //lista position - >sprawdzenie czy sa w jednym wierszu - >zamienic na mape (integer-numer wiersza,integer-ilosc wystapien)
        //ide po liscie i sprawdzam czy istnieje w mapie - >jezeli tak to zwiekszam o 1 ; sprawdznie czy jakakolwiek wartosc jest rowna 3 //nie klucz
        //nastepnie  metodta z nuemrem wiersza
        //sout - > boolean; true lub false

    }

    //kazdego noda na coordinates wiersz kolumna
    //przypisac do listy
    //czy wiersz, koliumna sie zgadza(mapa) przypisywac po kolei
    //czy ktorykolwiek z wierszy zawiera trzy (int int)
    public void checkPositions(List<Node> nodes, String symbol) {

        List<coOrdinates> positionsList = nodes.stream().map(node -> {
            Integer row = GridPane.getRowIndex(node);
            Integer column = GridPane.getColumnIndex(node);

            return new coOrdinates(symbol, row, column);
        }).collect(Collectors.toList());
        if (checkRows(positionsList)) { //dodac kolumny ->dodane
            System.out.println("Elementy w jednym wierszu");
        } else if (checkColumns(positionsList)) {
            System.out.println("Elementy w jednej kolumnie");
        }


    }

    private boolean checkRows(List<coOrdinates> positionsList) {
        Map<Integer, Integer> rows = new HashMap<Integer, Integer>();
        for (coOrdinates coordinate : positionsList) {
            if (rows.containsKey(coordinate.getRow())) {
                Integer row = rows.get(coordinate.getRow());
                rows.put(coordinate.getRow(), ++row);
            } else {
                rows.put(coordinate.getRow(), 1);
            }

        }
        return rows.values().stream().anyMatch(i -> i == 3);

    }

    private boolean checkColumns(List<coOrdinates> positionsList) {
        Map<Integer, Integer> columns = new HashMap<Integer, Integer>();
        for (coOrdinates coordinate : positionsList) {
            if (columns.containsKey(coordinate.getColumn())) {
                Integer column = columns.get(coordinate.getColumn());
                columns.put(coordinate.getColumn(), ++column);
            } else {
                columns.put(coordinate.getColumn(), 1);
            }

        }
        return columns.values().stream().anyMatch(i -> i == 3);

    }


    private void markComputerTurn(List<Node> emptyButtons) {
        Random random = new Random();
        int chosenButtonNumber = random.nextInt(emptyButtons.size());
        Button button = (Button) emptyButtons.get(chosenButtonNumber);
        button.setText("O");

    }

    private void displayMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game result");
        alert.setHeaderText("Wygrałeś!!!");

        alert.showAndWait();
    }

    private void displayCredentials() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credentials");
        alert.setHeaderText("Autor: Sebastian Kalinkowski");
        alert.showAndWait();
    }

    File savedHashMaps=new File("ranking.list");
    Map<String, Long> map = new HashMap<>();

    public void saveMap() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(savedHashMaps));
            oos.writeObject(map);
            oos.close();
        } catch (Exception e) {
            // obsługa błędów
        }
    }

    public void loadMap() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedHashMaps));
            Object readMap = ois.readObject();
            if(readMap instanceof HashMap) {
                map.putAll((HashMap) readMap);
            }
            ois.close();
        } catch (Exception e) {
            // obsługa błędów
        }
    }


    public static void main(String args[]) {
        launch(args);
    }
}