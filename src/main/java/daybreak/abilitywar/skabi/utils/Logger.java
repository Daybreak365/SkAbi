package daybreak.abilitywar.skabi.utils;

import ch.njol.skript.Skript;

public class Logger {

	private Logger() {}

	public static void error(final String message) {
		final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		if (elements.length > 3) {
			Skript.error(elements[2].toString() + ": " + message);
		} else {
			Skript.error(message);
		}
	}

}
