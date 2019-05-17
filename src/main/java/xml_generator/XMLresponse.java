package xml_generator;

import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class XMLresponse {
    private String tableName;
    private String fieldsQuantity;
    private String language;
    private String condition;
    private String orderBy;
    private String groupBy;
    private String fieldsName;

    public XMLresponse(String table, String fieldsQuan, String language, String where,
                       String order, String group, String fieldNames) {
        this.tableName = table;
        this.fieldsQuantity = fieldsQuan;
        this.language = language;
        this.condition = where;
        this.orderBy = order;
        this.groupBy = group;
        this.fieldsName = fieldNames;
    }

    public String getXMLresponse() {
        String xmlResponse = null;
        //       String username = "K.GACHECHILA";
        //       String password = "Welcome1!";
        String urnName = "urn";
        String urn = "ZTABLEREAD";
        String uri = "urn:sap-com:document:sap:rfc:functions";
        String destination = "http://support.alpeconsulting.com:8201/sap/bc/srt/rfc/sap/ztablereadws/100/" +
                "ztablereadws/ztablereadws";
        // create XML request , get connection, get XML response as a string/
        try {
            // First create the connection
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = soapConnFactory.createConnection();

            // Next, create the actual message
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();

            // authorization params
//            String authorization = new sun.misc.BASE64Encoder().encode((username + ":" + password).getBytes());
//            MimeHeaders hd = message.getMimeHeaders();
//            hd.addHeader("Authorization", "Basic " + authorization);



            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();

            envelope.addNamespaceDeclaration(urnName, uri);

            // Create and populate the body
            SOAPBody body = envelope.getBody();

            // enter params for connection
            SOAPElement bodyElement = body.addChildElement(envelope.createName("urn:" + urn));

            // Add parameters
            bodyElement.addChildElement("FIELDNAMES").addTextNode(fieldsName);
            bodyElement.addChildElement("GROUP").addTextNode(groupBy);
            bodyElement.addChildElement("ORDER").addTextNode(orderBy);
            bodyElement.addChildElement("WHERE").addTextNode(condition);
            bodyElement.addChildElement("LANG").addTextNode(language);
            bodyElement.addChildElement("FIELDSQUAN").addTextNode(fieldsQuantity);
            bodyElement.addChildElement("TABLENAME").addTextNode(tableName);


            // Save the message
            message.saveChanges();

          //  message.writeTo(System.out);

            // Send the message and get the reply
            SOAPMessage reply = connection.call(message, destination);

            //get response as a string
            final StringWriter sw = new StringWriter();
            try {
                TransformerFactory.newInstance().newTransformer().transform(
                        new DOMSource(reply.getSOAPPart()),
                        new StreamResult(sw));
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
            xmlResponse = sw.toString();

            // Close the connection
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return xmlResponse;
    }
}