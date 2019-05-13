package ws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import xml_generator.MyHash;
import xml_generator.XMLresponse;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@WebService(endpointInterface = "ws.WebData")
public class WebDataImpl implements WebData {
    private LinkedHashMap<String, LinkedList<String>> dataMap = new LinkedHashMap<>();
    private LinkedList<String> columnLeng = new LinkedList<>();
    private LinkedList<String> fieldName = new LinkedList<>();
    private LinkedList<String> dataType = new LinkedList<>();
    private LinkedList<String> repText = new LinkedList<>();
    private LinkedList<String> domName = new LinkedList<>();
    private LinkedList<String> outputLen = new LinkedList<>();
    private LinkedList<String> decimals = new LinkedList<>();
    private int quan;
    private MyHash webMapa = new MyHash();


    public WebDataImpl() {
        getResponse("", "", "", "", "", "", "");
    }

    // process of XML response
    private static Document loadXMLString(String XMLresponse) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(XMLresponse));
        return db.parse(is);
    }

    @Override
    public int getQuan() {
        return quan;
    }

    @Override
    public LinkedList<String> getColumnLeng() {
        return columnLeng;
    }

    @Override
    public LinkedList<String> getFieldName() {
        return fieldName;
    }

    public LinkedList<String> getDataType() {
        return dataType;
    }

    @Override
    public LinkedList<String> getRepText() {
        return repText;
    }

    @Override
    public LinkedList<String> getDomName() {
        return domName;
    }

    public LinkedList<String> getOutputLen() {
        return outputLen;
    }

    public LinkedList<String> getDecimals() {
        return decimals;
    }

    private void getResponse(String table, String fieldsQuan, String language, String where,
                             String order, String group, String fieldNames) {

        XMLresponse xmLresponse = new XMLresponse(table, fieldsQuan, language, where, order, group, fieldNames);
        String XMLresponse = xmLresponse.getXMLresponse();
        Document xmlDoc = null;
        try {
            xmlDoc = loadXMLString(XMLresponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NodeList nodeList = xmlDoc.getElementsByTagName("*");
        StringBuilder zdata;
        String zdataTemp;
        String value;
        boolean flag = false;
        boolean flagFE = false;
        int lengthTab = 0;

        // loop of all dataMap (tags) in the XML response
        for (int tag = 0; tag < nodeList.getLength(); tag++) {
            Element element = (Element) nodeList.item(tag);

            //condition: when tag equals "item" and it's closing tag then....
            if (element.getTagName().equals("item") && flag) {
                flag = false;
            }

            // condition: when tag equals "item" and it's opening tag then....
            if (flag) {
                if (element.getTagName().equals("WA") || element.getTagName().equals("ZDATA")) {
                    LinkedList<String> tempFieldName = fieldName;
                    LinkedList<String> tempColumnLeng = columnLeng;
                    if (!flagFE) {
                        flagFE = true;

                        for (String name : tempFieldName) {
                            dataMap.put(name, new LinkedList<String>());
                        }

                    }
                    // the line, which contains values must have the same length as sum of all chars of fileds in
                    // table.
                    try {
                        zdata = new StringBuilder(element.getFirstChild().getNodeValue());
                    } catch (NullPointerException e) {
                        zdata = new StringBuilder("");
                    }

                    if (zdata.length() < lengthTab) {
                        int dif = lengthTab - zdata.length();
                        for (int i = 0; i < dif; i++) {
                            zdata.append(" ");
                        }
                    }
                    for (String val : tempFieldName) {
                        zdataTemp = zdata.substring(0, Integer.parseInt(tempColumnLeng.get(tempFieldName.indexOf(val))));
                        zdata = new StringBuilder(zdata.substring(Integer.parseInt
                                (tempColumnLeng.get(tempFieldName.indexOf(val)))));
                        dataMap.get(val).add(zdataTemp);
                    }

                } else {
                    // a little treak in ABAP and JAVA with null values of attributes inside the xml response
                    value = element.getFirstChild().getNodeValue();
                    if (value.equals("!@#$%^&")) {
                        value = " ";
                    }

                    //add values of tag in tag lists

                    switch (element.getTagName()) {
                        case "FIELDNAME":
                            fieldName.addLast(value);
                            break;
                        case "DATATYPE":
                            dataType.add(value);
                            break;
                        case "REPTEXT":
                            repText.add(value);
                            break;
                        case "DOMNAME":
                            domName.add(value);
                            break;
                        case "LENG":
                            columnLeng.add(value);
                            // get sum of all fileds length in table
                            lengthTab += Integer.parseInt(value);
                            break;
                        case "OUTPUTLEN":
                            outputLen.add(value);
                            break;
                        case "DECIMALS":
                            decimals.add(value);
                            break;
                        default:
                            break;
                    }
                }
            }
            // set flag=true  (each iteration of output string splitted by tag "item") for splitting strings.
            // the are exist open tag and close tag. This one is open tag
            if (element.getTagName().equals("item")) {
                flag = true;
            }
        }
        for (String k : dataMap.keySet()) {
            dataMap.get(k).remove(0);
            quan = dataMap.get(k).size();
            webMapa.put(k, dataMap.get(k));
        }

        //return dataMap;

    }

    @Override
    public MyHash getWebMapa(String table, String fieldsQuan, String language, String where,
                             String order, String group, String fieldNames) {
        getResponse(table, fieldsQuan, language, where, order, group, fieldNames);
        return webMapa;
    }

    @Override
    public void clear() {
        dataMap = new LinkedHashMap<>();
        columnLeng = new LinkedList<>();
        fieldName = new LinkedList<>();
        dataType = new LinkedList<>();
        repText = new LinkedList<>();
        domName = new LinkedList<>();
        outputLen = new LinkedList<>();
        decimals = new LinkedList<>();
        webMapa = new MyHash();
    }
}
