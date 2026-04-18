//Heba Alnabahin 220231513
package com.example.demo1.assignment3;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

public class loginPage extends Application {

    private ArrayList<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        showLoginPage(stage);
    }

    private void showLoginPage(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #a9a9a9;");

        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial';");
        grid.add(welcomeLabel, 0, 0, 2, 1);

        TextField userIn = new TextField();
        PasswordField passIn = new PasswordField();
        grid.add(new Label("User Name:"), 0, 1);
        grid.add(userIn, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passIn, 1, 2);

        Button loginBtn = new Button("Sign in");
        Button exitBtn = new Button("Exit");
        String btnStyle = "-fx-background-color: linear-gradient(#5da1a1, #3b6a6a); -fx-text-fill: white;";
        loginBtn.setStyle(btnStyle);
        exitBtn.setStyle(btnStyle);

        loginBtn.setOnAction(e -> {
            if (authenticate(userIn.getText(), passIn.getText())) {
                showOptionsPage(stage, userIn.getText());
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Credentials").show();
            }
        });

        exitBtn.setOnAction(e -> stage.close());
        grid.add(new HBox(10, loginBtn, exitBtn), 1, 3);

        stage.setTitle("Login");
        stage.setScene(new Scene(grid, 400, 300));
        stage.show();
    }

    private void showOptionsPage(Stage stage, String userName) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #a9a9a9;");

        Label welcome = new Label("Welcome " + userName);
        welcome.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button addStudent = new Button("Add Student");
        String btnStyle = "-fx-min-width: 150px; -fx-background-color: #3b6a6a; -fx-text-fill: white;";
        addStudent.setStyle(btnStyle);

        addStudent.setOnAction(e -> showStudentEntryPage(stage));

        root.getChildren().addAll(welcome, addStudent);
        stage.setScene(new Scene(root, 450, 350));
    }

    private void showStudentEntryPage(Stage stage) {
        HBox mainLayout = new HBox(30);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #a9a9a9;");

        VBox leftSide = new VBox(15);
        leftSide.setAlignment(Pos.TOP_LEFT);

        Label header = new Label("Student Data");
        header.setStyle("-fx-font-size: 22px; -fx-font-family: 'Arial';");

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(15);

        TextField idF = new TextField();
        TextField nameF = new TextField();
        TextField majorF = new TextField();
        TextField gradeF = new TextField();

        inputGrid.add(new Label("Id:"), 0, 0);
        inputGrid.add(idF, 1, 0);
        inputGrid.add(new Label("Name:"), 0, 1);
        inputGrid.add(nameF, 1, 1);
        inputGrid.add(new Label("Major:"), 0, 2);
        inputGrid.add(majorF, 1, 2);
        inputGrid.add(new Label("Grade:"), 0, 3);
        inputGrid.add(gradeF, 1, 3);

        HBox actionBtns = new HBox(10);
        Button addBtn = new Button("Add");
        Button resetBtn = new Button("Reset");
        Button exitBtn = new Button("Exit");

        String btnStyle = "-fx-background-color: linear-gradient(#5da1a1, #3b6a6a); -fx-text-fill: white; -fx-min-width: 60px;";
        addBtn.setStyle(btnStyle);
        resetBtn.setStyle(btnStyle);
        exitBtn.setStyle(btnStyle);

        actionBtns.getChildren().addAll(addBtn, resetBtn, exitBtn);
        leftSide.getChildren().addAll(header, inputGrid, actionBtns);

        ListView<Student> listView = new ListView<>();
        listView.setPrefWidth(300);
        listView.setPrefHeight(400);

        addBtn.setOnAction(e -> {
            try {
                Student s = new Student(Integer.parseInt(idF.getText()), nameF.getText(),
                        majorF.getText(), Double.parseDouble(gradeF.getText()));
                studentList.add(s);
                listView.getItems().add(s);
                FXCollections.sort(listView.getItems());
            } catch (Exception ex) {
                new Alert(Alert.AlertType.WARNING, "Check input format!").show();
            }
        });

        resetBtn.setOnAction(e -> {
            idF.clear();
            nameF.clear();
            majorF.clear();
            gradeF.clear();
        });

        exitBtn.setOnAction(e -> showOptionsPage(stage, "User"));

        mainLayout.getChildren().addAll(leftSide, listView);

        // Q6
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: #a9a9a9;");
        root.setAlignment(Pos.CENTER);

        HBox queryBox = new HBox(10);
        queryBox.setAlignment(Pos.CENTER);
        queryBox.setPadding(new Insets(10));
        Button btnAvg = new Button("Avg Grade");
        Button btn8090 = new Button("80-90 Range");
        Button btnGroup = new Button("Group by Major");
        btnAvg.setStyle(btnStyle);
        btn8090.setStyle(btnStyle);
        btnGroup.setStyle(btnStyle);

        btnAvg.setOnAction(e -> {
            double avg = studentList.stream().mapToDouble(Student::getGrade).average().orElse(0.0);
            new Alert(Alert.AlertType.INFORMATION, "Average: " + String.format("%.2f", avg)).show();
        });

        btn8090.setOnAction(e -> {
            String res = studentList.stream()
                    .filter(s -> s.getGrade() >= 80 && s.getGrade() <= 90)
                    .sorted(Comparator.comparing(Student::getGrade).reversed())
                    .map(s -> s.getName() + ": " + s.getGrade())
                    .collect(Collectors.joining("\n"));
            new Alert(Alert.AlertType.INFORMATION, "Filtered (80-90):\n" + (res.isEmpty() ? "None" : res)).show();
        });

        btnGroup.setOnAction(e -> {
            Map<String, java.util.List<Student>> grouped = studentList.stream()
                    .collect(Collectors.groupingBy(Student::getMajor));
            StringBuilder sb = new StringBuilder();
            grouped.forEach((m, list) -> sb.append(m).append(": ").append(list.size()).append(" students\n"));
            new Alert(Alert.AlertType.INFORMATION, "Majors Summary:\n" + sb.toString()).show();
        });

        queryBox.getChildren().addAll(btnAvg, btn8090, btnGroup);
        root.getChildren().addAll(mainLayout, queryBox);

        stage.setScene(new Scene(root, 750, 550));
    }

    private boolean authenticate(String user, String pass) {
        String hashedPass = hashPassword(pass);
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream("/users.txt"))) {
            while (scanner.hasNext()) {
                if (scanner.next().equals(user.trim()) && scanner.next().equals(hashedPass))
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
