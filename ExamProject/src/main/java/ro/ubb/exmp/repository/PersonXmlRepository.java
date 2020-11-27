package ro.ubb.exmp.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.exmp.domain.Address;
import ro.ubb.exmp.domain.AddressType;
import ro.ubb.exmp.domain.Person;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonXmlRepository {

    private String fileName;

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    public PersonXmlRepository(String fileName) {
        this.fileName = fileName;

        documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            document = documentBuilder.parse(fileName);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> loadPersonsFromXml() {

        List<Person> persons = new ArrayList<>();

        Element root = document.getDocumentElement();

        NodeList personsNodes = root.getChildNodes();
        for (int i = 0; i < personsNodes.getLength(); i++) {

            Node entityNode = personsNodes.item(i);
            if (!(entityNode instanceof Element)) {
                continue;
            }

            Element currentElement = (Element) entityNode;
            Person person = buildPersonFromElement(currentElement);

            List<Address> personAddresses = new ArrayList<>();

            NodeList addressesNodes = currentElement.getElementsByTagName("address");

            for (int j = 0; j < addressesNodes.getLength(); j++) {

                Node node = addressesNodes.item(j);

                if (!(node instanceof Element)) {
                    continue;
                }

                Element currentElement1 = (Element) node;

                Address address = buildAddressFromElement(currentElement1);

                personAddresses.add(address);

                person.setAddresses(personAddresses);
            }

            persons.add(person);
        }

        return persons;
    }

    private Person buildPersonFromElement(Element currentElement) {

        Person person = new Person();

        String ssn = getTextFromTagName(currentElement, "ssn");
        person.setSsn(ssn);

        String name = getTextFromTagName(currentElement, "name");
        person.setName(name);

        return person;
    }

    private String getTextFromTagName(Element element, String tagName) {

        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private Address buildAddressFromElement(Element currentElement1) {

        Address address = new Address();

        String type = currentElement1.getAttribute("type");
        address.setType(AddressType.valueOf(type.toUpperCase()));

        String street = getTextFromTagName(currentElement1, "street");
        address.setStreet(street);

        String phone = getTextFromTagName(currentElement1, "phone");
        address.setPhone(phone);

        return address;
    }
}
