package Connection.Client;

import java.io.Serializable;
import java.util.HashMap;

public class ClientPayLoad implements Serializable {

    HashMap<String,String> stringStringHashMap = new HashMap<>();
    public HashMap<String, String> getStringStringHashMap() {
        return stringStringHashMap;
    }
    public void setStringStringHashMap(HashMap<String, String> stringMap) {
        this.stringStringHashMap = stringMap;
    }

}
