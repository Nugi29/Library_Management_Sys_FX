package controller.bookborrow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Member;

public class Bookborrowform {

    @FXML
    private Button btnExit;

    @FXML
    private TableColumn<?, ?> colbook;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private Label lblPoints;

    @FXML
    private Label rightPnlLblId;

    @FXML
    private Label rightPnlLblName;

    @FXML
    private Label rightPnlLblStatus;

    @FXML
    private Label rightPnlLblTp;

    @FXML
    private TableView<?> rightPnlTblView;

    public void initialize() {
        setRightpnl();
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {

    }
    private void setRightpnl(){
        Member m1 = bookborrowloginformController.memberar[0];
        rightPnlLblId.setText(m1.getId());
        rightPnlLblName.setText(m1.getName());
        rightPnlLblTp.setText(m1.getContact());
        rightPnlLblStatus.setText("Active");

    }

}
