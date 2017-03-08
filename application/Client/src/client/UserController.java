package client;

abstract class UserController
{
    protected UserView _userView = null;
    protected ClientNetwork _connection = null;

    UserController(UserView formView, ClientNetwork con)
    {
        _connection = con;
        _userView = formView;
    }

    protected void SwitchScene(UserView.SceneIndex changeToSceneIndex)
    {
        _userView.DisplayScene(changeToSceneIndex);
    }

    public void SetActive()
    {
        _connection.SetActiveController(this);
    }

    protected abstract void HandleData();
}
