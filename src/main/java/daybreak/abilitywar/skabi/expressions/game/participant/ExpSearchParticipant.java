package daybreak.abilitywar.skabi.expressions.game.participant;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExpSearchParticipant extends SimpleExpression<Participant> {
	public static final String pattern = "participant %player% of %game%";

	private Expression<Player> player;
	private Expression<AbstractGame> abstractGame;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.player = (Expression<Player>) expressions[0];
		this.abstractGame = (Expression<AbstractGame>) expressions[1];
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
		final Player player = this.player.getSingle(event);
		if (player == null) {
			Logger.error("플레이어가 null입니다.");
			return SkAbi.EMPTY_PARTICIPANT_ARRAY;
		}
		return new Participant[] {game.getParticipant(player)};
	}

	@Override
	public Class<? extends Participant> getReturnType() {
		return Participant.class;
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
