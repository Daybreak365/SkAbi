package daybreak.abilitywar.skabi.conditions.game;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.GameManager;
import daybreak.abilitywar.skabi.utils.Negated;
import java.util.Arrays;
import org.bukkit.event.Event;

public class CondStopGame extends Condition {

	public static final String[] patterns = {"stop [a(w|bilitywar) ]game", "stop [a(w|bilitywar) ]game not successful"};

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		return Negated.value(isNegated(), GameManager.stopGame());
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}
}
