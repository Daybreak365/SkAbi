package daybreak.abilitywar.skabi.expressions.game.participant;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpNameOfParticipant extends SimpleExpression<String> {
    public static final String pattern = "%participant%'s name";

    private Expression<Participant> participant;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        this.participant = (Expression<Participant>) expressions[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        final Participant participant = this.participant.getSingle(event);
        if (participant == null) {
            Logger.error("참가자가 null입니다.");
            return SkAbi.EMPTY_STRING_ARRAY;
        }
        return new String[] {participant.getPlayer().getName()};
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
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
