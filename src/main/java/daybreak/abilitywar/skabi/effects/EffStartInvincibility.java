package daybreak.abilitywar.skabi.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.module.Invincibility;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class EffStartInvincibility extends Effect {
	public static final String pattern = "start inv[incibility] from %game%[ for %-number% second[s]]";

	private Expression<AbstractGame> abstractGame;
	private Expression<Number> seconds;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.abstractGame = (Expression<AbstractGame>) expressions[0];
		this.seconds = (Expression<Number>) expressions[1];
		return true;
	}

	@Override
	protected void execute(Event event) {
		final AbstractGame game = abstractGame.getSingle(event);
		if (game == null) {
			Logger.error("게임이 null입니다.");
			return;
		}
		if (!game.isRunning()) {
			Logger.error("게임이 실행 중이지 않습니다.");
			return;
		}
		final int seconds;
		if (this.seconds != null) {
			final Number number = this.seconds.getSingle(event);
			seconds = number != null ? number.intValue() : 0;
		} else {
			seconds = -1;
		}
		if (game instanceof Invincibility.Handler) {
			final Invincibility.Handler inv = (Invincibility.Handler) game;
			if (seconds > 0) {
				inv.getInvincibility().start(seconds);
			} else {
				inv.getInvincibility().start(true);
			}
		} else {
			Logger.error(game.getClass().getSimpleName() + "는 무적을 지원하지 않는 게임입니다.");
		}
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}
}
