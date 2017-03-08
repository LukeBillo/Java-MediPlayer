package server;

import com.mongodb.connection.Server;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import netdata.DataObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientConnection implements Runnable {
    private Socket _clientSocket = null;
    private ServerModel _dataModel = null;

    private ObjectInputStream _socketInput;
    private ObjectOutputStream _socketOutput;

    ObservableList<DataObject> _observedRecvObjects = FXCollections.observableList(new ArrayList<>());

    ClientConnection(Socket clientSock, ServerModel model)
    {
        _clientSocket = clientSock;
        _dataModel = model;
    }

    public void run()
    {
        // Received objects listener
        _observedRecvObjects.addListener(
                (ListChangeListener.Change<? extends DataObject> changedObjects) ->
                {
                    changedObjects.next();
                    List<DataObject> addedObjects = (List<DataObject>)changedObjects.getAddedSubList();
                    _dataModel.HandleData(addedObjects);
                }
        );

        try {
            _socketOutput = new ObjectOutputStream(_clientSocket.getOutputStream());
            _socketOutput.flush();
            _socketInput = new ObjectInputStream(_clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("I/O Exception occurred when retrieving server-client object stream.");
        }

        while (_clientSocket.isConnected())
        {
            try {
                DataObject receivedObject = (DataObject)_socketInput.readObject();
                _observedRecvObjects.add(receivedObject);
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Received object's class was not found. \n");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error: An I/O Exception occurred when receiving a DataObject.");
                e.printStackTrace();
            }

        }

    }

    public void SendMessage(DataObject message) {
        try {
            _socketOutput.writeObject(message);
            _socketOutput.flush();
        } catch (IOException e) {
            System.err.println("I/O Exception triggered during object write to socket output.");
        }

    }
}
