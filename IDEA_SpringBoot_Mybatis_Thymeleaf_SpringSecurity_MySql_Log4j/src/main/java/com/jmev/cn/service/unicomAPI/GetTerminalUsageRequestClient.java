package com.jmev.cn.service.unicomAPI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jmev.cn.util.UnicomAPIUtils;
import com.sun.xml.wss.XWSSecurityException;

@Service("getTerminalUsageRequestClient")
public class GetTerminalUsageRequestClient extends UnicomAPIUtils
{

    public static String totalDataVolume = "";

    public GetTerminalUsageRequestClient()
    {
    }

    public String GetTerminalUsageRequest(String iccid, String time) throws Exception
    {
        GetTerminalUsageRequestClient terminalUsageRequest = new GetTerminalUsageRequestClient(API_URL, LICENSE_KEY);
        terminalUsageRequest.callWebService(iccid, time);
        return totalDataVolume;
    }

    public GetTerminalUsageRequestClient(String url, String licenseKey)
            throws SOAPException, MalformedURLException, XWSSecurityException
    {
        super(UnicomAPIUtils.API_URL, UnicomAPIUtils.LICENSE_KEY);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SOAPMessage createTerminalRequest(Object... objects) throws SOAPException
    {
        SOAPMessage message = messageFactory.createMessage();
        message.getMimeHeaders().addHeader("SOAPAction",
                "http://api.jasperwireless.com/ws/service/billing/GetTerminalUsage");
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalRequestName = envelope.createName("GetTerminalUsageRequest", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalRequestElement = message.getSOAPBody().addBodyElement(terminalRequestName);
        Name msgId = envelope.createName("messageId", PREFIX, NAMESPACE_URI);
        SOAPElement msgElement = terminalRequestElement.addChildElement(msgId);
        msgElement.setValue("TCE-100-ABC-34084");
        Name version = envelope.createName("version", PREFIX, NAMESPACE_URI);
        SOAPElement versionElement = terminalRequestElement.addChildElement(version);
        versionElement.setValue("1.0");
        Name license = envelope.createName("licenseKey", PREFIX, NAMESPACE_URI);
        SOAPElement licenseElement = terminalRequestElement.addChildElement(license);
        licenseElement.setValue(licenseKey);
        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
        SOAPElement iccidElement = terminalRequestElement.addChildElement(iccidName);
        iccidElement.setValue(((List<String>) objects[0]).get(0));
        Name cycleStartDateName = envelope.createName("cycleStartDate", PREFIX, NAMESPACE_URI);
        SOAPElement cycleStartDate = terminalRequestElement.addChildElement(cycleStartDateName);
        cycleStartDate.setValue((String) objects[1]);
        return message;
    }

    @Override
    public void callWebService(Object... objects) throws SOAPException, IOException, XWSSecurityException, Exception
    {
        List<String> iccids = new ArrayList<>();
        iccids.add((String) objects[0]);
        //        @SuppressWarnings(
        //        { "unchecked" })
        //        List<String> iccids = (List<String>) objects[0];
        //        List<String> unicomIccids = new ArrayList<>();
        //        for (int i = 0; i < iccids.size(); i++)
        //        {
        //            if (iccids.get(i) != null && iccids.get(i).length() > 17 && "8986011".equals(iccids.get(i).substring(0, 7)))
        //            {
        //                unicomIccids.add(iccids.get(i));
        //            } else
        //            {
        //                notUnicom.add(i);
        //            }
        //        }
        SOAPMessage request = createTerminalRequest(iccids,objects[1]);
        request = super.secureMessage(request, UnicomAPIUtils.USERNAME, UnicomAPIUtils.PASSWORD);
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        if (!response.getSOAPBody().hasFault())
        {
            writeTerminalResponse(response);
        } else
        {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code :" + fault.getFaultCode());
            System.err.println("SOAP Fault String :" + fault.getFaultString());
        }
    }

    @Override
    public void writeTerminalResponse(SOAPMessage message) throws SOAPException
    {
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = envelope.createName("GetTerminalUsageResponse", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message.getSOAPBody()
                .getChildElements(terminalResponseName).next();
        NodeList list = terminalResponseElement.getChildNodes();
        Node n = null;
        for (int i = 0; i < list.getLength(); i++)
        {
            n = list.item(i);
            if ("totalDataVolume".equalsIgnoreCase(n.getLocalName()))
                totalDataVolume = n.getTextContent();
        }
    }

}

