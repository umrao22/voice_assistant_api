package radius.voice.assistant.Services;

import radius.voice.assistant.Utility.Conceal;

public class TokenService {
	/**
	 * Hold theTokenService
	 */
	private static TokenService instance;
	/**
	 * Hold the ENCRYPTION DECRYPTION
	 */
	private Conceal encdnc;
	/**
	 * @return
	 */
	public static TokenService getInstance() {
		/**
		 * Check for the Null
		 */
		if (instance == null) {
			instance = new TokenService();
		}
		return instance;
	}
}
