package it.er.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String getHash(String input) throws NoSuchAlgorithmException{
		byte[] defaultBytes = input.getBytes();
		StringBuffer hexString = new StringBuffer();
		
		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();
		String hex = null;
		for (byte b: messageDigest){
			hex = Integer.toHexString(0xFF & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
}
