package com.jmev.cn.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;

/**
 * 联通API通用解析工具
 * @author 邵欣
 */
public abstract class AbstractUnicomAPIUtils implements ApiClientConstant
{

    public static String API_URL = "https://api.10646.cn/ws/service";
    public static String LICENSE_KEY = "c1df557d-e3df-4397-bc3e-3a160ff31e28";
    public static String USERNAME = "chenliwei";
    public static String PASSWORD = "jmev666666";

    public SOAPConnectionFactory connectionFactory;
    public MessageFactory messageFactory;
    public URL url;
    public String licenseKey;
    private XWSSProcessorFactory processorFactory;

    public AbstractUnicomAPIUtils()
    {
    }

    public static void main(String[] args) throws Exception
    {
        String argArr[] = { LICENSE_KEY, USERNAME, PASSWORD, "89860117770003376307" };// iccid
        if (argArr.length != 4)
        {
            System.out.println("usage: GetTerminalDetailsClient <license-key> <username> <password> <iccid>");
            System.exit(-1);
        }
    }

    // ProcessorFactory 实例化参数
    public AbstractUnicomAPIUtils(String url, String licenseKey)
            throws SOAPException, MalformedURLException, XWSSecurityException
    {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        processorFactory = XWSSProcessorFactory.newInstance();
        this.url = new URL(url);
        this.licenseKey = licenseKey;
    }

    // 封装SOAP请求信息
    public abstract SOAPMessage createTerminalRequest(Object... objects) throws SOAPException;

    //    private SOAPMessage createTerminalRequest(String iccid) throws SOAPException {
    //        SOAPMessage message = messageFactory.createMessage();
    //        message.getMimeHeaders().addHeader("SOAPAction",
    //                "http://api.jasperwireless.com/ws/service/terminal/GetTerminalDetails"); // 添加头部信息
    //        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();// 添加封装节点
    //        Name terminalRequestName = envelope.createName("GetTerminalDetailsRequest", PREFIX, NAMESPACE_URI);
    //        SOAPBodyElement terminalRequestElement = message.getSOAPBody().addBodyElement(terminalRequestName); // 添加内部节点信息
    //        Name msgId = envelope.createName("messageId", PREFIX, NAMESPACE_URI);
    //        SOAPElement msgElement = terminalRequestElement.addChildElement(msgId);
    //        msgElement.setValue("TCE-100-ABC-34084");
    //        Name version = envelope.createName("version", PREFIX, NAMESPACE_URI);
    //        SOAPElement versionElement = terminalRequestElement.addChildElement(version);
    //        versionElement.setValue("1.0");
    //        Name license = envelope.createName("licenseKey", PREFIX, NAMESPACE_URI);
    //        SOAPElement licenseElement = terminalRequestElement.addChildElement(license);
    //        licenseElement.setValue(licenseKey);
    //
    //
    //        Name iccids = envelope.createName("iccids", PREFIX, NAMESPACE_URI);
    //        SOAPElement iccidsElement = terminalRequestElement.addChildElement(iccids);
    //        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
    //        SOAPElement iccidElement = iccidsElement.addChildElement(iccidName);
    //        iccidElement.setValue(iccid);
    //        Name iccidName2 = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
    //        SOAPElement iccidElement2 = iccidsElement.addChildElement(iccidName2);
    //        iccidElement2.setValue("89860117770003376315");
    //        return message;
    //    }
    public abstract void callWebService(Object... objects)
            throws SOAPException, IOException, XWSSecurityException, Exception;
    //    public void callWebService(String username, String password, String iccid)
    //            throws SOAPException, IOException, XWSSecurityException, Exception
    //    {
    //        SOAPMessage request = createTerminalRequest(iccid);
    //        request = secureMessage(request, username, password);
    //        SOAPConnection connection = connectionFactory.createConnection();
    //        SOAPMessage response = connection.call(request, url);
    //        System.out.println("Response: ");
    //        // response.writeTo(System.out);
    //        if (!response.getSOAPBody().hasFault())
    //        {
    //            writeTerminalResponse(response);
    //        } else
    //        {
    //            SOAPFault fault = response.getSOAPBody().getFault();
    //            System.err.println("Received SOAP Fault");
    //            System.err.println("SOAP Fault Code :" + fault.getFaultCode());
    //            System.err.println("SOAP Fault String :" + fault.getFaultString());
    //        }
    //    }

    // 打印信息
    public abstract void writeTerminalResponse(SOAPMessage message) throws SOAPException;
    //    {
    //        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
    //        Name terminalResponseName = envelope.createName("GetTerminalDetailsResponse", PREFIX, NAMESPACE_URI);
    //        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message.getSOAPBody()
    //                .getChildElements(terminalResponseName).next();
    //        Name terminals = envelope.createName("terminals", PREFIX, NAMESPACE_URI);
    //        Name terminal = envelope.createName("terminal", PREFIX, NAMESPACE_URI);
    //        SOAPBodyElement terminalsElement = (SOAPBodyElement) terminalResponseElement.getChildElements(terminals).next();
    //        @SuppressWarnings("rawtypes")
    //        Iterator childElements2 = terminalsElement.getChildElements(terminal);
    //        while (childElements2.hasNext()) {
    //            SOAPBodyElement terminalElement = (SOAPBodyElement) childElements2.next();
    //            NodeList list = terminalElement.getChildNodes();
    //            Node n = null;
    //            for (int i = 0; i < list.getLength(); i++) {
    //                n = list.item(i);
    //                if ("dateActivated".equalsIgnoreCase(n.getLocalName()))
    //                    System.out.println("Date Activated = " + n.getTextContent());
    //                if ("status".equalsIgnoreCase(n.getLocalName()))
    //                    System.out.println("Activated Status = " + n.getTextContent());
    //            }
    //
    //        }
    //    }

    // 安全校验
    public SOAPMessage secureMessage(SOAPMessage message, final String username, final String password)
            throws IOException, XWSSecurityException
    {
        CallbackHandler callbackHandler = new CallbackHandler()
        {
            @Override
            public void handle(Callback[] callbacks) throws UnsupportedCallbackException
            {
                for (int i = 0; i < callbacks.length; i++)
                {
                    if (callbacks[i] instanceof UsernameCallback)
                    {
                        UsernameCallback callback = (UsernameCallback) callbacks[i];
                        callback.setUsername(username);
                    } else if (callbacks[i] instanceof PasswordCallback)
                    {
                        PasswordCallback callback = (PasswordCallback) callbacks[i];
                        callback.setPassword(password);
                    } else
                    {
                        throw new UnsupportedCallbackException(callbacks[i]);
                    }
                }
            }
        };
        InputStream policyStream = null;
        XWSSProcessor processor = null;
        try
        {
            policyStream = getClass().getResourceAsStream("/config/securityPolicy.xml");
            processor = processorFactory.createProcessorForSecurityConfiguration(policyStream, callbackHandler);
        } finally
        {
            if (policyStream != null)
            {
                policyStream.close();
            }
        }
        ProcessingContext context = processor.createProcessingContext(message);
        return processor.secureOutboundMessage(context);
    }
}