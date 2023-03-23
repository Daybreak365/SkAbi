package daybreak.abilitywar.skabi.expressions.game.team;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.game.team.TeamGame;
import daybreak.abilitywar.game.team.interfaces.Members;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpTeamOfParticipant extends SimpleExpression<Members> {
    public static final String pattern = "%participant%'s team";

    private Expression<Participant> participant;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        this.participant = (Expression<Participant>) expressions[0];
        return true;
    }

    @Override
    protected Members[] get(Event event) {
        final Participant participant = this.participant.getSingle(event);
        if (participant == null) {
            Logger.error("참가자가 null입니다.");
            return SkAbi.EMPTY_TEAM_ARRAY;
        }
        if (!participant.getGame().isRunning()) {
            Logger.error("게임이 실행 중이지 않습니다.");
            return SkAbi.EMPTY_TEAM_ARRAY;
        }
        if (!(participant.getGame() instanceof TeamGame)) {
            Logger.error("팀 기능을 지원하는 게임 모드가 아닙니다.");
            return SkAbi.EMPTY_TEAM_ARRAY;
        }
        return new Members[] {((TeamGame) participant.getGame()).getTeam(participant)};
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) {
            return CollectionUtils.array(Members.class);
        } else if (mode == ChangeMode.DELETE) {
            return SkAbi.EMPTY_CLASS_ARRAY;
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        final Participant participant = this.participant.getSingle(e);
        if (participant != null) {
            if (!(participant.getGame() instanceof TeamGame)) {
                Logger.error("팀 기능을 지원하는 게임 모드가 아닙니다.");
                return;
            }
            if (mode == ChangeMode.SET) {
                final Members team = (Members) delta[0];
                ((TeamGame) participant.getGame()).setTeam(participant, team);
            } else if (mode == ChangeMode.DELETE) {
                ((TeamGame) participant.getGame()).setTeam(participant, null);
            }
        } else {
            Logger.error("참가자가 null입니다.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return pattern;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Members> getReturnType() {
        return Members.class;
    }
}
