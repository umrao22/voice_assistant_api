/**
 * @copyright RADIUS SYNERGIES INTERNATIONAL PVT. LTD
 */
package radius.voice.assistant.Utility;

import java.io.File;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.StringEscapeUtils;

/**
 * @author HRIDENDRA
 */
public class Conceal implements Serializable, Cloneable {
	/**
	 * SerialVersionUID is used to ensure that during DESERIALIZATION the same class
	 * (that was used during serialize process) is loaded.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Hold the instance of the class
	 */
	private static volatile Conceal INSTANCE = new Conceal();
	/**
	 * keySize CSN be of 128, 192 and 256 bits. keySize=16 bytes 1 bytes =8 bits
	 */
	private final String key;
	/**
	 * 16 bytes IV
	 */
	private final String initVector;
	/**
	 * Cipher for encode
	 */
	private final MessageDigest md;
	/**
	 * keySize CSN be of 128, 192 and 256 bits. keySize=16 bytes 1 bytes =8 bits
	 */
	private final String token_key;
	/**
	 * 16 bytes IV
	 */
	private final String token_initVector;

	/**
	 * Get the Instance
	 * 
	 * @return
	 */
	public static Conceal getInstance() {
		/**
		 * Check for the Null
		 */
		if (INSTANCE == null) {
			INSTANCE = new Conceal();
		}
		return INSTANCE;
	}

	/**
	 * {@link Constructor}
	 */
	private Conceal() {
		try {
			key = Utility.getKey();
			initVector = Utility.getInitvector();
			token_key = Utility.getTokenKey();
			token_initVector = Utility.getTokenInitvector();
			/**
			 * MD 5 Key
			 */
			md = MessageDigest.getInstance(Utility.getMd5(), Utility.getSun());
		} catch (Exception se) {
			throw new SecurityException("In MD5 constructor " + se);
		}
		M2MLogger.info("Conceal object is created.", Conceal.class);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		/**
		 * Directly throw Clone Not Supported Exception
		 */
		throw new CloneNotSupportedException();
	}

	/**
	 * To suppress creating new object during de-serialization
	 * 
	 * @return
	 * @throws ObjectStreamException
	 */
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private String base64Encrypt(String s) {
		/**
		 * Check for the Null
		 */
		if (s == null) {
			return null;
		}
		try {
			return DatatypeConverter.printBase64Binary(s.getBytes());
		} catch (Exception e) {
			return s;
		}
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private String base64Decrypt(String s) {
		/**
		 * Check for the Null
		 */
		if (s == null) {
			return null;
		}
		try {
			return new String(DatatypeConverter.parseBase64Binary(s));
		} catch (Exception e) {
			return s;
		}
	}

	/**
	 * AES encryption Logic Step 1. base64 Step 2. AES
	 * 
	 * @param value
	 * @return
	 */
	private String aesEncrypt(String s) {
		/**
		 * Check for the Null
		 */
		if (s == null) {
			return null;
		}
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(Literal.UTF8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(Literal.UTF8), Literal.AES);
			Cipher cipher = Cipher.getInstance(Literal.AES_CHIPER_CLASS);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return DatatypeConverter.printBase64Binary(cipher.doFinal(s.getBytes()));
		} catch (Exception ex) {
			M2MLogger.error(ex.getMessage(), Conceal.class);
			ex.printStackTrace();
		}
		return s;
	}

	/**
	 * AES DECRYPTION
	 * 
	 * @param value
	 * @return
	 */
	private String aesDecrypt(String s) {
		/**
		 * Check for the Null
		 */
		if (s == null) {
			return null;
		}
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(Literal.UTF8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(Literal.UTF8), Literal.AES);

			Cipher cipher = Cipher.getInstance(Literal.AES_CHIPER_CLASS);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(s));
			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
			M2MLogger.error(ex.getMessage(), Conceal.class);
			return s;
		}
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public String enc64(String s) {
		return this.base64Encrypt(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public String dec64(String s) {
		return this.base64Decrypt(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public String encAES(String s) {
		return this.aesEncrypt(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public String decAES(String s) {
		return this.aesDecrypt(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	private String md5Encode(String s) {
		/**
		 * Check for the Null
		 */
		if (s == null) {
			return null;
		}
		try {
			byte[] raw = null;
			byte[] stringBytes = null;
			stringBytes = this.enc64(s).getBytes("UTF8");
			synchronized (md) {
				raw = md.digest(stringBytes);
			}
			return DatatypeConverter.printBase64Binary(raw);
		} catch (Exception ex) {
			ex.printStackTrace();
			M2MLogger.error(ex.getMessage(), Conceal.class);
			return s;
		}
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public String md5Enc(String s) {
		return this.md5Encode(s);
	}

	/**
	 * 
	 * @param s1
	 * @param dbs2
	 * @return
	 */
	public boolean matchPassword(final String s1, final String dbs2) {
		/**
		 * Check for the Null
		 */
		if (s1 != null && dbs2 != null && this.md5Encode(s1).equals(dbs2)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private String encToken(String data) {
		String finaldata = "";
		String generatedString1 = RandomStringUtils.randomAlphanumeric(10);
		String generatedString2 = RandomStringUtils.randomAlphanumeric(10);
		try {
			int len = data.length();
			String data1 = data.substring(0, 4);
			String data2 = data.substring(4, len - 4);
			String data3 = data.substring(len - 4);
			finaldata = data1 + generatedString1 + File.separator + data2 + generatedString2 + data3;
			finaldata = this.enc64(finaldata);
		} catch (Exception e) {
			M2MLogger.error(e.getMessage(), Conceal.class);
			e.printStackTrace();
		}

		return finaldata;

	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private String decToken(String data) {
		String finaldata = "";
		try {
			data = this.dec64(data);
			int len = data.length();
			String data1 = data.substring(0, 4);
			String data2 = data.substring(15, len - 14);
			String data3 = data.substring(len - 4);
			finaldata = data1 + data2 + data3;
		} catch (Exception e) {
			M2MLogger.error(e.getMessage(), Conceal.class);
			e.printStackTrace();
		}
		return finaldata;
	}

	/**
	 * Token encryption Logic Step 1. special logic Step 2. AES
	 * 
	 * @param value
	 * @return
	 */
	public String TokenEncrypt(String s) {
		/**
		 * Check for the Null
		 */
		if (s == null) {
			return null;
		} else {
			s = this.enc64(s);
		}
		try {
			/**
			 * First convert the text into base64
			 */
			IvParameterSpec iv = new IvParameterSpec(token_initVector.getBytes(Literal.UTF8));
			SecretKeySpec skeySpec = new SecretKeySpec(token_key.getBytes(Literal.UTF8), Literal.AES);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return DatatypeConverter.printBase64Binary(cipher.doFinal(encToken(s).getBytes()));
		} catch (Exception ex) {
			M2MLogger.error(ex.getMessage(), Conceal.class);
			ex.printStackTrace();
		}
		return s;
	}

	/**
	 * Token DECRYPTION
	 * 
	 * @param value
	 * @return
	 */
	public String TokenDecrypt(String s) {
		/**
		 * Check for the Null
		 */
		if (s == null) {
			return null;
		}
		try {
			IvParameterSpec iv = new IvParameterSpec(token_initVector.getBytes(Literal.UTF8));
			SecretKeySpec skeySpec = new SecretKeySpec(token_key.getBytes(Literal.UTF8), Literal.AES);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(s));
			return this.dec64(decToken(new String(original)));
		} catch (Exception ex) {
			ex.printStackTrace();
			M2MLogger.error(ex.getMessage(), Conceal.class);
			return s;
		}
	}

	/**
	 * @param data
	 * @return
	 */
	public String URLEncode(String data) {
		/**
		 * Check for the Null
		 */
		if (data == null) {
			return null;
		}
		try {
			return URLEncoder.encode(data, Literal.UTF_8);
		} catch (Exception ex) {
			ex.printStackTrace();
			M2MLogger.error(ex.getMessage(), Conceal.class);
			return data;
		}
	}

	/**
	 * @param data
	 * @return
	 */
	public String URLDecode(String data) {
		/**
		 * Check for the Null
		 */
		if (data == null) {
			return null;
		}
		try {
			return URLDecoder.decode(data, Literal.UTF_8);
		} catch (Exception ex) {
			ex.printStackTrace();
			M2MLogger.error(ex.getMessage(), Conceal.class);
			return data;
		}
	}

	/**
	 * @param data
	 * @return
	 */
	public String HtmlEncode(String data) {
		/**
		 * Check for the Null
		 */
		if (data == null) {
			return null;
		}
		try {
			return StringEscapeUtils.escapeHtml4(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			M2MLogger.error(ex.getMessage(), Conceal.class);
			return data;
		}
	}

	/**
	 * @param data
	 * @return
	 */
	public String HtmlDecode(String data) {
		/**
		 * Check for the Null
		 */
		if (data == null) {
			return null;
		}
		try {
			return StringEscapeUtils.unescapeHtml4(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			M2MLogger.error(ex.getMessage(), Conceal.class);
			return data;
		}
	}
}
