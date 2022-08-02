package daybreak.abilitywar.skabi.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.GameManager;
import org.bukkit.event.Event;

public class EffStopGame extends Effect {

	public static final String pattern = "stop [a(w|bilitywar) ]game";

	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		return true;
	}

	@Override
	protected void execute(Event event) {
		GameManager.stopGame();
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}
}
