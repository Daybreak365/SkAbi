package daybreak.abilitywar.skabi.expressions.game;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.event.GameWinEvent.Winner;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpNameOfWinner extends SimpleExpression<String> {

	public static final String pattern = "[the ]name of %winner%";

	private Expression<Winner> winner;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.winner = (Expression<Winner>) expressions[0];
		return true;
	}

	@Override
	protected String[] get(Event event) {
		final Winner winnerSingle = this.winner.getSingle(event);
		if (winnerSingle == null) {
			Logger.error("winner가 null입니다.");
			return SkAbi.EMPTY_STRING_ARRAY;
		}
		return winnerSingle.getWinnerNames().toArray(new String[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}
}
