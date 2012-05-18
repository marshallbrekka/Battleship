package networking;

public class SocketLanguage {
	public static enum MESSAGE_CODES {JOIN_GAME, EXIT_GAME, FLEET, PLAY_AGAIN, FIRE, GAME_JOINED, GAME_REJECTED};
	
	
	public static MESSAGE_CODES getCode(int val) {
		return MESSAGE_CODES.values()[val];
	}
	
	public static int createCode(MESSAGE_CODES code) {
		return code.ordinal();
	}

}


