package ws;

import xml_generator.MyHash;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.LinkedList;

@WebService
public interface WebData {

    @WebMethod
    public MyHash getWebMapa(String table, String fieldsQuan, String language, String where,
                             String order, String group, String fieldNames);

    @WebMethod
    public int getQuan();

    @WebMethod
    public LinkedList<String> getColumnLeng();

    @WebMethod
    public LinkedList<String> getFieldName();

    @WebMethod
    public LinkedList<String> getRepText();

    @WebMethod
    public LinkedList<String> getDataType();

    @WebMethod
    public LinkedList<String> getDomName();

    @WebMethod
    public LinkedList<String> getOutputLen();

    @WebMethod
    public LinkedList<String> getDecimals();

    @WebMethod
    public void clear();

}
