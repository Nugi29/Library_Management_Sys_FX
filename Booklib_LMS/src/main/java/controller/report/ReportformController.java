package controller.report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Member;
import model.reportModels.AvailableBooks;
import model.reportModels.BorrowedBooks;
import model.reportModels.OverdueBooksWithFines;

import java.util.List;

public class ReportformController {


    @FXML
    private TableView<AvailableBooks> abTblView;

    @FXML
    private TableColumn<?, ?> abcolaut;

    @FXML
    private TableColumn<?, ?> abcolcat;

    @FXML
    private TableColumn<?, ?> abcolid;

    @FXML
    private TableColumn<?, ?> abcolisbn;

    @FXML
    private TableColumn<?, ?> abcolname;

    @FXML
    private TableColumn<?, ?> abcolpub;

    @FXML
    private TableView<BorrowedBooks> bbTblView;

    @FXML
    private TableColumn<?, ?> bbcolbid;

    @FXML
    private TableColumn<?, ?> bbcolbname;

    @FXML
    private TableColumn<?, ?> bbcolbrodate;

    @FXML
    private TableColumn<?, ?> bbcolbroid;

    @FXML
    private TableColumn<?, ?> bbcolcon;

    @FXML
    private TableColumn<?, ?> bbcolmname;

    @FXML
    private TableColumn<?, ?> bbcolre;

    @FXML
    private Label lblAB;

    @FXML
    private Label lblOB;

    @FXML
    private Label lblBB;

    @FXML
    private TableView<OverdueBooksWithFines> obTblView;

    @FXML
    private TableColumn<?, ?> obcolbid;

    @FXML
    private TableColumn<?, ?> obcolbname;

    @FXML
    private TableColumn<?, ?> obcolbrodate;

    @FXML
    private TableColumn<?, ?> obcolbroid;

    @FXML
    private TableColumn<?, ?> obcolcon;

    @FXML
    private TableColumn<?, ?> obcolduedate;

    @FXML
    private TableColumn<?, ?> obcolfine;

    @FXML
    private TableColumn<?, ?> obcolmname;

    @FXML
    private TableColumn<?, ?> obcoloverdue;


    private ReportController reportController = new ReportController();

    @FXML
    public void initialize() {
        setTableDataToAB();
        setTableDataToBB();
        setTableDataToOB();
        setLbls();

    }

    public void setTableDataToAB() {
        ObservableList<AvailableBooks> availableBookList = FXCollections.observableArrayList(reportController.getAvailableBooksList());
        abcolid.setCellValueFactory(new PropertyValueFactory<>("id"));
        abcolname.setCellValueFactory(new PropertyValueFactory<>("name"));
        abcolisbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        abcolcat.setCellValueFactory(new PropertyValueFactory<>("category"));
        abcolaut.setCellValueFactory(new PropertyValueFactory<>("author"));
        abcolpub.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        abTblView.setItems(availableBookList);
    }

    public void setTableDataToBB() {
        ObservableList<BorrowedBooks> borrowedBookList = FXCollections.observableArrayList(reportController.getBorrowedBooksList());
        bbcolbroid.setCellValueFactory(new PropertyValueFactory<>("borrow_id"));
        bbcolbid.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        bbcolbname.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        bbcolmname.setCellValueFactory(new PropertyValueFactory<>("member_name"));
        bbcolcon.setCellValueFactory(new PropertyValueFactory<>("contact"));
        bbcolbrodate.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
        bbcolre.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        bbTblView.setItems(borrowedBookList);
    }

    public void setTableDataToOB() {
        ObservableList<OverdueBooksWithFines> overdueBookList = FXCollections.observableArrayList(reportController.getOverdueBooksListWithFines());
        obcolbroid.setCellValueFactory(new PropertyValueFactory<>("borrow_id"));
        obcolbid.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        obcolbname.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        obcolmname.setCellValueFactory(new PropertyValueFactory<>("member_name"));
        obcolcon.setCellValueFactory(new PropertyValueFactory<>("contact"));
        obcolbrodate.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
        obcolduedate.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        obcoloverdue.setCellValueFactory(new PropertyValueFactory<>("overdue_days"));
        obcolfine.setCellValueFactory(new PropertyValueFactory<>("fine_amount"));
        obTblView.setItems(overdueBookList);
    }

    public void setLbls() {
        lblAB.setText("All available book count is : " + String.valueOf(reportController.getAvailableBooksList().size()));
        lblBB.setText("All borrowed book count is : "+ String.valueOf(reportController.getBorrowedBooksList().size()));
        lblOB.setText("All overdue book count is : "+String.valueOf(reportController.getOverdueBooksListWithFines().size()));

    }




}
