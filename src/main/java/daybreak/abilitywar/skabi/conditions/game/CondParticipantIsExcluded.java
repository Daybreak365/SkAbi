package daybreak.abilitywar.skabi.conditions.game;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.game.module.DeathManager;
import daybreak.abilitywar.skabi.utils.Logger;
import daybreak.abilitywar.skabi.utils.Negated;
import org.bukkit.event.Event;

import java.util.Arrays;

public class CondParticipantIsExcluded extends Condition {
	public static final String[] patterns = {"%participant% is excluded", "%participant% is( not|n't) excluded"};

	private Expression<Participant> participantExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		this.participantExpression = (Expression<Participant>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		final Participant participant = this.participantExpression.getSingle(event);
		if (participant == null) {
			Logger.error("참가자가 null입니다.");
			return false;
		}
		final AbstractGame game = participant.getGame();
		if (!game.isRunning()) {
			Logger.error("게임이 실행 중이지 않습니다.");
			return false;
		}
		if (game instanceof DeathManager.Handler) {
			return Negated.value(isNegated(), ((DeathManager.Handler) game).getDeathManager().isExcluded(participant.getPlayer()));
		} else {
			Logger.error("탈락 매니저를 지원하는 게임이 아닙니다.");
			return false;
		}
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}

}
