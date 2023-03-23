package daybreak.abilitywar.skabi.conditions.game;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.team.TeamGame;
import daybreak.abilitywar.skabi.utils.Logger;
import daybreak.abilitywar.skabi.utils.Negated;
import org.bukkit.event.Event;

import java.util.Arrays;

public class CondIsTeamGame extends Condition {
	public static final String[] patterns = {"%game% is team game", "%game% is( not|n't) team game"};

	private Expression<AbstractGame> gameExpression;

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		this.gameExpression = (Expression<AbstractGame>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		final AbstractGame game = gameExpression.getSingle(event);
		if (game == null) {
			Logger.error("게임이 null입니다.");
			return false;
		}
		if (!game.isRunning()) {
			Logger.error("게임이 실행 중이지 않습니다.");
			return false;
		}
		return Negated.value(isNegated(), game instanceof TeamGame);
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}
}
