package com.lijie.daliange.sign.ms;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 民生签名、验签函数
 * 
 * 
 * **/
public class Sign {
	
	private final static Logger logger = LoggerFactory.getLogger(Sign.class);
	
	
	/**民生验签**/
	public static  boolean verifySign(Map<String, String> data, String encoding) throws Exception {
		
		String plainText = StringUtil.coverMap2String(data, String.CASE_INSENSITIVE_ORDER);
		String sign = data.get("sign");
		boolean isValid = CryptoUtil.verifyDigitalSign(plainText.getBytes("UTF-8"), 
				Base64.decodeBase64(sign), 
				CryptoUtil.getPulbicKey("classpath:cmbc_gwpay_rsa_public_key_2048.pem", "classpath:cmbc_gwpay_rsa_public_key_2048.pem"), 
				"SHA1WithRSA");// 验签
		return isValid;
    
	}
	
	public static void main(String[] args) throws Exception {
		
		String charset = "UTF-8";
		
		Map<String ,String > mapVerifySign = new HashMap<>();
		mapVerifySign.put("sign", "RJOrmXlAJUIc1NQ85cd5Kw0w4uzFjnL493m4MyAcr5OB+/PzHtKYDV3RjaxFTaMF4OlviNxSQEOVAEbG+N56+oBGJvm0iSkb1hnxoUcPuavpe1s81caFJoApsQDdgw5Y8bju9uI40NNYucgAXIdCJWyPFNv5fIshztEFtQ3YXFHpNzTDwVp7cFjmHpySLr2r6blIx086dVIYUt5PQFAY0T68j2AayR2Gbw9NvhjuVcKuqSOtVJoOM6jstyg8x+QHE1fjdz54UsD4UtW/sKZAwYyHjbmjCzSAOFINq3pOaARatHdse4a7vDxWlhK/cp4n5tgY5AvfzqO8O3BLCPH0HA==");
		mapVerifySign.put("oid_paybill", "2017011304950619");
		mapVerifySign.put("oid_partner", "GWP_SHSD");
		mapVerifySign.put("no_order", "2017011304707304000000000001");
		mapVerifySign.put("resp_msg", "交易成功");
		
		mapVerifySign.put("pay_type", "3");
		mapVerifySign.put("resp_type", "S");
		mapVerifySign.put("resp_code", "00");
		mapVerifySign.put("money_order", "0.12");
		mapVerifySign.put("settle_date", "20170113");
		
		Boolean b = verifySign(mapVerifySign,charset);
		logger.info("验签结果=============="+b);
		
		Map<String,String> mapSign = new HashMap<>();
		
		mapSign.put("oid_partner", "GWP_TEST");
		mapSign.put("no_order", "20170114192514");
		mapSign.put("url_return", "http://110.80.39.174:9012/gwshmn/jsp/payReturn.jsp");
		mapSign.put("notify_url", "http://110.80.39.174:9012/gwshmn/stateChangeServlet");
		mapSign.put("name_goods", "测试商品");
		
		mapSign.put("money_order", "0.12");
		mapSign.put("bank_code", "01050000");
		mapSign.put("info_order", "用户购买话费0.12元");
		mapSign.put("sign", "");
		mapSign.put("valid_order", "30");
		
		mapSign.put("pay_acct_no", "");
		mapSign.put("pay_type", "3");
		mapSign.put("user_id", "111111");
		mapSign.put("dt_order", "20170114192514");
		mapSign.put("target_url", "http://110.80.39.174:9012/gwpay/payServlet");
		
		String plainBytes = CommonUtil.genSignData(mapSign);
		logger.info("plainBytes:"+plainBytes);
		PrivateKey hzfPriKey = CryptoUtil.getRSAPrivateKeyByFileSuffix("E://workspaceeclipsetest//learing//sign//src//main//resources//hzftest_pkcs8_rsa_private_key_2048.pem", "pem", null, "RSA");
		byte[] base64SingDataBytes = Base64.encodeBase64(CryptoUtil.digitalSign(plainBytes.getBytes(charset), hzfPriKey, "SHA1WithRSA"));
		String sign = new String(base64SingDataBytes, charset);
		logger.info("签名结果:"+sign);
		
	}

}
