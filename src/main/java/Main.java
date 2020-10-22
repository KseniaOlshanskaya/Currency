import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        if(args.length < 2) {
            System.out.println("Аргументы введены неверно. Повторите попытку.");
            System.out.println("Пример: 17/12/2019 USD");
            System.exit(1);
        }
        Manager manager = new Manager();

        String date = args[0];
        String charCode = args[1];

        String result = manager.start(date, charCode);
        System.out.println(result);
    }
}
