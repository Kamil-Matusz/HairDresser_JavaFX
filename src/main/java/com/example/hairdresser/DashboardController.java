package com.example.hairdresser;

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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The class that manages the main application
 * @author Kamil Matusz
 */
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

    @FXML
    private TextField reservation_PhoneNumber;

    @FXML
    private TextField reservation_Email;

    @FXML
    private BarChart<?, ?> home_Chart;

    @FXML
    private DatePicker reservation_DateValue;

    @FXML
    private AnchorPane reservation_DatePicker;

    @FXML
    private TableColumn<Reservation, String> reservation_EmailColumn;

    @FXML
    private TableColumn<Reservation, String> reservation_PhoneColumn;


    private Connection connect;
    private PreparedStatement prepare;
    private PreparedStatement prepare2;
    private Statement statement;
    private ResultSet result;

    /**
     * Switching between application panels
     * @param event
     */
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_Button) {
            home_form.setVisible(true);
            availableService_form.setVisible(false);
            reservation_form.setVisible(false);

            home_Button.setStyle("-fx-background-color: red");
            availableServices_Button.setStyle("-fx-background-color: transprarent");
            reservation_Button.setStyle("-fx-background-color: transprarent");

            homeAS();
            homeTR();
            homeTP();
            chart();

        } else if (event.getSource() == availableServices_Button) {
            home_form.setVisible(false);
            availableService_form.setVisible(true);
            reservation_form.setVisible(false);

            availableServiceShowList();
            availableServicesStatus();

            home_Button.setStyle("-fx-background-color: transparent");
            availableServices_Button.setStyle("-fx-background-color: red");
            reservation_Button.setStyle("-fx-background-color: transprarent");

        } else if (event.getSource() == reservation_Button) {
            home_form.setVisible(false);
            availableService_form.setVisible(false);
            reservation_form.setVisible(true);

            home_Button.setStyle("-fx-background-color: transparent");
            availableServices_Button.setStyle("-fx-background-color: transprarent");
            reservation_Button.setStyle("-fx-background-color: red");

            reservationShowList();
            reservationServiceName();
            reservationSpinner();
            reservationServicePrice();
        }
    }

    /**
     * Logout option for user
     */
    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logoutButton.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String listStatus[] = {"Available", "Not available"};
    /**
     * Displaying service status
     */
    public void availableServicesStatus() {
        List<String> listStatusService = new ArrayList<>();
        for (String data : listStatus) {
            listStatusService.add(data);
        }
        ObservableList listService = FXCollections.observableArrayList(listStatusService);
        availableService_serviceStatus.setItems(listService);
    }

    /**
     * Adding service for the database
     */
    public void availableServices_Add() {
        String sql = "INSERT INTO services (service_Name,service_Price,service_Date,service_Status)" + "VALUES (?,?,?,?)";
        connect = DatabaseConnection.connectDB();
        double servicePrice = 0;
        try {

            Alert alert;
            if (availableService_serviceName.getText().isEmpty() || availableService_servicePrice.getText().isEmpty()
                    || availableService_serviceStatus.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
            } else {
                try {
                    servicePrice = Double.parseDouble(availableService_servicePrice.getText());
                }
                catch (NumberFormatException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("The price must be number!");
                    alert.showAndWait();
                }
                if (servicePrice < 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("The price cannot be smaller than 1 !");
                    alert.showAndWait();
                } else {
                    String checkData = "SELECT service_Name from services WHERE service_Name = '" + availableService_serviceName.getText() + "'";
                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);

                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("This service exist");
                        alert.showAndWait();
                    } else {

                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1, availableService_serviceName.getText());
                        prepare.setDouble(2, Double.parseDouble(availableService_servicePrice.getText()));

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
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("This service already exist");
            alert.showAndWait();
        }

    }

    /**
     * Update record in database
     */
    public void availableServicesUpdate() {
        double price = Double.parseDouble(availableService_servicePrice.getText());
        if(price <= 0) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Price cannot be smaller than 1!");
            alert.showAndWait();
        }
        else {
        String sql = "UPDATE services SET service_Name = \"" + availableService_serviceName.getText() + "\", service_Price = '" + availableService_servicePrice.getText() + "', service_Status = '" + availableService_serviceStatus.getSelectionModel().getSelectedItem() + "'" +
                "WHERE service_Name = \"" + availableService_serviceName.getText() + "\"";

        connect = DatabaseConnection.connectDB();
        try {
            Alert alert;
            if (availableService_serviceName.getText().isEmpty() || availableService_servicePrice.getText().isEmpty()
                    || availableService_serviceStatus.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to Update ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    /**
     * Delete service from Database
     */
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Selecting a record from the table
     */
    public void availableServicesSelect() {
        Service service = availableService_tableView.getSelectionModel().getSelectedItem();
        int number = availableService_tableView.getSelectionModel().getSelectedIndex();
        if ((number - 1) < -1) return;
        availableService_serviceName.setText(service.getService_Name());
        availableService_servicePrice.setText(String.valueOf(service.getService_Price()));
    }

    /**
     * Clearing values in fields
     */
    public void availableServicesClear() {
        availableService_serviceName.setText("");
        availableService_servicePrice.setText("");
        availableService_serviceStatus.getSelectionModel().clearSelection();
    }

    /**
     * Retrieving a list of all services from the database
     * @return List which containing all services with parameters
     */
    public ObservableList<Service> availableServices() {

        String sql = "SELECT * FROM services";
        ObservableList<Service> listData = FXCollections.observableArrayList();
        connect = DatabaseConnection.connectDB();
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            Service service;

            while (result.next()) {
                service = new Service(result.getString("service_Name"), result.getDouble("service_Price"), result.getDate("service_Date"), result.getString("service_Status"));
                listData.add(service);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Service> availableServicesList;
    /**
     * Displaying elements of the service class in a table
     */
    public void availableServiceShowList() {
        availableServicesList = availableServices();

        availableService_NameColumn.setCellValueFactory(new PropertyValueFactory<>("service_Name"));
        availableService_PriceColumn.setCellValueFactory(new PropertyValueFactory<>("service_Price"));
        availableService_StatusColumn.setCellValueFactory(new PropertyValueFactory<>("service_Status"));

        availableService_tableView.setItems(availableServicesList);
    }

    private int userId;
    /**
     * Retreving the unique user Id from database
     */
    public void reservationUserId() {
        String sql = "SELECT MAX(user_Id) FROM reservations";
        connect = DatabaseConnection.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                userId = result.getInt("Max(user_Id)");
            }
            int count = 0;
            if (userId == 0) {
                userId += 1;
            } else if (userId == count) {
                userId = count + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieving the service name from the table based on its unique name
     */
    public void reservationServiceName() {
        String sql = "SELECT service_Name,service_Status FROM services WHERE service_Status = 'Available'";
        connect = DatabaseConnection.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();
            while (result.next()) {
                listData.add(result.getString("service_Name"));
            }
            reservation_serviceName.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double price = 0;
    /**
     * Retrieving the service price from the table based on its unique id
     */
    public void reservationServicePrice() {
        reservationUserId();
        String sql = "SELECT service_Name,service_Price from services WHERE service_Name = '" + reservation_serviceName.getSelectionModel().getSelectedItem() + "'";
        connect = DatabaseConnection.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()) {
                price = result.getDouble("service_Price");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SpinnerValueFactory<Integer> spinner;
    /**
     * Setting the quantity of ordered services
     */
    public void reservationSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        reservation_quantity.setValueFactory(spinner);
    }

    private int qty;
    /**
     * Getting a numerical value from the Quantity field
     */
    public void reservationQuantity() {
        qty = reservation_quantity.getValue();
    }

    /**
     * Retrieving information about all available bookings
     * @return list of reservations
     */
    public ObservableList<Reservation> reservationsListData() {
        reservationUserId();
        ObservableList<Reservation> list = FXCollections.observableArrayList();

        //String sql = "SELECT * FROM reservations WHERE user_Id = '" + userId + "'";
        String sql = "SELECT reservations.user_Id,services.service_Name,reservations.reservation_Quantity,reservations.reservation_Price,reservations.reservation_Date,reservations.email,reservations.phone_Number\n" +
                "FROM reservations\n" +
                "JOIN services\n" +
                "WHERE reservations.service_Id = services.service_Id";

        connect = DatabaseConnection.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Reservation reservation;
            while (result.next()) {
                reservation = new Reservation(result.getInt("user_Id"), result.getString("service_Name"),
                        result.getInt("reservation_Quantity"), result.getDouble("reservation_Price"), result.getDate("reservation_Date"),result.getString("phone_Number"),result.getString("email"));
                list.add(reservation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Adding a reservation record to the appropriate table based on the form
     */
    public void addToReservations() {
        reservationUserId();
        String sql = "INSERT INTO reservations (user_Id,service_Id,reservation_Quantity,reservation_Price,reservation_Date,phone_Number,email)" +
                "VALUES(?,?,?,?,?,?,?)";

        connect = DatabaseConnection.connectDB();
        try {

            Alert alert;
            if (reservation_serviceName.getSelectionModel().getSelectedItem() == null || qty == 0 || reservation_PhoneNumber.getText() == null || reservation_Email.getText() == null || reservation_DateValue.getValue() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks fields");
                alert.showAndWait();
            } else {
                try {
                    if (vaildationEmailReservation() && validationPhoneNumber()) {
                            double price = 0;
                            int serviceId = 0;
                            double totalPrice;
                            String priceValue = "SELECT service_Id,service_Name,service_Price FROM services WHERE service_Name = \"" + reservation_serviceName.getSelectionModel().getSelectedItem() + "\"";
                            statement = connect.createStatement();
                            result = statement.executeQuery(priceValue);
                            if (result.next()) {
                                price = result.getDouble("service_Price");
                                serviceId = result.getInt("service_Id");
                            }

                            prepare = connect.prepareStatement(sql);
                            prepare.setString(1, String.valueOf(userId));
                            //prepare.setString(2, (String) reservation_serviceName.getSelectionModel().getSelectedItem());
                            prepare.setString(2, String.valueOf(serviceId));
                            prepare.setString(3, String.valueOf(qty));

                            totalPrice = (price * qty);
                            prepare.setString(4, String.valueOf(totalPrice));

                            LocalDate date = reservation_DateValue.getValue();

                            prepare.setString(5, String.valueOf(date));
                            prepare.setString(6, String.valueOf(reservation_PhoneNumber.getText()));
                            prepare.setString(7, String.valueOf(reservation_Email.getText()));
                            prepare.executeUpdate();
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Creating reservation!");
                            alert.showAndWait();
                            reservationsListData();
                            reservationShowList();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ErrorMessage");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid email or phone number");
                        alert.showAndWait();
                    }
                }catch (PatternSyntaxException e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ErrorMessage");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid symbol in phone number");
                    alert.showAndWait();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double totalPrice = 0;
    Date date = new Date();
    java.sql.Date todayDate = new java.sql.Date(date.getTime());

    /**
     * Displaying elements of the reservation class in a table
     */
    private ObservableList<Reservation> reservationsList;
    public void reservationShowList() {
        reservationsList = reservationsListData();

        reservation_serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Name"));
        reservation_quantityColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Quantity"));
        reservation_priceColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Price"));
        reservation_dateColumn.setCellValueFactory(new PropertyValueFactory<>("reservation_Date"));
        reservation_PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone_Number"));
        reservation_EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        reservation_tableView.setItems(reservationsList);
    }

    /**
     * Display count of all available services
     */
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Display money value of all made reservations
     */
    public void homeTP() {
        String sql = "SELECT  SUM(reservation_Price) FROM reservations";

        connect = DatabaseConnection.connectDB();
        try {
            int countTR = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                countTR = result.getInt("SUM(reservation_Price)");
            }

            home_total.setText("$" + String.valueOf(countTR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Display number of all made reservations
     */
    public void homeTR() {
        String sql = "SELECT COUNT(reservation_Id) FROM reservations";

        connect = DatabaseConnection.connectDB();
        try {
            int countTR = 0;
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                countTR = result.getInt("COUNT(reservation_Id)");
            }

            home_totalReservation.setText(String.valueOf(countTR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String reservation_Name;
    int reservation_Quantity;
    LocalDate reservation_Date;
    String Reservation_Email;
    String Reservation_Phone;


    /**
     * Generate receipt to pdf file based on a record from the  reservations table
     */
    public void createPDF() {

        Alert alert;
        Document document = new Document();
        connect = DatabaseConnection.connectDB();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\Receipt.pdf"));
            //PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Receipt.pdf"));
            document.open();

            reservation_Name = String.valueOf(reservation_serviceName.getValue());
            reservation_Quantity = reservation_quantity.getValue();
            reservation_Date = reservation_DateValue.getValue();
            Reservation_Email = reservation_Email.getText();
            Reservation_Phone = reservation_PhoneNumber.getText();
            if(reservation_Name == null || reservation_Quantity == 0 || reservation_Date == null || reservation_Email == null || reservation_PhoneNumber == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("There are no services to generate on the receipt");
                alert.showAndWait();
            }
            else {
                document.add(new Paragraph("                                                                    HairDresserSalon"));
                document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
                document.add(new Paragraph("Service Name: " + String.valueOf(reservation_Name)));
                document.add(new Paragraph("Quantity: " + String.valueOf(reservation_Quantity)));
                document.add(new Paragraph("Reservation Date: " + String.valueOf(reservation_Date)));
                document.add(new Paragraph("Email: " + String.valueOf(Reservation_Email)));
                document.add(new Paragraph("Phone: " + "+" + String.valueOf(Reservation_Phone)));
                document.close();
                writer.close();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText(null);
                alert.setContentText("You reservation is complete! A receipt has been generated");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("It is not possible to generate an invoice or the folder does not exist on the disk");
            alert.showAndWait();
        }
    }

    /**
     * Generating a chart with the number of bookings for a given day
     */
    public void chart() {
        home_Chart.getData().clear();
        String sql = "SELECT reservation_Date,COUNT(reservation_Id) FROM reservations GROUP BY reservation_Date ORDER BY TIMESTAMP(reservation_Date)";
        connect = DatabaseConnection.connectDB();
        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
                chart.setName("Reservation Date");
            }

            home_Chart.getData().add(chart);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method which validate user email address
     * @return true/false status indicating whether the email address meets the criteria
     */
    public boolean vaildationEmailReservation() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher match = pattern.matcher(reservation_Email.getText());
        if (match.find() && match.group().matches(reservation_Email.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validationPhoneNumber()
    {
        //Pattern pattern = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");
        Pattern pattern = Pattern.compile("^[1-9]\\d{0,9}$");
        Matcher match = pattern.matcher(reservation_PhoneNumber.getText());
        if(match.find() && match.group().matches(reservation_PhoneNumber.getText())) {
            return  true;
        }
        else {
            return false;
        }
    }


    /**
     * Method which initialize DashboardController
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        homeAS();
        homeTR();
        homeTP();
        chart();

        availableServiceShowList();
        availableServicesStatus();

        reservationShowList();
        reservationServiceName();
        reservationSpinner();
    }
}

