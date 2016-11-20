package converter;

import messages.ActiveMQMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Created by nicob on 09.11.2016.
 * convert xml string to erp class
 */

public class XmlConverter {
    private static XmlConverter instance = new XmlConverter();

    public static XmlConverter getInstance() {
        return instance;
    }

    private XmlConverter() {
    }

    /**
     * convert a xml string to a document
     */
    public static Document loadXmlFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(xml));
        return builder.parse(inputSource);
    }

    /**
     * creates a activemq message from a xml-string
     */
    public ActiveMQMessage getActiveMqMessage(String xml){
        ActiveMQMessage activeMQMessage = new ActiveMQMessage();

        try {
            Document document = loadXmlFromString(xml);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("erpData");
            Node node = nList.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                //set attributes for the message
                activeMQMessage.setCustomerNumber(Integer.parseInt(element.getElementsByTagName("customerNumber").item(0).getTextContent()));
                activeMQMessage.setMaterialNumber(Integer.parseInt(element.getElementsByTagName("materialNumber").item(0).getTextContent()));
                activeMQMessage.setOrderNumber(element.getElementsByTagName("orderNumber").item(0).getTextContent());
                activeMQMessage.setTimeStamp(DateConverter.getInstance().getDateFromString(element.getElementsByTagName("timeStamp").item(0).getTextContent()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return activeMQMessage;
    }
}