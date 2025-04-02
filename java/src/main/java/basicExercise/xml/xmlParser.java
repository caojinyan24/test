package basicExercise.xml;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;

public class xmlParser {
//    private xmlBody parseXml(String args) {
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(xmlBody.class);
//            SAXParserFactory spf = SAXParserFactory.newInstance();
//            XMLReader xmlReader = spf.newSAXParser().getXMLReader();
//            SAXSource source = new SAXSource(xmlReader, new InputSource(new StringReader(args)));
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            return (xmlBody) jaxbUnmarshaller.unmarshal(source);
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//    }

//    public static void main(String[] args) {
//        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<!DOCTYPE paymentService PUBLIC \"-//WorldPay//DTD WorldPay PaymentService v1//EN\" \"http://dtd.worldpay.com/paymentService_v1.dtd\">\n" +
//                "<paymentService version=\"1.4\" merchantCode=\"BYTEMODOLUSD\">\n" +
//                "    <notify>\n" +
//                "        <orderStatusEvent orderCode=\"1558536470120911vioy\">\n" +
//                "        </orderStatusEvent>\n" +
//                "    </notify>\n" +
//                "</paymentService>";
//        System.out.println(new xmlParser().parseXml(xmlStr));
//    }
}
