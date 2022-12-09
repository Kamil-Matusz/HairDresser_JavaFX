package com.example.hairdresser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
