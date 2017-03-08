package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserView extends Application
{
    /*
     *   Enum of scenes in scenePath array.
     *   Loaded in the same order into loadedScenes.
     *   Used to change scene.
     */
    enum SceneIndex
    {
        LOGIN(0),
        REGISTER(1),
        MAIN_INTERFACE(2);

        private int value;

        SceneIndex(int value)
        {
            this.value = value;
        }
    }

    private Stage mainStage = null;

    private String[] fxmlPaths =
            {
                    "user_login.fxml",
                    "user_register.fxml",
                    "user_interface.fxml"
            };

    // Empty list for Parents to be loaded into, and FXML paths to be loaded.
    private Scene[] loadedScenes = new Scene[fxmlPaths.length];
    private UserController[] sceneControllers = new UserController[fxmlPaths.length];

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        mainStage = primaryStage;
        LoadScenes();

        DisplayScene(SceneIndex.LOGIN);
        mainStage.show();
    }

    void DisplayScene(SceneIndex sceneIndex)
    {
        switch(sceneIndex)
        {
            case LOGIN:
                mainStage.setTitle("MediPlayer: User Login");
                break;
            case REGISTER:
                mainStage.setTitle("MediPlayer: Register");
            case MAIN_INTERFACE:
                mainStage.setTitle("MediPlayer: User Interface");
                break;
            default:
                mainStage.setTitle("MediPlayer");
                break;
        }

        mainStage.setScene(loadedScenes[sceneIndex.value]);
        sceneControllers[sceneIndex.value].SetActive();
    }

    private void LoadScenes()
    {
        ClientNetwork clientConnection = new ClientNetwork();
        new Thread(clientConnection).start();

        for (SceneIndex index : SceneIndex.values())
        {
            String scenePath = fxmlPaths[index.value];
            FXMLLoader loaderObject = new FXMLLoader(getClass().getResource(scenePath));

            switch(index)
            {
                case LOGIN:
                    UserLoginController loginController = new UserLoginController(this, clientConnection);
                    loaderObject.setController(loginController);
                    sceneControllers[index.value] = loginController;
                    break;
                case REGISTER:
                    UserRegisterController registerController = new UserRegisterController(this, clientConnection);
                    loaderObject.setController(registerController);
                    sceneControllers[index.value] = registerController;
                    break;
                case MAIN_INTERFACE:
                    UserInterfaceController interfaceController = new UserInterfaceController(this, clientConnection);
                    loaderObject.setController(interfaceController);
                    sceneControllers[index.value] = interfaceController;
                    break;
            }

            try {
                Parent loadedRoot = loaderObject.load();
                loadedRoot.getStylesheets().add(getClass().getResource("../style/main.css").toExternalForm());
                loadedScenes[index.value] = new Scene(loadedRoot);
            } catch (IOException e) {
                System.err.println("Error: Loader did not find JavaFX scene.");
                e.printStackTrace();
            }
        }
    }
}
