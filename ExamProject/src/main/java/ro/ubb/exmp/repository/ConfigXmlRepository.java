package ro.ubb.exmp.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ro.ubb.exmp.config.Configuration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ConfigXmlRepository {

    private static ConfigXmlRepository instance = null;

    private String fileName;

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    private ConfigXmlRepository(String fileName) {

        this.fileName = fileName;

        documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            document = documentBuilder.parse(fileName);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ConfigXmlRepository getInstance(String fileName) {
        if (instance == null) {
            instance = new ConfigXmlRepository(fileName);
        }
        return instance;
    }

    public Configuration loadConfigFromXML() {

        Element root = document.getDocumentElement();

        return  buildEntityFromElement(root);

    }

    private Configuration buildEntityFromElement(Element currentElement) {

        Configuration config = new Configuration();

        String url = getTextFromTagName(currentElement, "url");
        config.setUrl(url);

        String user = getTextFromTagName(currentElement, "user");
        config.setUser(user);

        String password = getTextFromTagName(currentElement, "password");
        config.setPassword(password);

        return config;
    }

    private String getTextFromTagName(Element element, String tagName) {

        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    public String getUrl() {

        Element root = document.getDocumentElement();

        return getTextFromTagName(root, "url");
    }

    public String getUser() {

        Element root = document.getDocumentElement();

        return getTextFromTagName(root, "user");
    }

    public String getPassword() {

        Element root = document.getDocumentElement();

        return getTextFromTagName(root, "password");
    }

}
