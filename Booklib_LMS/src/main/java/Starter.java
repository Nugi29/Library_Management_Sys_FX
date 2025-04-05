import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/loginform.fxml"))));
        stage.setTitle("Booklib LMS - Login Form");
        stage.setResizable(false);
        stage.show();

//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/dashboard.fxml"))));
//        stage.setTitle("Booklib LMS");
//        stage.setResizable(false);
//        stage.show();

    }
}
