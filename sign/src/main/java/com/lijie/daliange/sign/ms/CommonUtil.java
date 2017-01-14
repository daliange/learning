package com.lijie.daliange.sign.ms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Title : CommonUtil</strong><br>
 * <strong>Description : 通用工具类</strong><br>
 * <strong>Create on : 2013-4-25</strong><br>
 * 
 * @author linda1@cmbc.com.cn<br>
 */
public final class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	
	/**
	 * 生成指定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return
	 */
	public static String generateLenString(int length) {
		char[] cResult = new char[length];
		int[] flag = { 0, 0, 0 }; // A-Z, a-z, 0-9
		int i = 0;
		while (flag[0] == 0 || flag[1] == 0 || flag[2] == 0 || i < length) {
			i = i % length;
			int f = (int) (Math.random() * 3 % 3);
			if (f == 0)
				cResult[i] = (char) ('A' + Math.random() * 26);
			else if (f == 1)
				cResult[i] = (char) ('a' + Math.random() * 26);
			else
				cResult[i] = (char) ('0' + Math.random() * 10);
			flag[f] = 1;
			i++;
		}
		return new String(cResult);
	}
	
    /**
     * str空判断
     * @param str
     * @return
     * @author guoyx
     */
    public static boolean isnull(String str)
    {
        if (null == str || str.equalsIgnoreCase("null") || str.trim().equals(""))
        {
            return true;
        } else
            return false;
    }
    public static void main(String[] args) {
		String a="null";
		System.out.println(isnull(a));
	}
    
    /**
     * 生成待签名串
     * @param paramMap
     * @return
     * @author guoyx
     */
    public static String getPlainText(Map<String, Object> params)
    {
        StringBuffer content = new StringBuffer();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            if ("sign".equals(key))
            {
                continue;
            }
            String value = (String)params.get(key);
            // 空串不参与签名
            if (isnull(value))
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
    
    /**
     * 生成待签名串
     * @param paramMap
     * @return
     * @author guoyx
	 * @throws UnsupportedEncodingException 
     */
    public static String genSignData(Map<String, String> params) throws UnsupportedEncodingException
    {
        StringBuffer content = new StringBuffer();
        String charset = "UTF-8";
        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            if ("sign".equals(key))
            {
                continue;
            }
            String value = params.get(key);
            // 空串不参与签名
            if (CommonUtil.isnull(value))
            {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&"))
        {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }
}