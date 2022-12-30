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
    private TableColumn<Reservation, String> reservation_priceColumn;

    @FXML
    private Spinner<Integer> reservation_quantity;

    @FXML
    private TableColumn<Reservation, String> reservation_quantityColumn;

    @FXML
    private Button reservation_reserveButton;

    @FXML
    private ComboBox<?> reservation_serviceID;

    @FXML
    private TableColumn<Reservation, String> reservation_serviceIDColumn;

    @FXML
    private ComboBox<?> reservation_serviceName;

    @FXML
    private TableColumn<Reservation, String> reservation_serviceNameColumn;

    @FXML
    private TableView<Reservation> reservation_tableView;

    @FXML
    private Label reservation_total;

    @FXML
    private TableColumn<Reservation, String> reservation_dateColumn;

    @FXML
    private Button reservation_addToReservationButton;


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

            reservationShowList();
            reservationServiceId();
            reservationServiceName();
            reservationSpinner();
            reservationDisplayTotal();
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
                service  = new Service(result.getString("service_Name"),result.getDouble("service_Price"),result.getDate("service_Date"),result.getString("service_Status"));
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


    private int userId;
    public void reservationUserId() {
        String sql = "SELECT MAX(user_Id) FROM reservations";
        connect = DatabaseConnection.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if(result.next()) {
                userId = result.getInt("Max(user_Id)");
            }

            int count = 0;
            String checkInfo = "SELECT MAX(customer_Id) FROM customer_info";
            prepare = connect.prepareStatement(checkInfo);
            result = prepare.executeQuery();

            if(result.next()) {
                count = result.getInt("MAX(customer_Id)");
            }

            if(userId == 0) {
                userId +=1;
            }
            else if(userId == count) {
                userId = count +1;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reservationServiceId() {
        String sql = "SELECT service_Id,service_Status FROM services WHERE service_Status = 'Available'";

        connect = DatabaseConnection.connectDB();
        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                listData.add(result.getInt("service_Id"));
            }
            reservation_serviceID.setItems(listData);
            reservationServiceName();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reservationServiceName() {
        String sql = "SELECT service_Id,service_Name from services WHERE service_Id = '" + reservation_serviceID.getSelectionModel().getSelectedItem() + "'";
        connect = DatabaseConnection.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();
            while (result.next()) {
                listData.add(result.getString("service_Name"));
            }
            reservation_serviceName.setItems(listData);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SpinnerValueFactory<Integer> spinner;
    public void reservationSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,1);
        reservation_quantity.setValueFactory(spinner);
    }

    private int qty;
    public void reservationQuantity() {
        qty = reservation_quantity.getValue();
    }


    public ObservableList<Reservation> reservationsListData() {
        reservationUserId();
        ObservableList<Reservation> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer_info WHERE customer_Id = '" +userId+"'";

        connect = DatabaseConnection.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Reservation reservation;
            while (result.next()) {
                reservation = new Reservation(result.getInt("user_Id"),result.getInt("service_Id"),result.getString("reservation_Name"),
                        result.getInt("reservation_Quantity"),result.getDouble("reservation_Price"),result.getDate("reservation_Date"));
                list.add(reservation);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addToReservations() {
        reservationUserId();
        String sql = "INSERT INTO reservations (user_Id,service_Id,reservation_Name,reservation_Quantity,reservation_Price,reservation_Date)" +
                "VALUES(?,?,?,?,?,?)";

        connect = DatabaseConnection.connectDB();
        try {

            Alert alert;
            if(reservation_serviceID.getSelectionModel().getSelectedItem() == null || reservation_serviceName.getSelectionModel().getSelectedItem() == null || qty == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose the service");
                alert.showAndWait();
            } else {

                double price = 0;
                double totalPrice;
                String priceValue = "SELECT service_Name,service_Price FROM services WHERE service_Name = \"" + reservation_serviceName.getSelectionModel().getSelectedItem() + "\"";
                statement = connect.createStatement();
                result = statement.executeQuery(priceValue);
                if (result.next()) {
                    price = result.getDouble("service_Price");
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(userId));
                prepare.setInt(2, (Integer) reservation_serviceID.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) reservation_serviceName.getSelectionModel().getSelectedItem());
                prepare.setString(4, String.valueOf(qty));

                totalPrice = (price * qty);
                prepare.setString(5, String.valueOf(totalPrice));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(6, String.valueOf(sqlDate));

                prepare.executeUpdate();
                reservationsListData();
                reservationDisplayTotal();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private double totalPrice = 0;
    public void reservationDisplayTotal() {
        reservationUserId();
        String sql = "SELECT SUM(reservation_Price) FROM reservations WHERE user_Id = '" + userId+"'";

        connect = DatabaseConnection.connectDB();
        try {
            prepare = connect.prepareStatement(sql);

            if(result.next()) {
                totalPrice = result.getDouble("SUM(reservation_Price)");
                reservation_total.setText("$" + String.valueOf(totalPrice));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reservationReserve() {
        String sql = "INSERT INTO customer_info (customer_Id,total,date) VALUES (?,?,?)";

        connect = DatabaseConnection.connectDB();
        try {
            Alert alert;
            if(totalPrice == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Something was wrong");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Are yo sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)) {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(userId));
                    prepare.setString(2, String.valueOf(totalPrice));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("Reserved!");
                    alert.showAndWait();
                    totalPrice = 0;

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Reservation> reservationsList;
    public void reservationShowList() {
        reservationsList = reservationsListData();

        reservation_serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Name"));
        reservation_quantityColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Quantity"));
        reservation_priceColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Price"));
        reservation_dateColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Date"));

        reservation_tableView.setItems(reservationsList);
    }

    public void homeAS() {
        String sql = "SELECT COUNT(service_Id) FROM services WHERE service_Status = 'Available'";

        connect = DatabaseConnection.connectDB();
        try {
            int countAS = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if (result.next()) {
                countAS = result.getInt("COUNT(service_Id)");
            }
            home_availableServices.setText(String.valueOf(countAS));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableServiceShowList();
        availableServicesStatus();
        reservationShowList();
        reservationServiceId();
        reservationServiceName();
        reservationSpinner();
    }
}
