package daybreak.abilitywar.skabi.effects;

import ch.njol.skript.lang.Effect;
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
import org.bukkit.event.Event;

public class EffStartGame extends Effect {

	public static final String pattern = "start [a(w|bilitywar) ]game[ %-string%]";

	private Expression<String> gameName;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.gameName = (Expression<String>) expressions[0];
		return true;
	}

	@Override
	protected void execute(Event event) {
		if (gameName == null) {
			GameManager.startGame(SkAbi.EMPTY_STRING_ARRAY);
		} else {
			final String name = gameName.getSingle(event);
			final GameRegistration registration = GameFactory.getByName(name);
			if (registration != null) {
				Configuration.modifyProperty(ConfigNodes.GAME_MODE, registration.getGameClass().getName());
				GameManager.startGame(SkAbi.EMPTY_STRING_ARRAY);
			} else Logger.error("존재하지 않는 게임 이름입니다: " + name);
		}
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}
}
