import java.io.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class ConfigurationParameters {
    public static int GAME_DIFFICULTY;
    public static boolean OBSTACLES;
    public static int BLOCK_SIZE;    
    public static int PLAYGROUND_WIDTH;
    public static int PLAYGROUND_HEIGTH;
    public static Color PLAYGROUND_COLOR;
    public static Color OBSTACLE_COLOR;
    public static Color SNAKE_BORDER_COLOR;
    public static Color SNAKE_HEAD_COLOR;
    public static Color SNAKE_BODY_COLOR;    
    public static KeyCode KEY_UP = KeyCode.UP;
    public static KeyCode KEY_RIGHT = KeyCode.RIGHT;
    public static KeyCode KEY_DOWN = KeyCode.DOWN;
    public static KeyCode KEY_LEFT = KeyCode.LEFT;    
    public static int LEADERBOARD_SIZE;
    public static int LEADERBOARD_DAYS;
    public static String FONT;
    public static String FONT_SIZE;
    public static String CLIENT_IP;
    public static String SERVER_IP;
    public static int SERVER_PORT;
    public static String DB_IP;
    public static int DB_PORT;
    public static String DB_USER;
    public static String DB_PASSWORD;
    
    
    public static boolean validateConfigFileXML() {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File("./myfiles/config.xml"));
            Schema s = sf.newSchema(new StreamSource(new File("./myfiles/config.xsd")));
            s.newValidator().validate(new DOMSource(d));
        } catch (ParserConfigurationException | SAXException | IOException e) { 
            System.err.println(e.getMessage());  
            return false; 
        }
        return true;
    }
    
    public static void deserializeConfigFileXML() {
        try {            
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document d = db.parse(new File("./myfiles/config.xml"));
            getKeybindingParameters(d);
            getGameParameters(d);
            getStyleParameters(d);
            getTechnicalParameters(d);
            getLeaderboardParameters(d);
        } catch (ParserConfigurationException | SAXException | IOException e) { 
            System.err.println(e.getMessage()); 
        }
    }
    
    private static void getKeybindingParameters(Document d) {
        if (!d.getElementsByTagName("KEY_UP").item(0).getTextContent().equals(""))
            KEY_UP = KeyCode.getKeyCode(d.getElementsByTagName("KEY_UP").item(0).getTextContent());        
        if (!d.getElementsByTagName("KEY_RIGHT").item(0).getTextContent().equals(""))
            KEY_RIGHT = KeyCode.getKeyCode(d.getElementsByTagName("KEY_RIGHT").item(0).getTextContent());        
        if (!d.getElementsByTagName("KEY_DOWN").item(0).getTextContent().equals(""))
            KEY_DOWN = KeyCode.getKeyCode(d.getElementsByTagName("KEY_DOWN").item(0).getTextContent());        
        if (!d.getElementsByTagName("KEY_LEFT").item(0).getTextContent().equals(""))
            KEY_LEFT = KeyCode.getKeyCode(d.getElementsByTagName("KEY_LEFT").item(0).getTextContent());        
    }

    private static void getGameParameters(Document d) {
        GAME_DIFFICULTY = Integer.parseInt(d.getElementsByTagName("GAME_DIFFICULTY").item(0).getTextContent());
        OBSTACLES = "true".equals(d.getElementsByTagName("OBSTACLES").item(0).getTextContent());
        PLAYGROUND_WIDTH = Integer.parseInt(d.getElementsByTagName("PLAYGROUND_WIDTH").item(0).getTextContent());
        PLAYGROUND_HEIGTH = Integer.parseInt(d.getElementsByTagName("PLAYGROUND_HEIGTH").item(0).getTextContent());
    }
        
    private static void getStyleParameters(Document d) {
        BLOCK_SIZE = Integer.parseInt(d.getElementsByTagName("BLOCK_SIZE").item(0).getTextContent());
        PLAYGROUND_COLOR = Color.web(d.getElementsByTagName("PLAYGROUND_COLOR").item(0).getTextContent());
        OBSTACLE_COLOR = Color.web(d.getElementsByTagName("OBSTACLE_COLOR").item(0).getTextContent());
        SNAKE_BORDER_COLOR = Color.web(d.getElementsByTagName("SNAKE_BORDER_COLOR").item(0).getTextContent());
        SNAKE_HEAD_COLOR = Color.web(d.getElementsByTagName("SNAKE_HEAD_COLOR").item(0).getTextContent());
        SNAKE_BODY_COLOR = Color.web(d.getElementsByTagName("SNAKE_BODY_COLOR").item(0).getTextContent());        
        FONT = d.getElementsByTagName("FONT").item(0).getTextContent();
        FONT_SIZE = d.getElementsByTagName("FONT_SIZE").item(0).getTextContent() 
                    + d.getElementsByTagName("FONT_SIZE").item(0).getAttributes().getNamedItem("unit").getNodeValue();        
    }
    
    private static void getTechnicalParameters(Document d) {
        CLIENT_IP = d.getElementsByTagName("CLIENT_IP").item(0).getTextContent();
        SERVER_IP = d.getElementsByTagName("SERVER_IP").item(0).getTextContent();
        SERVER_PORT = Integer.parseInt(d.getElementsByTagName("SERVER_PORT").item(0).getTextContent());
        DB_IP = d.getElementsByTagName("DB_IP").item(0).getTextContent();
        DB_PORT = Integer.parseInt(d.getElementsByTagName("DB_PORT").item(0).getTextContent());
        DB_USER = d.getElementsByTagName("DB_USER").item(0).getTextContent();
        DB_PASSWORD = d.getElementsByTagName("DB_PASSWORD").item(0).getTextContent();
    }
    
    private static void getLeaderboardParameters(Document d) {
        LEADERBOARD_SIZE = Integer.parseInt(d.getElementsByTagName("LEADERBOARD_SIZE").item(0).getTextContent());
        LEADERBOARD_DAYS = Integer.parseInt(d.getElementsByTagName("LEADERBOARD_DAYS").item(0).getTextContent());        
    }
    
}
