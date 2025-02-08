package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {

    @FXML
    public Label lblTime;

    @FXML
    public Label lblDate;

    @FXML
    private Button btnAddNewAdmin;

    @FXML
    private Button btnBookForm;

    @FXML
    private Button btnBorrowForm;

    @FXML
    private Button btnFineForm;

    @FXML
    private Button btnReportForm;

    @FXML
    private Button btnUserForm;

    @FXML
    private AnchorPane dashbordSubAnchor;

    public void initialize() {
        setDateAndTime();
    }

    @FXML
    void btnAddNewAdminOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/RegNewAdmin.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        dashbordSubAnchor.getChildren().clear();
        dashbordSubAnchor.getChildren().add(load);

    }

    @FXML
    void btnBookOnAction(ActionEvent event) throws IOException {

        URL resource = this.getClass().getResource("/view/bookform.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        dashbordSubAnchor.getChildren().clear();
        dashbordSubAnchor.getChildren().add(load);

    }

    @FXML
    void btnBorrowOnActon(ActionEvent event) {

    }

    @FXML
    void btnFineOnActon(ActionEvent event) {

    }

    @FXML
    void btnReportOnActon(ActionEvent event) {

    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/memberform.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        dashbordSubAnchor.getChildren().clear();
        dashbordSubAnchor.getChildren().add(load);
    }

    private void setDateAndTime() {
        // Date Formatting
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        lblDate.setText(LocalDate.now().format(dateFormatter));

        // Time Formatting
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    lblTime.setText(now.format(timeFormatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}



