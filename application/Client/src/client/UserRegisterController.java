package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRegisterController extends UserController implements Initializable {

    @FXML TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML PasswordField confirmPasswordField;
    @FXML TextField emailField;

    @FXML Button registerButton;


    UserRegisterController(UserView formView, ClientNetwork con)
    {
        super(formView, con);
    }

    @Override
    public void initialize(URL fxmlSrc, ResourceBundle resources)
    {
        registerButton.setOnAction(e -> RegisterUser());
    }

    private void RegisterUser()
    {

    }
}
