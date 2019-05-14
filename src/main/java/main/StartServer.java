package main;

import connection.ResourceManager;
import ws.WebDataImpl;

import javax.xml.ws.Endpoint;

public class StartServer {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String address;
        try {
            ResourceManager rm = new ResourceManager();
            for (String k : rm.getRb().keySet()) {
                sb.append(rm.getValues(k));
            }
            address = String.valueOf(sb);
        } catch (ExceptionInInitializerError exception) {
            address = "http://localhost:8082/ws/webdata";
            System.out.println(" 'setup.properties' file missing. Default URL was loaded!!!");
        }

        try {
            Endpoint.publish(address, new WebDataImpl());
        } catch (Exception e) {
            Endpoint.publish("http://localhost:8082/ws/webdata", new WebDataImpl());
            System.out.println("Wrong URL. Default URL was loaded!!!");
        }
        System.out.println("Done");
    }
}
