package ro.ubb.exmp.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.exmp.domain.Employee;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeXmlRepository {

    private String fileName;

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    public EmployeeXmlRepository(String fileName) {
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

    public List<Employee> loadEmployeesFromXml() {

        List<Employee> employees = new ArrayList<>();

        Element root = document.getDocumentElement();

        NodeList personsNodes = root.getChildNodes();
        for (int i = 0; i < personsNodes.getLength(); i++) {

            Node entityNode = personsNodes.item(i);
            if (!(entityNode instanceof Element)) {
                continue;
            }

            Element currentElement = (Element) entityNode;
            Employee employee = buildEmployeeFromElement(currentElement);

            employees.add(employee);
        }

        return employees;

    }

    private Employee buildEmployeeFromElement(Element currentElement) {

        Employee employee = new Employee();

        String ssn = getTextFromTagName(currentElement, "ssn");
        employee.setSsn(ssn);

        int salary = Integer.parseInt(getTextFromTagName(currentElement, "salary"));
        employee.setSalary(salary);

        return employee;
    }

    private String getTextFromTagName(Element element, String tagName) {

        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

}
