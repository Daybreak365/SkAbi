package daybreak.abilitywar.skabi.expressions.game.team;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.game.team.interfaces.Members;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpTeamMembers extends SimpleExpression<Participant> {
    public static final String pattern = "%team%'s members";

    private Expression<Members> teamExp;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        this.teamExp = (Expression<Members>) expressions[0];
        return true;
    }

    @Override
    protected Participant[] get(Event event) {
        final Members members = teamExp.getSingle(event);
        if (members == null) {
            Logger.error("팀이 null입니다.");
            return SkAbi.EMPTY_PARTICIPANT_ARRAY;
        }
        return members.getMembers().toArray(new Participant[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Participant> getReturnType() {
        return Participant.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return pattern;
    }

}
