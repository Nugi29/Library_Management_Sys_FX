package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {


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
    private PieChart chart;

    @FXML
    private AnchorPane dashbordSubAnchor;

    @FXML
    private AnchorPane dashbordmainAnchor;

    @FXML
    private Label lblBookCount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblFine;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserCount;

    @FXML
    private Label weather;

    DashboardController dashboardController = new DashboardController();

    String w = WeatherService.getWeather();

    public void initialize() {
        setDateAndTime();
        setBookCount();
        setUserCount();
        setFineCount();
        setChart();
        weather.setText(w);
    }

    @FXML
    void logOutImgOnClick(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) dashbordmainAnchor.getScene().getWindow();
        currentStage.close();

        URL resource = this.getClass().getResource("/view/loginform.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Login Form");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void ImgClickOnClick(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/dashboard.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        dashbordmainAnchor.getChildren().clear();
        dashbordmainAnchor.getChildren().add(load);
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
    void btnBorrowOnActon(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/bookborrowloginform.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        dashbordSubAnchor.getChildren().clear();
        dashbordSubAnchor.getChildren().add(load);
    }

    @FXML
    void btnFineOnActon(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/fineform.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        dashbordSubAnchor.getChildren().clear();
        dashbordSubAnchor.getChildren().add(load);
    }

    @FXML
    void btnReportOnActon(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/reportform.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        dashbordSubAnchor.getChildren().clear();
        dashbordSubAnchor.getChildren().add(load);
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        lblDate.setText(LocalDate.now().format(dateFormatter));

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

    public void setBookCount() {
        lblBookCount.setText(dashboardController.getTotalBookCount());
    }
    public void setUserCount() {
        lblUserCount.setText(dashboardController.getTotalUserCount());
    }
    public void setFineCount() {
        lblFine.setText(dashboardController.getTotalFineCount());
    }
    public void setChart() {

        chart.getData().add(new PieChart.Data("Borrowed Books", dashboardController.getTotalBorrowedBooksCount()));
        chart.getData().add(new PieChart.Data("Returned Books", dashboardController.getTotalReturnedBooksCount()));

        chart.setTitle("Borrowed and Returned Books");
        chart.setLabelLineLength(10);
        chart.setLabelsVisible(true);
        chart.setLegendVisible(true);
        chart.setStartAngle(90);
        chart.setClockwise(true);
        chart.setAnimated(true);

    }


}
