package connection;

import java.util.ResourceBundle;

public class ResourceManager {

    private final static ResourceManager instance = new ResourceManager();

    private static ResourceBundle rb = ResourceBundle.getBundle(
            "properties/setup");

    public ResourceManager getInstance() {
        return instance;
    }

    public String getValues(String key) {
        return rb.getString(key);
    }

    public ResourceBundle getRb() {
        return rb;
    }
}
