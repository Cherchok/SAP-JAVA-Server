package main;

import ws.WebDataImpl;

import javax.xml.ws.Endpoint;

public class StartServer {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8082/ws/webdata", new WebDataImpl());
        System.out.println("Done");
    }
}
