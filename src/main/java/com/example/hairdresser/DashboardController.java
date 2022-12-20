package com.example.hairdresser;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.*;

public class DashboardController implements Initializable {
    @FXML
    private TableColumn<Service, String> availableService_NameColumn;

    @FXML
    private TableColumn<Service, String> availableService_PriceColumn;

    @FXML
    private TableColumn<Service, String> availableService_StatusColumn;

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
    private TextField availableService_serviceName;

    @FXML
    private TextField availableService_servicePrice;

    @FXML
    private ComboBox<?> availableService_serviceStatus;

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
    private AnchorPane main_form;

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
            availableServicesStatus();

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

    public void availableServicesSearch() {
        FilteredList<Service> filter = new FilteredList<>(availableServicesList, e-> true);

        availableService_search.textProperty().addListener((Observable,oldValue,newValue) -> {
            filter.setPredicate(PredicateService ->{
                if(newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if(PredicateService.getService_Name().contains((searchKey))) {
                    return  true;
                }else {
                    return false;
                }
            } );
        });
        SortedList<Service> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(availableService_tableView.comparatorProperty());
        availableService_tableView.setItems(sortList);
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

    String listStatus[] = {"Available","Not available"};
    public void availableServicesStatus() {
        List<String> listStatusService = new ArrayList<>();
        for(String data: listStatus) {
            listStatusService.add(data);
        }
        ObservableList listService = FXCollections.observableArrayList(listStatusService);
        availableService_serviceStatus.setItems(listService);
    }

    public void availableServices_Add() {
        String sql = "INSERT INTO services (service_Name,service_Price,service_Date,service_Status)" + "VALUES (?,?,?,?)";

        connect = DatabaseConnection.connectDB();
        try {

            Alert alert;
            if(availableService_serviceName.getText().isEmpty() || availableService_servicePrice.getText().isEmpty()
                    || availableService_serviceStatus.getSelectionModel().getSelectedItem() == null ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
            } else {

                String checkData = "SELECT service_Name from services WHERE service_Name = '" + availableService_serviceName.getText().isEmpty() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("This service exist");
                    alert.showAndWait();
                }
                else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, availableService_serviceName.getText());
                    prepare.setString(2, availableService_servicePrice.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.setString(4, String.valueOf(availableService_serviceStatus.getSelectionModel().getSelectedItem()));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Services succesfully added");
                    alert.showAndWait();

                    availableServiceShowList();
                    availableServicesClear();
                    availableServicesSearch();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableServicesUpdate() {
        String sql = "UPDATE services SET service_Name = \"" + availableService_serviceName.getText() + "\", service_Price = '" + availableService_servicePrice.getText() + "', service_Status = '" + availableService_serviceStatus.getSelectionModel().getSelectedItem() + "'" +
                "WHERE service_Name = \"" + availableService_serviceName.getText() + "\"";

        connect = DatabaseConnection.connectDB();
        try {
            Alert alert;
            if(availableService_serviceName.getText().isEmpty() || availableService_servicePrice.getText().isEmpty()
                    || availableService_serviceStatus.getSelectionModel().getSelectedItem() == null ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
            }
            else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to Update ?");
               Optional<ButtonType> option = alert.showAndWait();

               if(option.get().equals(ButtonType.OK)) {
                   statement = connect.createStatement();
                   statement.executeUpdate(sql);

                   alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Information message");
                   alert.setHeaderText(null);
                   alert.setContentText("Successfully Updated!");
                   alert.showAndWait();

                   availableServiceShowList();
                   availableServicesClear();
               }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableServicesDelete() {
        String sql = "DELETE FROM services WHERE service_Name = \"" + availableService_serviceName.getText() + "\"";
        connect = DatabaseConnection.connectDB();
        try {
            Alert alert;

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to Delete ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    availableServiceShowList();
                    availableServicesClear();
                }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableServicesSelect() {
        Service service = availableService_tableView.getSelectionModel().getSelectedItem();
        int number = availableService_tableView.getSelectionModel().getSelectedIndex();
        if((number-1) < -1) return;
        availableService_serviceName.setText(service.getService_Name());
        availableService_servicePrice.setText(String.valueOf(service.getService_Price()));

    }

    public void availableServicesClear() {
        availableService_serviceName.setText("");
        availableService_servicePrice.setText("");
        availableService_serviceStatus.getSelectionModel().clearSelection();

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
                service  = new Service(result.getString("service_Name"),result.getDouble("service_price"),result.getDate("service_Date"),result.getString("service_Status"));
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

        availableService_NameColumn.setCellValueFactory(new PropertyValueFactory<>("service_Name"));
        availableService_PriceColumn.setCellValueFactory(new PropertyValueFactory<>("service_Price"));
        availableService_StatusColumn.setCellValueFactory(new PropertyValueFactory<>("service_Status"));

        availableService_tableView.setItems(availableServicesList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableServiceShowList();
        availableServicesStatus();
    }
}
