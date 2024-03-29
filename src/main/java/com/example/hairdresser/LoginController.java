package com.example.hairdresser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class that manages the login panel
 * @author Kamil Matusz
 */
public class LoginController {
    @FXML
    private Hyperlink signIn_createAccount;

    @FXML
    private AnchorPane signIn_form;

    @FXML
    private Button signIn_loginButton;

    @FXML
    private PasswordField signIn_password;

    @FXML
    private TextField signIn_username;

    @FXML
    private Hyperlink signUp_alreadyHaveAccount;

    @FXML
    private Button signUp_button;

    @FXML
    private TextField signUp_email;

    @FXML
    private AnchorPane signUp_form;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private TextField signUp_username;

    private Connection connect;
    private PreparedStatement prepare;
    private PreparedStatement prepare2;
    private Statement statement;
    private ResultSet result;

    /**
     * A method that manages users' login to the database
     */
    public void signIn() {
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        connect = DatabaseConnection.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, signIn_username.getText());
            prepare.setString(2, signIn_password.getText());
            result = prepare.executeQuery();

            Alert alert;

            if (signIn_username.getText().isEmpty() || signIn_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Login");
                    alert.showAndWait();

                    signIn_loginButton.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username or Password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating new user account and insert them to the database
     */
    public void SignUp() {

        String sql = "INSERT INTO users (username,email,password) VALUES (?,?,?)";
        String sql_validation = "SELECT COUNT(username) FROM users WHERE username= '" + signUp_username.getText() + "'";

        connect = DatabaseConnection.connectDB();
        try {
            Alert alert;
            if(signUp_username.getText().isEmpty() || signUp_email.getText().isEmpty() || signUp_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else if(signUp_password.getText().length() < 8) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Password is too low");
                alert.showAndWait();
            }
            else {
                int usernameCount = 0;
                if (vaildationEmail()) {
                    statement = connect.createStatement();
                    result = statement.executeQuery(sql_validation);
                    if(result.next()) {
                        usernameCount = result.getInt("COUNT(username)");
                    }
                    if(usernameCount !=0) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("This username was already exist!");
                        alert.showAndWait();
                        System.out.println("wiecej niz 0");
                    }
                    else {
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1,signUp_username.getText());
                        prepare.setString(2,signUp_email.getText());
                        prepare.setString(3,signUp_password.getText());
                        prepare.execute();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully create a new account!");
                        alert.showAndWait();

                        signUp_username.setText("");
                        signUp_password.setText("");
                        signUp_email.setText("");
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switching between the login and account creation panel
     *
     * @param event
     */
    public void switchForms(ActionEvent event) {
        if (event.getSource() == signIn_createAccount) {
            signIn_form.setVisible(false);
            signUp_form.setVisible(true);
        } else if (event.getSource() == signUp_alreadyHaveAccount) {
            signIn_form.setVisible(true);
            signUp_form.setVisible(false);
        }
    }

    /**
     * Method which validate user email address
     * @return true/false status indicating whether the email address meets the criteria
     */
    public boolean vaildationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Alert alert;
        Matcher match = pattern.matcher(signUp_email.getText());
        if (match.find() && match.group().matches(signUp_email.getText())) {
            return true;
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ErrorMessage");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email");
            alert.showAndWait();
            return false;
        }
    }
}