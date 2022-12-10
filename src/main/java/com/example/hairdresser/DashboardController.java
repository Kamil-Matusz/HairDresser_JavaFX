package com.example.hairdresser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Button availableServices_Button;

    @FXML
    private Button availableServices_addButton;

    @FXML
    private Button availableServices_clearButton;

    @FXML
    private TableColumn<?, ?> availableServices_col_serviceID;

    @FXML
    private TableColumn<?, ?> availableServices_col_serviceName;

    @FXML
    private TableColumn<?, ?> availableServices_col_servicePrice;

    @FXML
    private Button availableServices_deleteButton;

    @FXML
    private AnchorPane availableServices_form;

    @FXML
    private TextField availableServices_price;

    @FXML
    private TextField availableServices_search;

    @FXML
    private TextField availableServices_serviceID;

    @FXML
    private TextField availableServices_serviceName;

    @FXML
    private TableView<?> availableServices_tableView;

    @FXML
    private Button availableServices_updateButton;

    @FXML
    private Button home_Button;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalIncome;

    @FXML
    private Label home_totalReservations;

    @FXML
    private Button logout_Button;

    @FXML
    private Button purchase_Button;

    @FXML
    private TableColumn<?, ?> purchase_col_Price;

    @FXML
    private TableColumn<?, ?> purchase_col_Quantity;

    @FXML
    private TableColumn<?, ?> purchase_col_ServiceID;

    @FXML
    private TableColumn<?, ?> purchase_col_Service_Name;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Button purchase_payButton;

    @FXML
    private Spinner<?> purchase_quantity;

    @FXML
    private ComboBox<?> purchase_serviceId;

    @FXML
    private ComboBox<?> purchase_serviceName;

    @FXML
    private TableView<?> purchase_tableView;

    @FXML
    private Label purchase_total;

    @FXML
    private Label username;

    @FXML
    private AnchorPane main_form;

    public void displayUsername() {
        String user = User.path;
        username.setText(user.substring(0,1).toUpperCase() + user.substring(1));
    }

    public ObservableList<Service> availableServiceList() {
        ObservableList<Service> listData = FXCollections.observableArrayList();
    }

    public void switchForm(ActionEvent event) {
        if(event.getSource() == home_Button) {
            home_form.setVisible(true);
            availableServices_form.setVisible(false);
            purchase_form.setVisible(false);

            home_Button.setStyle(" -fx-background-color: linear-gradient(to right top, #370521, #50325e, #4f649c, #2e99ce, #12cfeb)");
            availableServices_Button.setStyle("-fx-background-color: transparent");
            purchase_Button.setStyle("-fx-background-color: transparent");
        }
        else if(event.getSource() == availableServices_Button) {
            home_form.setVisible(false);
            availableServices_form.setVisible(true);
            purchase_form.setVisible(false);

            home_Button.setStyle(" -fx-background-color: linear-gradient(to right top, #370521, #50325e, #4f649c, #2e99ce, #12cfeb)");
            availableServices_Button.setStyle("-fx-background-color: transparent");
            purchase_Button.setStyle("-fx-background-color: transparent");
        }
        else if(event.getSource() == purchase_Button) {
            home_form.setVisible(false);
            availableServices_form.setVisible(false);
            purchase_form.setVisible(true);

            home_Button.setStyle(" -fx-background-color: linear-gradient(to right top, #370521, #50325e, #4f649c, #2e99ce, #12cfeb)");
            availableServices_Button.setStyle("-fx-background-color: transparent");
            purchase_Button.setStyle("-fx-background-color: transparent");
        }
    }

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {

                logout_Button.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("mainpanel.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
    }
}
