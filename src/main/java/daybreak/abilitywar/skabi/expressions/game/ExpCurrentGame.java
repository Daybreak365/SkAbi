package daybreak.abilitywar.skabi.expressions.game;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.GameManager;
import org.bukkit.event.Event;

public class ExpCurrentGame extends SimpleExpression<AbstractGame> {
	public static final String pattern = "current [a(w|bilitywar) ]game";

	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		return true;
	}

	@Override
	protected AbstractGame[] get(Event event) {
		return new AbstractGame[] {GameManager.getGame()};
	}

	@Override
	public Class<? extends AbstractGame> getReturnType() {
		return AbstractGame.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}

}
