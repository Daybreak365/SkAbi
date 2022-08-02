package daybreak.abilitywar.skabi.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.manager.object.AbilitySelect;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class EffReDrawAbilities extends Effect {
	public static final String pattern = "redraw abilities from %game%";

	private Expression<AbstractGame> abstractGame;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.abstractGame = (Expression<AbstractGame>) expressions[0];
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
		if (game instanceof AbilitySelect.Handler) {
			final AbilitySelect.Handler handler = (AbilitySelect.Handler) game;
			final AbilitySelect abilitySelect = handler.getAbilitySelect();
			if (abilitySelect != null) {
				abilitySelect.reset();
			} else Logger.error(game.getClass().getSimpleName() + "는 능력 추첨을 지원하지 않는 게임입니다.");
		} else Logger.error(game.getClass().getSimpleName() + "는 능력 추첨을 지원하지 않는 게임입니다.");
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}
}
