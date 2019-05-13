package xml_generator;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


public class MapAdapter extends XmlAdapter<MapElements[], Map<String, LinkedList<String>>> {
    public MapAdapter() {
    }

    public MapElements[] marshal(Map<String, LinkedList<String>> arg0) {
        MapElements[] mapElements = new MapElements[arg0.size()];
        int i = 0;
        for (Map.Entry<String, LinkedList<String>> entry : arg0.entrySet())
            mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());

        return mapElements;
    }

    public Map<String, LinkedList<String>> unmarshal(MapElements[] arg0) {
        Map<String, LinkedList<String>> r = new LinkedHashMap<>();
        for (MapElements mapelement : arg0)
            r.put(mapelement.key, mapelement.value);
        return r;
    }
}
