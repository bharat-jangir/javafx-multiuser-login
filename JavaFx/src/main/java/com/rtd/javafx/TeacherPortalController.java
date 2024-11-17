package com.rtd.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class TeacherPortalController implements Initializable {
    @FXML
    private Button teacher_loginbtn;

    @FXML
    private TextField teacher_password;

    @FXML
    private ComboBox<String> teacher_select;

    @FXML
    private TextField teacher_teacherID;

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

        if (teacher_teacherID.getText().isEmpty() || teacher_password.getText().isEmpty()) {
            errorMessage("Username and Password cannot be empty");
        }else{
            String setectData="SELECT * FROM teacher WHERE teacher_id=? AND password=?";

            connection=Connect.connectDB();

            try{
                preparedStatement=connection.prepareStatement(setectData);
                preparedStatement.setString(1,teacher_teacherID.getText());
                preparedStatement.setString(2,teacher_password.getText());
                resultSet=preparedStatement.executeQuery();

                if (resultSet.next()){
                    successMessage("Successfully logged in");
                    Stage stage=new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("teacherMainForm.fxml"));
                    Scene scene=new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    teacher_loginbtn.getScene().getWindow().hide();

                }else{
                    errorMessage("TeacherID or Password Incorrect");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public void switchForm(){

        try{
            Parent root=null;
        if(teacher_select.getSelectionModel().getSelectedItem().equals("Admin Portal")){
            root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        } else if (teacher_select.getSelectionModel().getSelectedItem().equals("Student Portal")) {
            root= FXMLLoader.load(getClass().getResource("StudentPortal.fxml"));
        }else {
            root= FXMLLoader.load(getClass().getResource("TeacherPortal.fxml"));
        }

        Stage stage = new Stage();
        stage.setTitle(teacher_select.getValue());
        stage.setScene(new Scene(root));
        stage.show();
        teacher_select.getScene().getWindow().hide();

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void selectUser() {
        // Example user list, replace with actual user data
        List<String> userList = new ArrayList<>();
        for (String data:users.users){
            userList.add(data);
        }
        // Create an ObservableList to populate the ComboBox
        ObservableList<String> listData = FXCollections.observableArrayList(userList);

        // Set items in the ComboBox
        teacher_select.setItems(listData);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectUser();
    }
}
