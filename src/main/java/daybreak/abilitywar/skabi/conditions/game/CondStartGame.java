package daybreak.abilitywar.skabi.conditions.game;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.config.Configuration;
import daybreak.abilitywar.config.enums.ConfigNodes;
import daybreak.abilitywar.game.GameManager;
import daybreak.abilitywar.game.manager.GameFactory;
import daybreak.abilitywar.game.manager.GameFactory.GameRegistration;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import daybreak.abilitywar.skabi.utils.Negated;
import java.util.Arrays;
import org.bukkit.event.Event;

public class CondStartGame extends Condition {

	public static final String[] patterns = {"start [a(w|bilitywar) ]game[ %-string%]", "start [a(w|bilitywar) ]game[ %-string%] not successful"};

	private Expression<String> game;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		this.game = (Expression<String>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		if (game == null) {
			return Negated.value(isNegated(), GameManager.startGame(SkAbi.EMPTY_STRING_ARRAY));
		} else {
			final String name = game.getSingle(event);
			final GameRegistration registration = GameFactory.getByName(name);
			if (registration != null) {
				Configuration.modifyProperty(ConfigNodes.GAME_MODE, registration.getGameClass().getName());
				return Negated.value(isNegated(), GameManager.startGame(SkAbi.EMPTY_STRING_ARRAY));
			} else {
				Logger.error("존재하지 않는 게임 이름입니다: " + name);
				return Negated.value(isNegated(), false);
			}
		}
	}

	@Override
	public String toString(Event event, boolean b) {
		return Arrays.toString(patterns);
	}
}
