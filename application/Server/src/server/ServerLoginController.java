package server;

import java.net.URL;

import com.mongodb.connection.Server;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

class ServerLoginController extends ServerController implements Initializable
{
    @FXML
    private VBox loginContainer;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    ServerLoginController(ServerView formView, ServerNetwork network)
    {
        super(formView, network);
    }

    @Override
    public void initialize(URL fxmlSrc, ResourceBundle resources)
    {
        loginButton.setOnAction(e-> UserLogin(e));
    }

    private void UserLogin(ActionEvent event)
    {
        Label errorLabel;

        if (usernameField.getText().toLowerCase().equals("admin"))
        {
            if (passwordField.getText().equals("myPassword"))
            {
                _userView.DisplayScene(ServerView.SceneIndex.ADMIN_INTERFACE);
            }
            else
            {
                errorLabel = new Label("The password was incorrect.");
                errorLabel.getStyleClass().add("errorLabel");
                loginContainer.getChildren().add(errorLabel);
            }
        }
        else
        {
            errorLabel = new Label("The username was not recognised.");
            errorLabel.getStyleClass().add("errorLabel");
            loginContainer.getChildren().add(errorLabel);
        }
    }
}
