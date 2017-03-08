package netdata;

import java.io.Serializable;
import java.util.ArrayList;

public class DataObject implements Serializable {
    public enum DataObjectType {
        LOGIN,
        MESSAGE,
        MODIFY,
        SONG,
        USER_CHECK,
        REGISTER
    }

    private ArrayList<String> data = new ArrayList<>();
    private DataObjectType dataType;

    public DataObject(ArrayList<String> dataItems, DataObjectType type)
    {
        data = dataItems;
        dataType = type;
    }

    public ArrayList<String> GetData() {  return data;  }

    public DataObjectType GetDataType()
    {
        return dataType;
    }
}
