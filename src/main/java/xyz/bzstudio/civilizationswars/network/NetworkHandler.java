package xyz.bzstudio.civilizationswars.network;

public class NetworkHandler {
	public static final String VERSION = "1.0";
	private static int id = 0;

	public static void register() {
	}

	private static int getId() {
		return id++;
	}
}
