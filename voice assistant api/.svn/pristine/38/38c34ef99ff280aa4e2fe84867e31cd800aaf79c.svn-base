package radius.voice.assistant.Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import m2m.platform.log.M2MLogger;

public class Utility {
	/**
	 * Make and load the object
	 */
	private static Utility INSTANCE;
	/**
	 * Get the root directory
	 */
	public static String rootDir = System.getenv().get("DM_HOME");
	/**
	 * Hold the Aes key
	 */
	private final static String KEY = "XeniusVoiceRadiuS";
	/**
	 * Hold the Aes key
	 */
	private final static String INITVECTOR = "XeniusVoiceRadiuS";
	/**
	 * Hold the Aes key
	 */
	private final static String TOKEN_KEY = "VoiceXenius21";
	/**
	 * Hold the Aes key
	 */
	
	private final static String TOKEN_INITVECTOR = "VoiceXenius21";
	private final static String MD5 = "MD5";
	private final static String SUN = "SUN";
	public static String getKey() {
		return KEY;
	}

	public static String getInitvector() {
		return INITVECTOR;
	}

	public static String getTokenKey() {
		return TOKEN_KEY;
	}

	public static String getTokenInitvector() {
		return TOKEN_INITVECTOR;
	}

	public static String getMd5() {
		return MD5;
	}

	public static String getSun() {
		return SUN;
	}
	/**
	 * Set error log file path
	 */
	public static String ERROR_HTML_PATH = "/tmp" + File.separator + "voiceassistant.txt";
	/**
	 *
	 * @param e
	 */
	public static void printStackTrace(Exception e) {
		try {
			PrintWriter stacktrace = new PrintWriter(
					new BufferedWriter(new FileWriter(Paths.get(Utility.ERROR_HTML_PATH).toFile().toString(), true)));
			stacktrace.write("<tr><td>" + new Date() + "</td>");
			if (e.getClass().getName().equals("com.fasterxml.jackson.core.JsonParseException")) {
				stacktrace.write("<td>6001 </td><td>");
			} else if (e.getClass().getName().equals("java.io.FileNotFoundException")) {
				stacktrace.write("<td>2001 </td><td>");
			} else if (e.getClass().getName().equals("java.lang.NullPointerException")) {
				stacktrace.write("<td>3001 </td><td>");
			} else if (e.getClass().getName().equals("java.lang.IllegalArgumentException")) {
				stacktrace.write("<td>4001 </td><td>");
			} else if (e.getClass().getName().equals("java.lang.JsonMappingException")) {
				stacktrace.write("<td>5001 </td><td>");
			} else if (e.getClass().getName().equals("javax.xml.parsers.ParserConfigurationException")) {
				stacktrace.write("<td>7001 </td><td>");
			} else if (e.getClass().getName().equals("org.xml.sax.SAXException")) {
				stacktrace.write("<td>8001 </td>");
			} else if (e.getClass().getName().equals("java.io.IOException")) {
				stacktrace.write("<td>9001 </td><td>");
			} else if (e.getClass().getName().equals("org.w3c.dom.DOMException")) {
				stacktrace.write("<td>9901 </td><td>");
			} else if (e.getClass().getName().equals("java.net.MalformedURLException")) {
				stacktrace.write("<td>9801 </td><td>");
			} else if (e.getClass().getName().equals("java.net.ProtocolException")) {
				stacktrace.write("<td>9802 </td><td>");
			} else if (e.getClass().getName().equals("java.io.UnsupportedEncodingException")) {
				stacktrace.write("<td>2201 </td><td>");
			} else if (e.getClass().getName().equals("java.net.UnknownHostException")) {
				stacktrace.write("<td>9803 </td><td>");
			} else {
				stacktrace.write("<td>1001 </td><td>");
			}
			e.printStackTrace(stacktrace);
			stacktrace.write("</td><td>-</td></tr>");
			stacktrace.flush();
			stacktrace.close();
			e.printStackTrace();
			M2MLogger.error(e.getMessage(), Utility.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 *
	 * @param e
	 * @param data_logger
	 */
	public static void printStackTrace(Exception e, String class_name) {
		try {
			PrintWriter stacktrace = new PrintWriter(
					new BufferedWriter(new FileWriter(Paths.get(Utility.ERROR_HTML_PATH).toFile().toString(), true)));
			stacktrace.write("<tr><td>" + new Date() + "</td>");
			if (e.getClass().getName().equals("com.fasterxml.jackson.core.JsonParseException")) {
				stacktrace.write("<td>6001 </td><td>");
			} else if (e.getClass().getName().equals("java.io.FileNotFoundException")) {
				stacktrace.write("<td>2001 </td><td>");
			} else if (e.getClass().getName().equals("java.lang.NullPointerException")) {
				stacktrace.write("<td>3001 </td><td>");
			} else if (e.getClass().getName().equals("java.lang.IllegalArgumentException")) {
				stacktrace.write("<td>4001 </td><td>");
			} else if (e.getClass().getName().equals("java.lang.JsonMappingException")) {
				stacktrace.write("<td>5001 </td><td>");
			} else if (e.getClass().getName().equals("javax.xml.parsers.ParserConfigurationException")) {
				stacktrace.write("<td>7001 </td><td>");
			} else if (e.getClass().getName().equals("org.xml.sax.SAXException")) {
				stacktrace.write("<td>8001 </td><td>");
			} else if (e.getClass().getName().equals("java.io.IOException")) {
				stacktrace.write("<td>9001 </td><td>");
			} else if (e.getClass().getName().equals("org.w3c.dom.DOMException")) {
				stacktrace.write("<td>9901 </td><td>");
			} else if (e.getClass().getName().equals("java.net.MalformedURLException")) {
				stacktrace.write("<td>9801 </td><td>");
			} else if (e.getClass().getName().equals("java.net.ProtocolException")) {
				stacktrace.write("<td>9802 </td><td>");
			} else if (e.getClass().getName().equals("java.io.UnsupportedEncodingException")) {
				stacktrace.write("<td>2201 </td><td>");
			} else if (e.getClass().getName().equals("java.net.UnknownHostException")) {
				stacktrace.write("<td>9803 </td><td>");
			} else {
				stacktrace.write("<td>1001 </td><td>");
			}
			e.printStackTrace(stacktrace);
			stacktrace.write("</td><td>" + class_name + "</td></tr>");
			stacktrace.flush();
			stacktrace.close();
			e.printStackTrace();
			M2MLogger.error(e.getMessage() + " :: " + class_name, Utility.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private Utility() {
		if (rootDir == null) {
			rootDir = ".";
		}
		try {
			ERROR_HTML_PATH = rootDir + File.separator + "logs" + File.separator + "voiceassistant.txt";
			File error_file = new File(ERROR_HTML_PATH);
			error_file.createNewFile();
		} catch (Exception e) {
			Utility.EmptyErrorFile();
		}
	}
	/**
	 *
	 * @return
	 */
	public static void EmptyErrorFile() {
		try {
			/**
			 * Empty the file
			 */
			Files.write(Paths.get(Utility.ERROR_HTML_PATH), " ".getBytes());
		} catch (Exception e) {
			Utility.printStackTrace(e);
		}
	}
	/**
	 *
	 * @return
	 */
	public static void loadProperties() {
		INSTANCE = new Utility();
	}

	/**
	 * @return
	 */
	public static Utility getInstance() {
		return INSTANCE;
	}
	public static long apiMill(final long s1, final long s2) {
		try {
			return s2 - s1;
		} catch (Exception e) {
			return -1;
		}
	}
}
