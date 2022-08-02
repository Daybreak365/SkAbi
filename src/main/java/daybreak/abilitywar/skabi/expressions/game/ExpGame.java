package daybreak.abilitywar.skabi.expressions.game;

import ch.njol.skript.expressions.base.EventValueExpression;
import daybreak.abilitywar.game.AbstractGame;

public class ExpGame extends EventValueExpression<AbstractGame> {

	public static final String pattern = "[the ]game";

	public ExpGame() {
		super(AbstractGame.class);
	}

}
