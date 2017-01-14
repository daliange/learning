/**
 * Copyright : http://www.sandpay.com.cn , 2011-2014
 * Project : paychannel-cmsb-provider
 * $Id$
 * $Revision$
 * Last Changed by pxl at 2016-12-5 下午2:25:36
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * pxl         2016-12-5        Initailized
 */
package com.lijie.daliange.sign.ms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @ClassName ：StringUtil
 * @author : pxl
 * @Date : 2016-12-5 下午2:25:36
 * @version 2.0.0
 *
 */
public class StringUtil {

	
	/**
	 * 将Map中的数据转换成key1=value1&key2=value2的形式 不包含签名域sign
	 * 
	 * @param data
	 *            待拼接的Map数据
	 * @return 拼接好后的字符串
	 */
	public static String coverMap2StringByTree(Map<String, String> data) {
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			if ("sign".equals(en.getKey().trim())) {
				continue;
			}
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			sf.append(en.getKey() + "=" + en.getValue()
					+ "&");
		}
		return sf.substring(0, sf.length() - 1);
	}
	
   /**
	* 将Map中的数据转换成key1=value1&key2=value2的形式 不包含签名域sign
	* @param data  待拼接的Map数据
	* @param comparator String.CASE_INSENSITIVE_ORDER
	* @return 拼接好后的字符串
	 */
	public static String coverMap2String(Map<String, String> data, Comparator<String> comparator) {
		
		 // 按照key做排列
        List<String> keys = new ArrayList<String>(data.keySet());
        Collections.sort(keys, comparator);
        
        StringBuffer sf = new StringBuffer();
        
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if ("sign".equals(key)) {
                continue;
            }
            String value = (String)data.get(key);
            // 空串不参与签名
            if (StringUtils.isBlank(value)) {
                continue;
            }
            sf.append(key + "=" + value + "&");
        }
        return sf.substring(0, sf.length() - 1);
	}
	
}
