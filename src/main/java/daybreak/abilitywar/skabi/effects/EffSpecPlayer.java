package daybreak.abilitywar.skabi.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.manager.SpectatorManager;
import daybreak.abilitywar.skabi.utils.Booleans;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffSpecPlayer extends Effect {

	public static final String pattern = "set %player%'s spec[tate] state to %boolean%";

	private Expression<Player> playerExpression;
	private Expression<Boolean> stateExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.playerExpression = (Expression<Player>) expressions[0];
		this.stateExpression = (Expression<Boolean>) expressions[1];
		return true;
	}

	@Override
	protected void execute(Event event) {
		final Player player = playerExpression.getSingle(event);
		if (player != null) {
			if (Booleans.nullToFalse(stateExpression.getSingle(event))) {
				SpectatorManager.addSpectator(player.getName());
			} else {
				SpectatorManager.removeSpectator(player.getName());
			}
		} else Logger.error("플레이어가 null입니다.");
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}
}
