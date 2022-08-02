package daybreak.abilitywar.skabi.conditions.game;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.GameManager;
import daybreak.abilitywar.skabi.utils.Negated;
import java.util.Arrays;
import org.bukkit.event.Event;

public class CondGameIsRunning extends Condition {
	public static final String[] patterns = {"[a(w|bilitywar) ]game (1¦is|2¦is(n't| not)) running"};

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		setNegated(parseResult.mark == 2);
		return true;
	}

	@Override
	public boolean check(Event event) {
		return Negated.value(isNegated(), GameManager.isGameRunning());
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}
}
