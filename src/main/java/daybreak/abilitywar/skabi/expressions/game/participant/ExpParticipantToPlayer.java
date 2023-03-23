package daybreak.abilitywar.skabi.expressions.game.participant;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExpParticipantToPlayer extends SimpleExpression<Player> {
    public static final String pattern = "player %participant%";

    private Expression<Participant> participant;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        this.participant = (Expression<Participant>) expressions[0];
        return true;
    }

    @Override
    protected Player[] get(Event event) {
        final Participant participant = this.participant.getSingle(event);
        if (participant == null) {
            Logger.error("참가자가 null입니다.");
            return SkAbi.EMPTY_PLAYER_ARRAY;
        }
        return new Player[] {participant.getPlayer()};
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return pattern;
    }

}
