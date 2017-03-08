package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerView extends Application
{
    /*
     *   Enum of scenes in scenePath array.
     *   Loaded in the same order into loadedScenes.
     *   Used to change scene.
     */
    enum SceneIndex
    {
        LOGIN(0),
        ADMIN_INTERFACE(1);

        private int value;

        SceneIndex(int value)
        {
            this.value = value;
        }
    }

    private Stage mainStage = null;

    private String[] fxmlPaths =
            {
                    "admin_login.fxml",
                    "admin_interface.fxml"
            };

    // Empty list for Parents to be loaded into, and FXML paths to be loaded.
    private Scene[] loadedScenes = new Scene[fxmlPaths.length];

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
                mainStage.setTitle("MediPlayer: Admin Login");
                break;
            case ADMIN_INTERFACE:
                mainStage.setTitle("MediPlayer: Admin Interface");
                break;
            default:
                mainStage.setTitle("MediPlayer");
                break;
        }

        mainStage.setScene(loadedScenes[sceneIndex.value]);
    }

    private void LoadScenes()
    {
        ServerNetwork serverNetwork = new ServerNetwork();
        new Thread(serverNetwork).start();

        for (SceneIndex index : SceneIndex.values())
        {
            String scenePath = fxmlPaths[index.value];
            FXMLLoader loaderObject = new FXMLLoader(getClass().getResource(scenePath));

            switch(index)
            {
                case LOGIN:
                    ServerLoginController loginController = new ServerLoginController(this, serverNetwork);
                    loaderObject.setController(loginController);
                    break;
                case ADMIN_INTERFACE:
                    ServerInterfaceController interfaceController = new ServerInterfaceController(this, serverNetwork);
                    loaderObject.setController(interfaceController);
                    break;
            }

            try {
                Parent loadedRoot = loaderObject.load();
                loadedRoot.getStylesheets().add(getClass().getResource("../style/main.css").toExternalForm());
                loadedScenes[index.value] = new Scene(loadedRoot);
            } catch (IOException e) {
                System.err.println("Error: Loader did not find JavaFX scene.");
            }
        }
    }

    public Stage getMainStage()
    {
        return mainStage;
    }
}
