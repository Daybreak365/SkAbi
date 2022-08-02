package daybreak.abilitywar.skabi.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.skabi.utils.Negated;
import java.util.Arrays;
import org.bukkit.event.Event;

public class CondIsNull extends Condition {

	public static final String[] patterns = {"%object% is null", "%object% is( not|n't) null"};

	private Expression<?> expression;

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		this.expression = expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		return Negated.value(isNegated(), expression.getSingle(event) == null);
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}
}
