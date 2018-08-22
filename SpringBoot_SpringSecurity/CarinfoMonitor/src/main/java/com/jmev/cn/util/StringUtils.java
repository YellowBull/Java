package com.jmev.cn.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils  extends org.springframework.util.StringUtils{

	/**
	 * 该函数得到c_Str中的第c_i个以c_Split分割的字符串
	 * 
	 * @param c_Str
	 *            目标字符串
	 * @param c_i
	 *            位置
	 * @param c_Split
	 *            分割符
	 * @return 如果发生异常，则返回空
	 */
	public static String getStr(String c_Str, int c_i, String c_Split) {
		String t_Str1 = "";
		String t_Str2 = "";
		String t_strOld = "";
		int i = 0;
		int i_Start = 0;
		t_Str1 = c_Str;
		t_Str2 = c_Split;
		i = 0;
		try {
			while (i < c_i) {
				i_Start = t_Str1.indexOf(t_Str2, 0);
				if (i_Start >= 0) {
					i += 1;
					t_strOld = t_Str1;
					t_Str1 = t_Str1.substring(i_Start + t_Str2.length(), t_Str1
							.length());
				} else {
					if (i != c_i - 1) {
						t_Str1 = "";
					}
					break;
				}
			}

			if (i_Start >= 0) {
				t_Str1 = t_strOld.substring(0, i_Start);
			}
		} catch (Exception ex) {
			t_Str1 = "";
		}
		return t_Str1;
	}
	
	
	 /**
	 * 字符串替换函数，替换时不区分大小写
	 * 
	 * @param strMain
	 *            String 原串
	 * @param strFind
	 *            String 查找字符串
	 * @param strReplaceWith
	 *            String 替换字符串,在替换时不区分大小写
	 * @return String 替换后的字符串，如果原串为空或者为""，则返回""
	 */
    public static String replaceEx(String strMain, String strFind, String strReplaceWith)
    {
        if (strMain == null || strMain.equals(""))
        {
            return "";
        }

        StringBuffer tSBql = new StringBuffer();
		// 小写化仅仅是为了确定替换的位置
        String tStrMain = strMain.toLowerCase();
        String tStrFind = strFind.toLowerCase();

        int intStartIndex = 0;
        int intEndIndex = 0;

        while ((intEndIndex = tStrMain.indexOf(tStrFind, intStartIndex)) > -1)
        {
			// 拼凑真实sql
            tSBql.append(strMain.substring(intStartIndex, intEndIndex));
			// 替换变量为查询值
            tSBql.append(strReplaceWith);

            intStartIndex = intEndIndex + strFind.length();
        }
        tSBql.append(strMain.substring(intStartIndex, strMain.length()));

        return tSBql.toString();
    }


	/**
	 * 把数字金额转换为中文大写金额 author: HST
	 * 
	 * @param money
	 *            数字金额(double)
	 * @return 中文大写金额(String)
	 */
	public static String getChnMoney(double money) {
		String ChnMoney = "";
		String s0 = "";

		// 在原来版本的程序中，getChnMoney(585.30)得到的数据是585.29。

		if (money == 0.0) {
			ChnMoney = "人民币零元整";
			return ChnMoney;
		}

		if (money < 0) {
			s0 = "负";
			money *= (-1);
		}

		String sMoney = new DecimalFormat("0").format(money * 100);

		int nLen = sMoney.length();
		String sInteger;
		String sDot;
		if (nLen < 2) {
			// add by JL at 2004-9-14
			sInteger = "";
			if (nLen == 1) {
				sDot = "0" + sMoney.substring(nLen - 1, nLen);
			} else {
				sDot = "0";
			}
		} else {
			sInteger = sMoney.substring(0, nLen - 2);
			sDot = sMoney.substring(nLen - 2, nLen);
		}

		String sFormatStr = formatStr(sInteger);

		String s1 = getChnM(sFormatStr.substring(0, 4), "亿");

		String s2 = getChnM(sFormatStr.substring(4, 8), "万");

		String s3 = getChnM(sFormatStr.substring(8, 12), "");

		String s4 = getDotM(sDot);

		if (s1.length() > 0 && s1.substring(0, 1).equals("0")) {
			s1 = s1.substring(1, s1.length());
		}
		if (s1.length() > 0
				&& s1.substring(s1.length() - 1, s1.length()).equals("0")
				&& s2.length() > 0 && s2.substring(0, 1).equals("0")) {
			s1 = s1.substring(0, s1.length() - 1);
		}
		if (s2.length() > 0
				&& s2.substring(s2.length() - 1, s2.length()).equals("0")
				&& s3.length() > 0 && s3.substring(0, 1).equals("0")) {
			s2 = s2.substring(0, s2.length() - 1);
		}
		if (s4.equals("00")) {
			s4 = "";
			if (s3.length() > 0
					&& s3.substring(s3.length() - 1, s3.length()).equals("0")) {
				s3 = s3.substring(0, s3.length() - 1);
			}
		}
		if (s3.length() > 0
				&& s3.substring(s3.length() - 1, s3.length()).equals("0")
				&& s4.length() > 0 && s4.substring(0, 1).equals("0")) {
			s3 = s3.substring(0, s3.length() - 1);
		}
		if (s4.length() > 0
				&& s4.substring(s4.length() - 1, s4.length()).equals("0")) {
			s4 = s4.substring(0, s4.length() - 1);
		}
		if (s3.equals("0")) {
			s3 = "";
			s4 = "0" + s4;
		}

		ChnMoney = s0 + s1 + s2 + s3 + "元" + s4;
		if (ChnMoney.substring(0, 1).equals("0")) {
			ChnMoney = ChnMoney.substring(1, ChnMoney.length());
		}
		for (int i = 0; i < ChnMoney.length(); i++) {
			if (ChnMoney.substring(i, i + 1).equals("0")) {
				ChnMoney = ChnMoney.substring(0, i) + "零"
						+ ChnMoney.substring(i + 1, ChnMoney.length());
			}
		}

		if (sDot.substring(1, 2).equals("0")) {
			ChnMoney += "整";
		}

		return ChnMoney;
	}

	/**
	 * 得到money的角分信息
	 * 
	 * @param sIn
	 *            String
	 * @return String
	 */
	private static String getDotM(String sIn) {
		String sMoney = "";
		if (!sIn.substring(0, 1).equals("0")) {
			sMoney += getNum(sIn.substring(0, 1)) + "角";
		} else {
			sMoney += "0";
		}
		if (!sIn.substring(1, 2).equals("0")) {
			sMoney += getNum(sIn.substring(1, 2)) + "分";
		} else {
			sMoney += "0";
		}

		return sMoney;
	}

	/**
	 * 添加仟、佰、拾等单位信息
	 * 
	 * @param strUnit
	 *            String
	 * @param digit
	 *            String
	 * @return String
	 */
	private static String getChnM(String strUnit, String digit) {
		String sMoney = "";
		boolean flag = false;

		if (strUnit.equals("0000")) {
			sMoney += "0";
			return sMoney;
		}
		if (!strUnit.substring(0, 1).equals("0")) {
			sMoney += getNum(strUnit.substring(0, 1)) + "仟";
		} else {
			sMoney += "0";
			flag = true;
		}
		if (!strUnit.substring(1, 2).equals("0")) {
			sMoney += getNum(strUnit.substring(1, 2)) + "佰";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(2, 3).equals("0")) {
			sMoney += getNum(strUnit.substring(2, 3)) + "拾";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(3, 4).equals("0")) {
			sMoney += getNum(strUnit.substring(3, 4));
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}

		if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0")) {
			sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim()
					+ "0";
		} else {
			sMoney += digit.trim();
		}
		return sMoney;
	}

	/**
	 * 格式化字符
	 * 
	 * @param sIn
	 *            String
	 * @return String
	 */
	private static String formatStr(String sIn) {
		int n = sIn.length();
		String sOut = sIn;
		// int i = n % 4;

		for (int k = 1; k <= 12 - n; k++) {
			sOut = "0" + sOut;
		}
		return sOut;
	}

	/**
	 * 获取阿拉伯数字和中文数字的对应关系
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	private static String getNum(String value) {
		String sNum = "";
		Integer I = new Integer(value);
		int iValue = I.intValue();
		switch (iValue) {
		case 0:
			sNum = "零";
			break;
		case 1:
			sNum = "壹";
			break;
		case 2:
			sNum = "贰";
			break;
		case 3:
			sNum = "叁";
			break;
		case 4:
			sNum = "肆";
			break;
		case 5:
			sNum = "伍";
			break;
		case 6:
			sNum = "陆";
			break;
		case 7:
			sNum = "柒";
			break;
		case 8:
			sNum = "捌";
			break;
		case 9:
			sNum = "玖";
			break;
		}
		return sNum;
	}

	// 金额千分位格式化
	public static String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}

	/**
	 * 字符串替换指定字符
	 * 
	 * @param target
	 *            目标字符串
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @param code
	 *            字符
	 * @return String
	 */
	public static String replaceWithSpecialChar(String target, int start,
			int end, char code) {
		if (target != null && end > start && start < target.length()) {
			end = end > target.length() ? target.length() : end;
			String subStr = "";
			for (int i = start; i < end; i++) {
				subStr = subStr + code;
			}
			target = target.substring(0, start) + subStr
					+ target.subSequence(end, target.length());
		}
		return target;
	}
	
	/**
	 *controller中字符串转码
	 * @param str
	 * @return
	 */
    public static String encodeStr(String str) {  
        try {  
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
	
    /**
     * 格式化银行卡号
     * @param bankNo
     * @param unit
     * @return
     */
    public static String formatBankNo(String bankNo,int unit){
    	StringBuffer bankNoBuffer = new StringBuffer();
    	try {
			if (bankNo!=null&&!"".equals(bankNo)) {
				int bankNoLength = bankNo.length();
				int forTimes = bankNoLength%unit==0?bankNoLength/unit:bankNoLength/unit+1;
				for (int i = 0; i < forTimes; i++) {
					if(i==forTimes-1){
						bankNoBuffer.append(bankNo.substring(i*4, bankNoLength));
					}else{
						bankNoBuffer.append(bankNo.substring(i*4, (i+1)*4));	
						bankNoBuffer.append(" ");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return bankNoBuffer.toString();
    }
    
	/**
	 * 处理逗号
	 * @param commaString
	 * @return
	 */
	public static String formatcomma(String commaString) {
		String returnFormatComma = "";
		if (commaString.contains(",")) {
			String[] a = new String[] {};
			a = commaString.split(",");
			for (String string : a) {
				if (string.equals("") || string.equals(null)) {
					returnFormatComma = "";
				} else {
					returnFormatComma = returnFormatComma + string + ",";
				}
			}
			returnFormatComma=	returnFormatComma.substring(0,returnFormatComma.length()-1);
			
		} else {
			returnFormatComma = commaString.trim();
		}

		return returnFormatComma;
	}
    
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String getSex(String cardNo) {
		cardNo = cardNo.trim();
		if (cardNo == null || (cardNo.length() != 15 && cardNo.length() != 18)) {
			return "";
		}
		String lastValue = "";
		if (cardNo.length() == 15) {
			lastValue = cardNo.substring(14, 15);
		} else {
			lastValue = cardNo.substring(16, 17);
		}
		int sex;
		if (lastValue.trim().toLowerCase().equals("x") || lastValue.trim().toLowerCase().equals("e")) {
			return "M";
		} else {
			sex = Integer.parseInt(lastValue) % 2;
			return sex == 0 ? "F" : "M";
		}
	}
	/**
	 * 根据身份证获取出生年月
	 * @param cardNo
	 * @return
	 */
	public static String getBirthday(String cardNo) {
		cardNo = cardNo.trim();
		if (cardNo == null || (cardNo.length() != 15 && cardNo.length() != 18)) {
			return "";
		}
		String year;
		String month;
		String day;
		String birth;
		if (cardNo.length() == 18) {
			year = cardNo.substring(6, 10);
			month = cardNo.substring(10, 12);
			day = cardNo.substring(12, 14);
		} else {
			year = "19" + cardNo.substring(6, 8);
			month = cardNo.substring(8, 10);
			day = cardNo.substring(10, 12);
		}
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		birth = year + month + day;

		return birth;
	}

	
	public static  String getFileName( String filename){
//		System.out.println(filename);
		if (filename!=null&&filename!=""&&!filename.equals("")) {
			String[] s=filename.split("\\.");
	        filename=s[0];
		   System.out.println(filename);
		}
		return filename;
	}
	
    
	public static List<String> search(String regex, String string) {
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(string);
        while (m.find()) {
            String g = m.group();
            list.add(g);
        }
        return list;
    }
	
	
	public static boolean isNull(String string) {
        if (string == null || string.equals(""))
            return true;
        return false;
    }

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		String filename="1201506040003吴莲子.bmp";
	}

}
