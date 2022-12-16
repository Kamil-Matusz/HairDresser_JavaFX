package com.example.hairdresser;

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
    private AnchorPane main_form;

    @FXML
    private TableColumn<?, ?> availableService_NameColumn;

    @FXML
    private TableColumn<?, ?> availableService_PriceColumn;

    @FXML
    private Button availableService_addButton;

    @FXML
    private Button availableService_clearButton;

    @FXML
    private Button availableService_deleteButton;

    @FXML
    private AnchorPane availableService_form;

    @FXML
    private TextField availableService_search;

    @FXML
    private TextField availableService_serviceID;

    @FXML
    private TextField availableService_serviceName;

    @FXML
    private TextField availableService_servicePrice;

    @FXML
    private TableView<?> availableService_tableView;

    @FXML
    private Button availableService_updateButton;

    @FXML
    private Button availableServices_Button;

    @FXML
    private Button home_Button;

    @FXML
    private Label home_availableServices;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_total;

    @FXML
    private Label home_totalReservation;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane rese;

    @FXML
    private Button reservation_Button;

    @FXML
    private AnchorPane reservation_form;

    @FXML
    private TableColumn<?, ?> reservation_priceColumn;

    @FXML
    private Spinner<?> reservation_quantity;

    @FXML
    private TableColumn<?, ?> reservation_quantityColumn;

    @FXML
    private Button reservation_reserveButton;

    @FXML
    private ComboBox<?> reservation_serviceID;

    @FXML
    private TableColumn<?, ?> reservation_serviceIDColumn;

    @FXML
    private ComboBox<?> reservation_serviceName;

    @FXML
    private TableColumn<?, ?> reservation_serviceNameColumn;

    @FXML
    private TableView<?> reservation_tableView;

    @FXML
    private Label reservation_total;


    public void switchForm(ActionEvent event) {
        if(event.getSource() == home_Button) {
            home_form.setVisible(true);
            availableService_form.setVisible(false);
            reservation_form.setVisible(false);
        }else if(event.getSource() == availableServices_Button) {
            home_form.setVisible(false);
            availableService_form.setVisible(true);
            reservation_form.setVisible(false);
        }else if(event.getSource() == reservation_Button) {
            home_form.setVisible(false);
            availableService_form.setVisible(false);
            reservation_form.setVisible(true);
        }
    }

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {

                logoutButton.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("mainpanel.xml"));
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

    }
}
