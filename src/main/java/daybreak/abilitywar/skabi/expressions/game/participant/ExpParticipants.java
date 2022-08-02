package daybreak.abilitywar.skabi.expressions.game.participant;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpParticipants extends SimpleExpression<Participant> {
	public static final String pattern = "participants of %game%";

	private Expression<AbstractGame> abstractGame;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.abstractGame = (Expression<AbstractGame>) expressions[0];
		return true;
	}

	@Override
	protected Participant[] get(Event event) {
		final AbstractGame game = abstractGame.getSingle(event);
		if (game == null) {
			Logger.error("게임이 null입니다.");
			return SkAbi.EMPTY_PARTICIPANT_ARRAY;
		}
		if (!game.isRunning()) {
			Logger.error("게임이 실행 중이지 않습니다.");
			return SkAbi.EMPTY_PARTICIPANT_ARRAY;
		}
		return game.getParticipants().toArray(new Participant[0]);
	}

	@Override
	public Class<? extends Participant> getReturnType() {
		return Participant.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}

}
