package com.example.hairdresser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
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

    public void switchForms(ActionEvent event) {
        if(event.getSource() == signIn_createAccount) {
            signIn_form.setVisible(false);
            signUp_form.setVisible(true);
        }else if(event.getSource() == signUp_alreadyHaveAccount) {
            signIn_form.setVisible(true);
            signUp_form.setVisible(false);
        }
    }

    public boolean vaildationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Alert alert;
        Matcher match = pattern.matcher(signUp_email.getText());
        if(match.find() && match.group().equals(signUp_email.getText())) {
            return true;
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ErrorMessage");
            alert.setHeaderText(null);
            alert.setContentText("Invalid emial");
            alert.showAndWait();
            return false;
        }
    }
}