package daybreak.abilitywar.skabi.conditions.game;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.skabi.utils.Logger;
import daybreak.abilitywar.skabi.utils.Negated;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class CondIsParticipating extends Condition {
	public static final String[] patterns = {"%player% participates %game%", "%player% is participating %game%", "%player% has joined %game%", "%player% does( not|n't) participate %game%", "%player% is( not|n't) participating %game%", "%player% has( not|n't) joined %game%"};

	private Expression<Player> playerExpression;
	private Expression<AbstractGame> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		this.playerExpression = (Expression<Player>) expressions[0];
		this.gameExpression = (Expression<AbstractGame>) expressions[1];
		setNegated(matchedPattern >= 3);
		return true;
	}

	@Override
	public boolean check(Event event) {
		final Player player = this.playerExpression.getSingle(event);
		if (player == null) {
			Logger.error("플레이어가 null입니다.");
			return false;
		}
		final AbstractGame game = gameExpression.getSingle(event);
		if (game == null) {
			Logger.error("게임이 null입니다.");
			return false;
		}
		if (!game.isRunning()) {
			Logger.error("게임이 실행 중이지 않습니다.");
			return false;
		}
		return Negated.value(isNegated(), game.isParticipating(player));
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}

}
