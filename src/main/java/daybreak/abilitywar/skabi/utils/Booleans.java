package daybreak.abilitywar.skabi.utils;

public class Booleans {

	private Booleans() {}

	public static final Boolean[] TRUE = new Boolean[]{Boolean.TRUE}, FALSE = new Boolean[]{Boolean.FALSE};

	public static Boolean[] toArray(final boolean bool) {
		return bool ? TRUE : FALSE;
	}

	public static boolean nullToFalse(final Boolean bool) {
		return bool != null ? bool : false;
	}

}
