package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;

import java.net.URL;
import java.util.ResourceBundle;

class UserInterfaceController extends UserController implements Initializable
{
    private enum InterfaceSubScene
    {
        PROFILE,
        SOCIAL;
    }

    private Parent currentSubScene;

    @FXML private ToolBar toolbar;
    @FXML private SplitPane socialScene;
    @FXML private Accordion profileScene;
    @FXML private Button profileButton;
    @FXML private Button chatButton;

    // Couldn't make this work before; array would erase need for
    // A lot of manual coding. (Scene switching / setManaged + setVisible)
    // private Parent[] subScenes = { profileScene, socialScene };

    UserInterfaceController(UserView formView, ClientNetwork con)
    {
        super(formView, con);
    }

    @Override
    public void initialize(URL fxmlSrc, ResourceBundle resources)
    {
        socialScene.setManaged(false);
        socialScene.setVisible(false);

        currentSubScene = profileScene;

        profileButton.setOnAction(e->SwapInterfaceSubScene(InterfaceSubScene.PROFILE));
        chatButton.setOnAction(e->SwapInterfaceSubScene(InterfaceSubScene.SOCIAL));
    }

    private void SwapInterfaceSubScene(InterfaceSubScene subScene)
    {
        currentSubScene.setManaged(false);
        currentSubScene.setVisible(false);

        switch(subScene)
        {
            case PROFILE:
                currentSubScene = profileScene;
                break;
            case SOCIAL:
                currentSubScene = socialScene;
                break;
            default:
                break;
        }

        currentSubScene.setManaged(true);
        currentSubScene.setVisible(true);
    }
}
