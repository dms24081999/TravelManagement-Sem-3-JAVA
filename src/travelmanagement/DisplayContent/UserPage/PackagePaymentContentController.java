/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelmanagement.DisplayContent.UserPage;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import travelmanagement.database.SqliteConnection;

/**
 *
 * @author dms
 */
public class PackagePaymentContentController implements Initializable {

    int packageid,userId;
    int totals;
    Connection connection;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    public PackagePaymentContentController() {
        connection = SqliteConnection.connector();
        if (connection == null) {
            System.exit(1);
        }
    }
    
    @FXML   
    private AnchorPane payment;

    @FXML
    private JFXTextField card;

    @FXML
    private JFXTextField cvv;

    @FXML
    private JFXTextField exp;

    @FXML
    private Label total;

    @FXML
    private Label isCard;

    @FXML
    private Label isCvv;

    @FXML
    private Label isExp;
    
    @FXML
    private RadioButton credit;

    @FXML
    private RadioButton debit;


    ToggleGroup paymentRadio = new ToggleGroup();
    
    Pattern cardPattern = Pattern.compile("^[0-9]{16}$");
    Pattern cvvPattern = Pattern.compile("^[0-9]{16}$");

    @FXML
    void Cancel(ActionEvent event) {
        payment.getChildren().clear();
        try {
            payment.getChildren().add(FXMLLoader.load(getClass().getResource("/travelmanagement/DisplayContent/UserPage/ViewPlacesContent.fxml")));
            System.out.println("loaded");
        } catch (IOException ex) {
            Logger.getLogger(UserPageContentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Pay(ActionEvent event) {
//update booked set paid="Yes" where packId=1 and userId=3;
        String query = "update booked set paid=\"Yes\" where packId=" + packageid + " and userId=" + userId + ";\n";
        System.out.println(query);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Done");
        } catch (SQLException e) {
            System.out.println("Failed");
            e.printStackTrace();
            
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        packageid=PackageBookingContentController.packageid;
        userId=PackageBookingContentController.userId;
        credit.setToggleGroup(paymentRadio);
        debit.setToggleGroup(paymentRadio);

        totals = PackageBookingContentController.total;
        total.setText(Integer.toString(totals));
        card.textProperty().addListener((observable, oldValue, newValue) -> {

            Matcher matcher = cardPattern.matcher(card.getText());
            if (!matcher.matches()) {
                isCard.setText("Invalid Username!");
            } else {
                isCard.setText("");

            }

        });
        cvv.textProperty().addListener((observable, oldValue, newValue) -> {

            Matcher matcher = cvvPattern.matcher(cvv.getText());
            if (!matcher.matches()) {
                isCvv.setText("Invalid Username!");
            } else {
                isCvv.setText("");

            }

        });

    }

}