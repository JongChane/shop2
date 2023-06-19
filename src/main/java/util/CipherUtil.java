package util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class CipherUtil {
	private static byte[] randomKey;
	//초기화 벡터 : 첫번째 블럭에 값 제공
	//CBC모드 : 블럭 암호화시 앞블럭의 암호문이 뒤 블럭의 암호화에 영향을 줌
	//패딩방법 : 마지막블럭의 자리수를 지정된 블럭의 크기만큼 채우기 위한 방법 설정
	private final static byte[] iv = new byte[] {
			(byte)0x8E,0x12,0x39,(byte)0x9,
				  0x07,0x72,0x6F,(byte)0x5A,
			(byte)0x8E,0x12,0x39,(byte)0x9,
				  0x07,0x72,0x6F,(byte)0x5A};
	static Cipher cipher; //암호처리 객체
	static {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //알고리즘 /블럭암호모드/패딩방법
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String makehash(String plain, String algo) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance(algo);
		byte[] plainByte = plain.getBytes();
		byte[] hashByte = md.digest(plainByte);
		return byteToHex(hashByte);
	}
	private String byteToHex(byte[] hashByte) {
		if(hashByte == null) return null;
		String str = "";
		for (byte b : hashByte) {
			str += String.format("%02X", b);
		}
		return str;
	} 
	private static byte[] hexToByte(String str) {
		if(str == null || str.length() < 2) return null;
		int len = str.length() / 2; 
		byte[] buf = new byte[len]; 
		for(int i = 0 ; i < len ; i++) {
			buf[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
		}
		return buf;
	}
	private byte[] makeKey(String key) {
		//key : q1w2e3r4t5
		int len = key.length(); //10
		char ch = 'A';
		for(int i = len ; i < 16 ; i++) { //16바이트로 생성
			key += ch++; //q1w2e3r4t5ABCEDF
		}
		return key.substring(0,16).getBytes(); //16바이트로 생성
	}
	public String decrypt(String cipherMsg, String key) {
		byte[] plainMsg = new byte[1024];
		try {
			Key genkey = new SecretKeySpec(makeKey(key), "AES"); //AES알고리즘에서 사용할 키 객체로 생성
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); //CBC 모드에서 사용할 IV 설정
			//Cipher.DECRYPT_MODE : 복호화 기능
			cipher.init(Cipher.DECRYPT_MODE, genkey, paramSpec); //암호화 객체 설정
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim())); //복호화 실행
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim(); //byte[]형태의 평문 => 문자열
	}
	
	public String encrypt(String plain1, String key) {
		byte[] cipherMsg = new byte[1024];
		try {								//byte[]    알고리즘
			Key genKey = new SecretKeySpec(makeKey(key), "AES"); //128비트 크기
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, genKey, paramSpec);
			cipherMsg = cipher.doFinal(plain1.getBytes()); //암호문
		} catch(Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg);
	}
}