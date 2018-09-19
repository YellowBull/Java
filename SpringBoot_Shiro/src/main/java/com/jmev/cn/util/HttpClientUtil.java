package com.jmev.cn.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

public class HttpClientUtil
{

    public static final String CHARSET = "UTF-8";

    public static String sendPost(String jsonStr, String path)
    {
        String msg = "";// 保存调用http服务后的响应信息
        try
        {
            byte[] data = jsonStr.getBytes();
            java.net.URL url = new java.net.URL(path);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒
            conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒
            conn.setDoInput(true); // 设置输入流采用字节流
            conn.setDoOutput(true); // 设置输出流采用字节流
            conn.setUseCaches(false); // 设置缓存
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("Accept-Charset", CHARSET);
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            /*
             * DataOutputStream out = new
             * DataOutputStream(conn.getOutputStream());
             * out.writeBytes(jsonStr); out.close();
             */
            OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
            outStream.write(data);
            outStream.close();// 关闭流

            String temp;
            // 如果请求响应码是200，则表示成功
            if (conn.getResponseCode() == 200)
            {
                // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
                while ((temp = in.readLine()) != null)
                {
                    //System.out.println("temp: "+temp);
                    msg += temp;
                }
                // msg = in.readLine();
                in.close();
            }
            conn.disconnect();// 断开连接
        } catch (Exception e)
        {
        }
        return msg;
    }

    public static String sendPost(String jsonStr, String path, Map<String, String> map)
    {
        String msg = "";// 保存调用http服务后的响应信息
        try
        {
            byte[] data = jsonStr.getBytes();
            System.out.println("jsonStr: " + jsonStr);
            java.net.URL url = new java.net.URL(path);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒
            conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒
            conn.setDoInput(true); // 设置输入流采用字节流
            conn.setDoOutput(true); // 设置输出流采用字节流
            conn.setUseCaches(false); // 设置缓存
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("Accept-Charset", CHARSET);
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            if (map != null)
            {
                for (String key : map.keySet())
                {
                    conn.setRequestProperty(key, map.get(key));
                }
            }
            OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
            outStream.write(data);
            outStream.close();// 关闭流

            System.out.println(conn.getResponseCode());
            String temp;
            // 如果请求响应码是200，则表示成功
            if (conn.getResponseCode() == 200)
            {
                // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
                while ((temp = in.readLine()) != null)
                {
                    msg += temp;
                }
                // msg = in.readLine();
                in.close();
            }
            conn.disconnect();// 断开连接
        } catch (Exception e)
        {
        }
        return msg;
    }

    public static String sendGet(String path, String vin, Map<String, String> map)
    {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try
        {
            //GET请求直接在链接后面拼上请求参数
            String mPath = path + "/" + vin;
            URL url = new URL(mPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            if (map != null)
            {
                for (String key : map.keySet())
                {
                    conn.setRequestProperty(key, map.get(key));
                }
            }
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
            String line;
            while ((line = in.readLine()) != null)
            {
                result.append(line);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        //关闭输入流
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static void main(String[] args)
    {
        String url = "http://112.35.77.149:18080/services/realData";
        //String codesStr="2902,3104,2606,2614,2302,2000,2501,2202,2613,2603,3801,2612,2609,2615,2001,2101,2301";
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("dataIds",codesStr);
        //jsonObject.put("vins", "LVXMAZAA4HS954708");

        jsonObject.put("vin", "LVXMAZAA4HS954708");
        jsonObject.put("page", "1");
        jsonObject.put("pageRecorders", "10");
        jsonObject.put("startTime", "2018-08-06 10:22:00");
        jsonObject.put("endTime", "2018-08-07 17:32:00");
        //System.out.println(jsonObject.toJSONString().replace("\"", "\\""));
        String string = "{\"vin\":\"LVXMAZAA4HS954708\",\"page\":1,\"pageRecorders\":10,\"startTime\":\"2018-08-06 10:22:00\",\"endTime\":\"2018-08-07 17:32:00\"}";
        String secConStr = HttpClientUtil.encrypt(string);
        System.out.println(secConStr);
        System.out.println(decrypt(secConStr));
    }

    public static String encrypt(String content)
    {

        String AES_KEY = "wHpRmGBdZx6QWuEv";
        try
        {
            if ("".equals(content))
                return "";
            String password = AES_KEY;
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器 15.
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            //String str = Base64.encode(result);
            String str = Base64.encode(result);
            return str; // 加密
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        } catch (InvalidKeyException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        } catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param str 待解密内容
     * @return
     */
    public static String decrypt(String str)
    {
        try
        {
            String password = "wHpRmGBdZx6QWuEv";
            // byte[] content = Base64.decode(str);
            byte[] content = Base64.decode(str);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return new String(result); // 加密
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        } catch (InvalidKeyException e)
        {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        } catch (BadPaddingException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    //  public statics void main(String[] args) throws ParseException, IOException {

    	/*Map<String, String> map =new HashMap<String,String>();
		String params ="{\"mobileNo\":" + "13120203030"+ ",\"password\":" + "111111" + "}";
		String Result = sendPost(params,"http://app.jmev.com.cn:8081/mobile/manageUserLogin");
		JSONObject jsStr = JSONObject.parseObject(Result);
		System.out.println("jsStr: "+jsStr.toJSONString());

		JSONObject jsonObject= JSON.parseObject(jsStr.toString());
        String data = jsonObject.getString("data");
        JSONObject jsondata= JSON.parseObject(data);
        String token = jsondata.getString("token");
        String userId = jsondata.getString("id");
        System.out.println("userId: "+userId+" token: "+token);

		map.put("userId", userId);
		map.put("token",token);

		String vin= "LVXMAZAA5JS920297";
		String json = sendGet("http://app.jmev.com.cn:8081/mobile/selectVinByManage",vin, map );
		System.out.println("json: "+json);

		String controlType = "1";
		String controlSonType ="1";
		String controlValue = "1";
		String appLat = "28.77284100";
		String appLon = "115.83911200";

		String param ="{\"vin\":\""+vin+"\",\"controlType\":\""+controlType+"\",\"controlSonType\":\""+controlSonType +"\",\"controlValue\":\""+ controlValue+"\",\"appLat\":\""+ appLat+"\",\"appLon\":\""+appLon+"\"}";
		String url ="http://app.jmev.com.cn:8081/mobile/vehicleControlManage";*/

    	/*String url = "http://218.205.184.122:18080/services/realData";
    	String codesStr="2902,3104,2606,2614,2302,2000,2501,2202,2613,2603,3801,2612,2609,2615,2001,2101,2301";
    	String vinsStr="LVXMAZAAXHS958652";
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("dataIds",codesStr);
    	jsonObject.put("vins", vinsStr);

    	String secConStr = HttpClientUtil.encrypt(jsonObject.toString());
    	JSONObject jsonObjecthead = new JSONObject();
    	jsonObjecthead.put("requestMsg", secConStr);
    	jsonObjecthead.put("sign", "");

    	String TOKEN="bearer WLs+BESmEwfmHFWv54q9D9lmGWK7nCBm";

    	Map<String, String> map =new HashMap<String,String>();
    	map.put("Authorization", TOKEN);
    	map.put("Content-type", "application/json;charset=UTF-8");
    	String result = HttpClientUtil.sendPost(jsonObjecthead.toString(), url, map);
    	JSONObject jsonx = JSON.parseObject(result);
    	String data = HttpClientUtil.decrypt(String.valueOf(jsonx.get("data")));
    	JSONObject jsondata= JSON.parseObject(data);

    	String vinStr = jsondata.get("LVXMAZAAXHS958652").toString();
    	System.out.println(vinStr);
    	JSONObject jsonVin= JSON.parseObject(vinStr);
    	Map<String , String> resultMap = new HashMap<String,String>();
    	if(jsonVin.getString("2301") == null) resultMap.put("chargeState", "");//充电状态
    	else {
    		switch (jsonVin.getString("2301")) {
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
		resultMap.put("maxFault", jsonVin.getString("2902"));//最高故障等级
		resultMap.put("netState", jsonVin.getString("3104"));//网络信号
		resultMap.put("minOneVol", jsonVin.getString("2606"));//最低单体电压
		resultMap.put("totalEle", jsonVin.getString("2614"));//总电流
		resultMap.put("chargeLinkState", "");//充电连接状态
		resultMap.put("controlTem", jsonVin.getString("2302"));//控制器温度
		resultMap.put("isSuccess", "");//信息是否查询成功
		resultMap.put("message", "");
		resultMap.put("time", jsonVin.getString("2000"));//数据更新时间
		resultMap.put("gpsState", jsonVin.getString("2501"));//GPS信号
		resultMap.put("runMeter", jsonVin.getString("2202"));//行驶里程
		resultMap.put("totalVol", jsonVin.getString("2613"));//总电压
		resultMap.put("maxOneVol", jsonVin.getString("2603"));//最高单体电压
		resultMap.put("faultCode", jsonVin.getString("3801"));//故障码
		resultMap.put("minTem", jsonVin.getString("2612"));//最低温度
		resultMap.put("maxTem", jsonVin.getString("2609"));//最低温度
		resultMap.put("soc", jsonVin.getString("2615")+"%");//soc
		resultMap.put("enduranceMileage", "");//续航里程
		resultMap.put("allCellsNum", jsonVin.getString("2001"));//单体电池总数
		resultMap.put("probeNum", jsonVin.getString("2101"));//温度探针个数
		System.out.println(JsonUtil.packageJson(resultMap));
	   String url = "http://10.250.100.87:8088/CarinfoMonitor1/getInfoController/getRecentState.mvc?deviceNo=";
		String result;
		String vin = "LVXMAZAA5HS930581";
		String cartype = "E200";
		result = HttpClientUtil.sendPost("",url+vin);
		System.out.println(result.charAt(result.length()-1));
		String stri = result.substring(0, result.length()-1);
		result = stri+",\"cartype\":\""+cartype+"\"}";
		System.out.println(result);
   }
    	 */
}
