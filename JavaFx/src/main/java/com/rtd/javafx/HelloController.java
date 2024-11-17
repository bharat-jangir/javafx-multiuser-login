package com.rtd.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button admin_loginbtn;

    @FXML
    private TextField admin_password;

    @FXML
    private ComboBox<String> admin_user;

    @FXML
    private TextField admin_username;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Alert alert;


    private void errorMessage(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void successMessage(String message) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();;
    }

    public void loginAccount(){

        if (admin_username.getText().isEmpty() || admin_password.getText().isEmpty()) {
            errorMessage("Username and Password cannot be empty");
        }else{
        String setectData="SELECT * FROM admin WHERE username=? AND password=?";

        connection=Connect.connectDB();

        try{
            preparedStatement=connection.prepareStatement(setectData);
            preparedStatement.setString(1,admin_username.getText());
            preparedStatement.setString(2,admin_password.getText());
            resultSet=preparedStatement.executeQuery();

            if (resultSet.next()){
                successMessage("Successfully logged in");
                Stage stage=new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("adminMainForm.fxml"));
                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
                admin_loginbtn.getScene().getWindow().hide();

            }else{
                errorMessage("Username or Password Incorrect");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        }


    }

    // Method to populate ComboBox with user list
    public void selectUser() {
        // Example user list, replace with actual user data
        List<String> userList = new ArrayList<>();
        for (String data:users.users){
            userList.add(data);
        }
        // Create an ObservableList to populate the ComboBox
        ObservableList<String> listData = FXCollections.observableArrayList(userList);

        // Set items in the ComboBox
        admin_user.setItems(listData);
    }

    public void switchForm(){

        try{
            Parent root=null;
            if(admin_user.getSelectionModel().getSelectedItem().equals("Teacher Portal")){
                root= FXMLLoader.load(getClass().getResource("TeacherPortal.fxml"));
            } else if (admin_user.getSelectionModel().getSelectedItem().equals("Student Portal")) {
                root= FXMLLoader.load(getClass().getResource("StudentPortal.fxml"));
            }else {
                root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            }

            Stage stage = new Stage();
            stage.setTitle(admin_user.getValue());
            stage.setScene(new Scene(root));
            stage.show();
            admin_user.getScene().getWindow().hide();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // Initialize method (called when the controller is loaded)

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Optionally, populate ComboBox with default users when the app starts
        selectUser();  // This will populate the ComboBox
    }
}
