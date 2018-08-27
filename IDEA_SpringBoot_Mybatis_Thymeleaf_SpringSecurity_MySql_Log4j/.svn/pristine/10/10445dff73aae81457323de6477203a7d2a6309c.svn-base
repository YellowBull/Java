package com.jmev.cn.service.unicomAPI;


import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jmev.cn.util.UnicomAPIUtils;
import com.sun.xml.wss.XWSSecurityException;

@Service("getTerminalDetailsClients")
public class GetTerminalDetailsClients extends UnicomAPIUtils
{
    public List<Integer> notUnicom = new ArrayList<>();

    public GetTerminalDetailsClients()
    {
    }

    public static Map<String, LinkedList<String>> result = new HashMap<>();

    public Map<String, LinkedList<String>> GetTerminalDetails(List<String> iccids) throws Exception
    {

        GetTerminalDetailsClients terminalClient = new GetTerminalDetailsClients(API_URL, LICENSE_KEY);
        terminalClient.callWebService(iccids);
        return result;
    }

    public GetTerminalDetailsClients(String url, String licenseKey)
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
                "http://api.jasperwireless.com/ws/service/terminal/GetTerminalDetails"); // 添加头部信息
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();// 添加封装节点
        Name terminalRequestName = envelope.createName("GetTerminalDetailsRequest", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalRequestElement = message.getSOAPBody().addBodyElement(terminalRequestName); // 添加内部节点信息
        Name msgId = envelope.createName("messageId", PREFIX, NAMESPACE_URI);
        SOAPElement msgElement = terminalRequestElement.addChildElement(msgId);
        msgElement.setValue("TCE-100-ABC-34084");
        Name version = envelope.createName("version", PREFIX, NAMESPACE_URI);
        SOAPElement versionElement = terminalRequestElement.addChildElement(version);
        versionElement.setValue("1.0");
        Name license = envelope.createName("licenseKey", PREFIX, NAMESPACE_URI);
        SOAPElement licenseElement = terminalRequestElement.addChildElement(license);
        licenseElement.setValue(licenseKey);

        Name iccids = envelope.createName("iccids", PREFIX, NAMESPACE_URI);
        SOAPElement iccidsElement = terminalRequestElement.addChildElement(iccids);
        List<String> allIccids = (List<String>) objects[0];
        for (String iccid : allIccids)
        {
            Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
            SOAPElement iccidElement = iccidsElement.addChildElement(iccidName);
            iccidElement.setValue(iccid);
        }
        return message;
    }

    @Override
    public void callWebService(Object... objects) throws SOAPException, IOException, XWSSecurityException, Exception
    {
        @SuppressWarnings(
        { "unchecked" })
        List<String> iccids = (List<String>) objects[0];
        List<String> unicomIccids = new ArrayList<>();
        for (int i = 0; i < iccids.size(); i++)
        {
            if (iccids.get(i) != null && iccids.get(i).length() > 17 && "8986011".equals(iccids.get(i).substring(0, 7)))
            {
                unicomIccids.add(iccids.get(i));
            } else
            {
                notUnicom.add(i);
            }
        }
        SOAPMessage request = createTerminalRequest(unicomIccids);
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

    @SuppressWarnings("deprecation")
    @Override
    public void writeTerminalResponse(SOAPMessage message) throws SOAPException
    {
        List<String> dateActivated = new ArrayList<>();
        List<String> status = new ArrayList<>();
        List<String> usage = new ArrayList<>();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = envelope.createName("GetTerminalDetailsResponse", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message.getSOAPBody()
                .getChildElements(terminalResponseName).next();
        Name terminals = envelope.createName("terminals", PREFIX, NAMESPACE_URI);
        Name terminal = envelope.createName("terminal", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalsElement = (SOAPBodyElement) terminalResponseElement.getChildElements(terminals).next();

        @SuppressWarnings("rawtypes")
        Iterator childElements2 = terminalsElement.getChildElements(terminal);
        while (childElements2.hasNext())
        {
            SOAPBodyElement terminalElement = (SOAPBodyElement) childElements2.next();
            NodeList list = terminalElement.getChildNodes();
            Node n = null;
            String activated = "";
            for (int i = 0; i < list.getLength(); i++)
            {
                n = list.item(i);
                if ("dateActivated".equalsIgnoreCase(n.getLocalName()))
                {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                    try
                    {
                        activated = n.getTextContent();
                        Date d = format.parse(n.getTextContent().replace("Z", " UTC"));
                        dateActivated.add(d.toLocaleString());
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    } //注意是空格+UTC

                }
                if ("monthToDateDataUsage".equals(n.getLocalName()))
                {
                    usage.add(n.getTextContent());
                }
                if ("status".equalsIgnoreCase(n.getLocalName()))
                {
                    status.add(n.getTextContent());
                }
            }
            if (StringUtils.isBlank(activated))// 未激活
            {
                dateActivated.add("");
                activated = "";
            }
        }
        LinkedList<String> dateActivatedLink = new LinkedList<>();
        LinkedList<String> dateStatusLink = new LinkedList<>();
        LinkedList<String> usageLink = new LinkedList<>();
        for (int i = 0; i < status.size(); i++)
        {
            dateStatusLink.add(status.get(i));
            dateActivatedLink.add(dateActivated.get(i));
            usageLink.add(usage.get(i));
        }
        int temp = 0;
        for (int j = 0; j < notUnicom.size(); j++)
        {
            dateActivatedLink.add(notUnicom.get(j) + temp, "");
            dateStatusLink.add(notUnicom.get(j) + temp, "");
            usageLink.add(notUnicom.get(j) + temp, "");
            temp++;
        }
        result.put("dateActivated", dateActivatedLink);
        result.put("status", dateStatusLink);
        result.put("useage", usageLink);
    }

}

