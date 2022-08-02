package daybreak.abilitywar.skabi.expressions.game.ability;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.manager.AbilityList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.event.Event;

public class ExpRandomAbility extends SimpleExpression<String> {
	public static final String[] patterns = {"random ability"};
	private static final Random random = new Random();

	private List<String> names;

	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		return true;
	}

	@Override
	protected String[] get(Event event) {
		if (names == null || names.size() != AbilityList.values().size()) {
			this.names = AbilityList.nameValues();
		}
		return new String[] {names.get(random.nextInt(names.size()))};
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
