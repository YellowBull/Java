package com.jmev.cn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils
{
    public static List<Map<String, String>> getCarinfoFromNewPlatform(String vin, List<String> vins) throws Exception
    {
        String url = "http://112.35.77.149:18080/services/realData";
        String codesStr = "2902,3104,2606,2614,2302,2000,2501,2202,2613,2603,3801,2612,2609,2615,2001,2101,2301";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataIds", codesStr);
        jsonObject.put("vins", vin);

        String secConStr = HttpClientUtil.encrypt(jsonObject.toString());
        JSONObject jsonObjecthead = new JSONObject();
        jsonObjecthead.put("requestMsg", secConStr);
        jsonObjecthead.put("sign", "");

        String TOKEN = "bearer WLs+BESmEwfmHFWv54q9D9lmGWK7nCBm";
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Authorization", TOKEN);
        map.put("Content-type", "application/json;charset=UTF-8");
        String result = HttpClientUtil.sendPost(jsonObjecthead.toString(), url, map);
        JSONObject jsonx = JSON.parseObject(result);
        String data = HttpClientUtil.decrypt(String.valueOf(jsonx.get("data")));
        JSONObject dataJson = JSON.parseObject(data);
        for (String myvin : vins)
        {
            Map<String, String> resultMap = new HashMap<String, String>();
            //String vinValue = HttpClientUtil.decrypt(String.valueOf(dataJson.get(myvin)));
            //JSONObject jsondatas = JSON.parseObject(vinValue);
            if (dataJson == null || dataJson.get(myvin) == null)
            {
                resultMap.put("vin", myvin);
                list.add(resultMap);
                continue;
            }
            String vinStr = dataJson.get(myvin).toString();
            JSONObject jsonVin = JSON.parseObject(vinStr);
            if (jsonVin.getString("2301") == null)
                resultMap.put("chargeState", "");//充电状态
            else
            {
                switch (jsonVin.getString("2301"))
                {
                case "0x01":
                    resultMap.put("chargeState", "停车充电");//充电状态
                    break;
                case "0x02":
                    resultMap.put("chargeState", "行驶充电");//充电状态
                    break;
                case "0x03":
                    resultMap.put("chargeState", "未充电");//充电状态
                    break;
                case "0x04":
                    resultMap.put("chargeState", "充电完成");//充电状态
                    break;
                case "0xFE":
                    resultMap.put("chargeState", "异常");//充电状态
                    break;
                case "0xFF":
                    resultMap.put("chargeState", "无效");//充电状态
                    break;
                default:
                    resultMap.put("chargeState", "");//充电状态
                    break;
                }
            }

            resultMap.put("vin", myvin);
            //        resultMap.put("maxFault", jsonVin.getString("2902"));//最高故障等级
            //        resultMap.put("netState", jsonVin.getString("3104"));//网络信号
            //        resultMap.put("minOneVol", jsonVin.getString("2606"));//最低单体电压
            //        resultMap.put("totalEle", jsonVin.getString("2614"));//总电流
            //        resultMap.put("chargeLinkState", "");//充电连接状态
            //        resultMap.put("controlTem", jsonVin.getString("2302"));//控制器温度
            //        resultMap.put("isSuccess", "");//信息是否查询成功
            //        resultMap.put("message", "");
            resultMap.put("time", jsonVin.getString("2000"));//数据更新时间
            //        resultMap.put("gpsState", jsonVin.getString("2501"));//GPS信号
            //        resultMap.put("runMeter", jsonVin.getString("2202"));//行驶里程
            //        resultMap.put("totalVol", jsonVin.getString("2613"));//总电压
            //        resultMap.put("maxOneVol", jsonVin.getString("2603"));//最高单体电压
            //        resultMap.put("faultCode", jsonVin.getString("3801"));//故障码
            //        resultMap.put("minTem", jsonVin.getString("2612"));//最低温度
            //        resultMap.put("maxTem", jsonVin.getString("2609"));//最低温度
            //        resultMap.put("soc", jsonVin.getString("2615") + "%");//soc
            //        resultMap.put("enduranceMileage", "");//续航里程
            //        resultMap.put("allCellsNum", jsonVin.getString("2001"));//单体电池总数
            //        resultMap.put("probeNum", jsonVin.getString("2101"));//温度探针个数
            //        resultMap.put("cartype", cartype);
            list.add(resultMap);
        }

        return list;
    }
}
