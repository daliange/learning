package com.lijie.daliange.sign.pxl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class Sign {
	
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
		
		Map<String ,String > map = new HashMap<>();
		map.put("sign", "RJOrmXlAJUIc1NQ85cd5Kw0w4uzFjnL493m4MyAcr5OB+/PzHtKYDV3RjaxFTaMF4OlviNxSQEOVAEbG+N56+oBGJvm0iSkb1hnxoUcPuavpe1s81caFJoApsQDdgw5Y8bju9uI40NNYucgAXIdCJWyPFNv5fIshztEFtQ3YXFHpNzTDwVp7cFjmHpySLr2r6blIx086dVIYUt5PQFAY0T68j2AayR2Gbw9NvhjuVcKuqSOtVJoOM6jstyg8x+QHE1fjdz54UsD4UtW/sKZAwYyHjbmjCzSAOFINq3pOaARatHdse4a7vDxWlhK/cp4n5tgY5AvfzqO8O3BLCPH0HA==");
		map.put("oid_paybill", "2017011304950619");
		map.put("oid_partner", "GWP_SHSD");
		map.put("no_order", "2017011304707304000000000001");
		map.put("resp_msg", "交易成功");
		
		map.put("pay_type", "3");
		map.put("resp_type", "S");
		map.put("resp_code", "00");
		map.put("money_order", "0.12");
		map.put("settle_date", "20170113");
		
		Boolean b = verifySign(map,"UTF-8");
		System.out.println("验签结果=============="+b);
		
	}

}
