package xml_generator;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@XmlRootElement
public class MyHash {


    private LinkedHashMap<String, LinkedList<String>> realMap;

    // constructor
    public MyHash() {
        realMap = new LinkedHashMap<>();
    }

    /**
     * @return LinkedHashMap<String , LinkedList < String>>
     */
    @XmlJavaTypeAdapter(MapAdapter.class)
    public LinkedHashMap<String, LinkedList<String>> getRealMap() {
        if (realMap == null) {
            realMap = new LinkedHashMap<>();
        }
        return realMap;
    }

    /**
     * @param key
     * @param value
     */
    public void put(String key, LinkedList<String> value) {
        realMap.put(key, value);
    }

}
