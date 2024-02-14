/**
 * @copyright RADIUS SYNERGIES INTERNATIONAL PVT. LTD
 */
package radius.voice.assistant.Utility;
/**
 * @author HRIDENDRA
 */
public class M2MLogger extends m2m.platform.log.M2MLogger {

	public static void error(String message, Class<?> clazz) {
		m2m.platform.log.M2MLogger.error(message, clazz);
	}

	public static void info(String message, Class<?> clazz) {
		m2m.platform.log.M2MLogger.info(message, clazz);

	}
	
	public static void warn(String message, Class<?> clazz) {
		m2m.platform.log.M2MLogger.warn(message, clazz);
	}

	public static void fatal(String message, Class<?> clazz) {
		m2m.platform.log.M2MLogger.fatal(message, clazz);
	}

	public static void trace(String message, Class<?> clazz) {
		m2m.platform.log.M2MLogger.trace(message, clazz);
	}

	public static void debug(String message, Class<?> clazz) {
		m2m.platform.log.M2MLogger.debug(message, clazz);
	}

	public static void object(String message, Class<?> clazz) {
		m2m.platform.log.M2MLogger.object(message, clazz);
	}
}
