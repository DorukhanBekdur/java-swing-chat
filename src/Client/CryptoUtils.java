package Client;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {
	private static final String SECRET_KEY = "NWSwa0YyeF4tAa2N6HMfMsxF8s3ngLET";

	public static String encrypt(String plainText) throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SECRET_KEY.getBytes(), 65536, 256);
		SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static String decrypt(String encryptedText) throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SECRET_KEY.getBytes(), 65536, 256);
		SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

		return new String(decryptedBytes);
	}
}