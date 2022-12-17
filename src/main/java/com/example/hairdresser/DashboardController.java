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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private TableColumn<Service, String> availableService_IDColumn;

    @FXML
    private TableColumn<Service, String> availableService_NameColumn;

    @FXML
    private TableColumn<Service, String> availableService_PriceColumn;

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
    private TableView<Service> availableService_tableView;

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


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void switchForm(ActionEvent event) {
        if(event.getSource() == home_Button) {
            home_form.setVisible(true);
            availableService_form.setVisible(false);
            reservation_form.setVisible(false);

            home_Button.setStyle("-fx-background-color: red");
            availableServices_Button.setStyle("-fx-background-color: transprarent");
            reservation_Button.setStyle("-fx-background-color: transprarent");

        }else if(event.getSource() == availableServices_Button) {
            home_form.setVisible(false);
            availableService_form.setVisible(true);
            reservation_form.setVisible(false);

            availableServiceShowList();

            home_Button.setStyle("-fx-background-color: transparent");
            availableServices_Button.setStyle("-fx-background-color: red");
            reservation_Button.setStyle("-fx-background-color: transprarent");
        }else if(event.getSource() == reservation_Button) {
            home_form.setVisible(false);
            availableService_form.setVisible(false);
            reservation_form.setVisible(true);

            home_Button.setStyle("-fx-background-color: transparent");
            availableServices_Button.setStyle("-fx-background-color: transprarent");
            reservation_Button.setStyle("-fx-background-color: red");
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

    public ObservableList<Service> availableServices() {

        String sql = "SELECT * FROM services";
        ObservableList<Service> listData = FXCollections.observableArrayList();
        connect = DatabaseConnection.connectDB();
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            Service service;

            while (result.next()) {
                service  = new Service(result.getInt("service_Id"),result.getString("service_Name"),result.getDouble("service_price"),result.getDate("service_Date"));
                listData.add(service);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return  listData;
    }

    private ObservableList<Service> availableServicesList;
    public void availableServiceShowList() {
        availableServicesList = availableServices();

        availableService_IDColumn.setCellValueFactory(new PropertyValueFactory<>("service_Id"));
        availableService_NameColumn.setCellValueFactory(new PropertyValueFactory<>("service_Name"));
        availableService_PriceColumn.setCellValueFactory(new PropertyValueFactory<>("service_Price"));

        availableService_tableView.setItems(availableServicesList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableServiceShowList();
    }
}
