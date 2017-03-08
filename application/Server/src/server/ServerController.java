package server;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import netdata.DataObject;

import java.util.ArrayList;
import java.util.List;

abstract class ServerController
{
    protected ServerView _userView = null;
    protected ServerNetwork _serverNetwork = null;

    ServerController(ServerView formView, ServerNetwork network)
    {
        _serverNetwork = network;
        _userView = formView;
    }

    protected void SwitchScene(ServerView.SceneIndex changeToSceneIndex)
    {
        _userView.DisplayScene(changeToSceneIndex);
    }


}
