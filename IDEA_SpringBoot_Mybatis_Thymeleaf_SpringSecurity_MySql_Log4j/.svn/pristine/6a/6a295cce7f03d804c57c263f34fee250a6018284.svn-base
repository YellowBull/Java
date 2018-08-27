package com.jmev.cn.service.unicomAPI;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("unicomAPIService")
public class UnicomAPIService 
{
    @Resource(name = "getTerminalDetailsClients")
    GetTerminalDetailsClients getTerminalDetailsClients;
    @Resource(name = "getTerminalUsageRequestClient")
    GetTerminalUsageRequestClient getTerminalUsageRequestClient;
    
    public Map<String, LinkedList<String>> GetTerminalDetails(List<String> iccids) throws Exception
    {
        return getTerminalDetailsClients.GetTerminalDetails(iccids);
    }

    public String GetTerminalUsageRequest(String iccid, String time) throws Exception
    {
        return getTerminalUsageRequestClient.GetTerminalUsageRequest(iccid, time);
    }

}

