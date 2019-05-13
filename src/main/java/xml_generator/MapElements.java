package xml_generator;

import javax.xml.bind.annotation.XmlElement;
import java.util.LinkedList;

class MapElements
{
    @XmlElement public String  key;
    @XmlElement public LinkedList<String> value;

    private MapElements() {} //Required by JAXB

    public MapElements(String key, LinkedList<String> value)
    {
        this.key   = key;
        this.value = value;
    }
}
