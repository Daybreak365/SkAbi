package daybreak.abilitywar.skabi.expressions.game;

import ch.njol.skript.expressions.base.EventValueExpression;
import daybreak.abilitywar.game.AbstractGame.Participant;

public class ExpParticipant extends EventValueExpression<Participant> {

	public static final String pattern = "[the ]participant";

	public ExpParticipant() {
		super(Participant.class);
	}

}
