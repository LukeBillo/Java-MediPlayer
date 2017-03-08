package client;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import netdata.DataObject;
import netdata.DataObject.DataObjectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

class UserLoginController extends UserController implements Initializable
{
    @FXML private VBox loginBox;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    UserLoginController(UserView formView, ClientNetwork con)
    {
        super(formView, con);
    }

    @Override
    public void initialize(URL fxmlSrc, ResourceBundle resources)
    {
        loginButton.setOnAction(e-> UserLogin());
    }

    private void UserLogin()
    {
        Label errorLabel;
        String usernameText = usernameField.getText();
        String passwordText = passwordField.getText();

        ArrayList<String> loginDataList = new ArrayList<>(Arrays.asList(usernameText, passwordText));
        DataObject loginData = new DataObject(loginDataList, DataObjectType.LOGIN);
        _connection.SendMessage(loginData);


    }
}
