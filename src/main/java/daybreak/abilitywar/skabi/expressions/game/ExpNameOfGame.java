package daybreak.abilitywar.skabi.expressions.game;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpNameOfGame extends SimpleExpression<String> {

	public static final String pattern = "[the ]name of %game%";

	private Expression<AbstractGame> abstractGame;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.abstractGame = (Expression<AbstractGame>) expressions[0];
		return true;
	}

	@Override
	protected String[] get(Event event) {
		final AbstractGame game = abstractGame.getSingle(event);
		if (game == null) {
			Logger.error("게임이 null입니다.");
			return SkAbi.EMPTY_STRING_ARRAY;
		}
		return new String[] {game.getRegistration().getManifest().name()};
	}

	@Override
	public boolean isSingle() {
		return true;
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
