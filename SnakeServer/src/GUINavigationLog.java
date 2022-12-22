import java.io.*;
import java.net.*;
import java.nio.file.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class GUINavigationLog {
    private static String xml;

    public static void main(String[] args) {
        while (true) {
            try (ServerSocket server = new ServerSocket(8080, 1);
                 Socket socket = server.accept(); 
                 DataInputStream din = new DataInputStream(socket.getInputStream());
            ) {
                xml = din.readUTF();
                validateNavEventXML();
                addNewLogLine();
            } catch (Exception e) { System.err.println(e.getMessage()); }  
        }
    }

    private static void validateNavEventXML() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Document d = db.parse(new ByteArrayInputStream(xml.getBytes()));
        Schema s = sf.newSchema(new StreamSource(new File("./myfiles/navEvent.xsd")));
        s.newValidator().validate(new DOMSource(d));
    }
    
    private static void addNewLogLine() throws IOException {
        try { Files.createFile(Paths.get("./myfiles/navLog.txt")); } catch (IOException e) { }
        xml += '\n';
        Files.write(Paths.get("./myfiles/navLog.txt"), xml.getBytes(), StandardOpenOption.APPEND);
        System.out.print(xml);
    }

}