package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import netdata.DataObject;

class ClientNetwork implements Runnable
{
    private final byte[] SERVER_IP = {127, 0, 0, 1};
    private final int SERVER_PORT = 45612;

    UserController _activeController = null;

    private InetAddress _address;
    private Socket _server;
    private ObjectInputStream _socketInput;
    private ObjectOutputStream _socketOutput;

    private ArrayList<DataObject> _recvObjectBuf = new ArrayList<>();

    ClientNetwork()
    {
        try {
            _address = Inet4Address.getByAddress(SERVER_IP);

        } catch (UnknownHostException e) {
            System.err.println("Error: Unknown host. \n" + e);
        }
    }

    public void run()
    {
        ObservableList<DataObject> observedRecvObjects = FXCollections.observableList(_recvObjectBuf);

        // Received objects listener
        observedRecvObjects.addListener(
                (ListChangeListener.Change<? extends DataObject> changedObjects) ->
                {
                    List<DataObject> addedObjects = (List<DataObject>)changedObjects.getAddedSubList();
                    HandleData(addedObjects);
                }
        );

        try {
            _server = new Socket(_address, SERVER_PORT);
            _socketOutput = new ObjectOutputStream(_server.getOutputStream());
            _socketOutput.flush();
            _socketInput = new ObjectInputStream(_server.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true)
        {
            try {
                DataObject receivedObject = (DataObject)_socketInput.readObject();
                _recvObjectBuf.add(receivedObject);
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Received object's class was not found. \n");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Error: An I/O Exception occurred when receiving a DataObject.");
                e.printStackTrace();
            }
        }
    }

    public void SendMessage(DataObject message)
    {
        try {
            _socketOutput.writeObject(message);
            _socketOutput.flush();
            System.out.println("Message sent.");
        } catch (IOException e) {
            System.err.println("I/O Exception triggered during object write to socket output.");
        }

    }

    public void HandleData(List<DataObject> newData)
    {
        while (_activeController == null)
        {
            try {
                System.err.println("Warning: No active controller set on network. Data objects not handled.");
                Thread.sleep(500);
            } catch(InterruptedException e) {
                System.err.println("Error: Network Thread sleep interrupted.");
            }
        }

        for (DataObject data : newData)
        {
            DataObject.DataObjectType dataType = data.GetDataType();

            switch(dataType)
            {

            }
        }
    }

    public void SetActiveController(UserController controller)
    {
        _activeController = controller;
    }
}
