package daybreak.abilitywar.skabi.utils;

public class Negated {

	private Negated() {}

	public static boolean value(final boolean negated, final boolean bool) {
		return negated != bool;
	}

}
