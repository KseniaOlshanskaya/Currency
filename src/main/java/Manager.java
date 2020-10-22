import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Manager {
    private static ArrayList<Currency> listOfCurrencies = new ArrayList<>();
    private static File file = new File("C:/Users/123/IdeaProjects/Currency/currency.xml");
    public static String regex = "\\d{2}['/']\\d{2}['/']\\d{4}";

    public Manager() {
    }

    public static String start(String date, String charCod) throws ParserConfigurationException, IOException, SAXException {


        if(!Pattern.matches(regex, date)) {
            System.out.println("Вы неверно ввели дату. Повторите. Пример: 15/12/2018");
            System.exit(1);
        }
        HttpResponse<String> response = Unirest.
                get("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date + "").asString();

        String finalStr = response.getBody().
                replace("encoding=\"windows-1251\"", "encoding=\"UTF-8\"");


        try (FileWriter fw = new FileWriter(file)) {
            fw.write(finalStr);
        } catch (IOException exc) {
            exc.printStackTrace(System.out);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(file.toURI()));

        NodeList charCodeElements = document.getDocumentElement().getElementsByTagName("CharCode");
        NodeList nominalElements = document.getDocumentElement().getElementsByTagName("Nominal");
        NodeList nameElements = document.getDocumentElement().getElementsByTagName("Name");
        NodeList valueElements = document.getDocumentElement().getElementsByTagName("Value");

        for (int i = 0; i < charCodeElements.getLength(); i++) {

            Node charCode = charCodeElements.item(i);
            Node nominal = nominalElements.item(i);
            Node name = nameElements.item(i);
            Node value = valueElements.item(i);
            listOfCurrencies.add(new Currency(charCode.getTextContent(),
                    nominal.getTextContent(), name.getTextContent(), value.getTextContent()));
        }
        for (Currency cur : listOfCurrencies) {

            if (cur.getCharCode().equals(charCod)) {
                return cur.getNominal() + " " + cur.getName()
                        + " = " + cur.getValue() + " Российских рубля";
            }
        }
        return "Валюты с таким кодом не сущестует";
    }
}



