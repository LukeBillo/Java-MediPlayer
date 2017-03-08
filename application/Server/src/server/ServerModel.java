package server;

import com.mongodb.*;
import com.mongodb.client.*;
import netdata.DataObject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

class ServerModel
{
    public enum SearchType {
        USER,
        PROFILE,
        CHAT,
        SONG
    }

    private ServerController _controller;

    private final String user = "root";
    private final String authDB = "admin";
    private final String password = "JustAPassword";
    private final String host = "localhost";

    private MongoDatabase mainDB = null;

    ServerModel()
    {
        String stringURI = "mongodb://" + user + ":" + password + "@" + host + "/?authSource=" + authDB;
        MongoClientURI mongoURI = new MongoClientURI(stringURI);

        MongoClient dbClient = new MongoClient(mongoURI);
        MongoDatabase mainDB = dbClient.getDatabase("MediPlayer");
    }

    public static void HandleData(List<DataObject> newData)
    {
        for (DataObject data : newData)
        {
            DataObject.DataObjectType dataType = data.GetDataType();

            switch(dataType)
            {
                case LOGIN:
                    HandleLogin(data);
                    break;
                case MESSAGE:
                    break;
                case MODIFY:
                    break;
                case REGISTER:
                    break;
                case USER_CHECK:
                    VerifyUser(data);
                    break;
                default:
                    System.err.println("Warning: An unknown data object was received.");
                    break;
            }
        }
    }

    private static void HandleLogin(DataObject loginData)
    {
        for (String dataString : loginData.GetData())
        {
            System.out.println(dataString);
        }
    }

    private static void HandleMessage()
    {

    }

    private static void HandleModify()
    {

    }

    private void VerifyUser(DataObject userData) {
        ArrayList<String> data = userData.GetData();
        String username = data.get(0);
        String password = data.get(1);
    }
}
