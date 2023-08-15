package org.exampleTwo;

import org.example.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> list = parseXML("data.xml");
        String json = listToJson(list);
        writeString(json, "data2.json");

    }

    private static List<Employee> parseXML(String filename) {
        List<Employee> employees = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filename));

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("employee");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    int id = Integer.parseInt(elem.getElementsByTagName("id").item(0).getTextContent());
                    String firstName = elem.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = elem.getElementsByTagName("lastName").item(0).getTextContent();
                    String country = elem.getElementsByTagName("country").item(0).getTextContent();
                    int age = Integer.parseInt(elem.getElementsByTagName("age").item(0).getTextContent());

                    employees.add(new Employee(id, firstName, lastName, country, age));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    public static String listToJson(List<Employee> employees) {
        JSONArray jsonArray = new JSONArray();

        for (Employee employee : employees) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", employee.id);
            jsonObject.put("firstName", employee.firstName);
            jsonObject.put("lastName", employee.lastName);
            jsonObject.put("country", employee.country);
            jsonObject.put("age", employee.age);

            jsonArray.add(jsonObject);
        }

        return jsonArray.toJSONString();
    }

    public static void writeString(String json, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(json);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






