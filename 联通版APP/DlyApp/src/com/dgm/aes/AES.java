package com.dgm.aes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	public static final byte[] AES_KEY=new byte[]{1,2,3,4,5,6,7,8,9,0x0A,0,0,0,0,0,0};
	
	public static byte[] encrypt(byte[] data,byte[] key){
		try {
			if(key==null){
				return null;
			}
			if(key.length!=16){
				return null;
			}
			SecretKeySpec skeySpec=new SecretKeySpec(key,"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted=cipher.doFinal(data);
			return encrypted;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static byte[] decrypt(byte[] data,byte[] key){
		try{
			if(key==null){
				return null;
			}
			if(key.length!=16){
				return null;
			}
			SecretKeySpec skeySpec=new SecretKeySpec(key,"AES");
			Cipher cipher=Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted=cipher.doFinal(data);
			return encrypted;
		}catch(Exception e){
			
		}
		return null;
	}
}
