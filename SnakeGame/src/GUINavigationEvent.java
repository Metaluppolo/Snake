import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;


public class GUINavigationEvent {
    private final String appName;
    private final String clientIP;
    private final String timestamp;
    private final String eventName;
    
    
    public GUINavigationEvent(String name) {
        Date now = new Date();
        appName = "Snake";
        clientIP = ConfigurationParameters.CLIENT_IP;
        timestamp = new SimpleDateFormat("yyyy-MM-dd").format(now) 
                    + "T" + new SimpleDateFormat("HH:mm:ss").format(now);
        eventName = name;
    }
    
    public void sendNavEvent() {
        try ( DataOutputStream dout = 
                new DataOutputStream(new Socket(ConfigurationParameters.SERVER_IP, ConfigurationParameters.SERVER_PORT).getOutputStream())
        ) { dout.writeUTF(serializeXML()); } catch (IOException e) { }        
    }
    
    private String serializeXML() {
        XStream xs = new XStream();
        xs.useAttributeFor(GUINavigationEvent.class, "appName");
        return xs.toXML(this);
    }
    
}
