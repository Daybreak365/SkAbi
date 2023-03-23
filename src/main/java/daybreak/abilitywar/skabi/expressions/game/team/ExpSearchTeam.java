package daybreak.abilitywar.skabi.expressions.game.team;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.team.TeamGame;
import daybreak.abilitywar.game.team.interfaces.Members;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpSearchTeam extends SimpleExpression<Members> {
    public static final String pattern = "team %string% of %game%";


    private Expression<String> teamName;
    private Expression<AbstractGame> abstractGame;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        this.teamName = (Expression<String>) expressions[0];
        this.abstractGame = (Expression<AbstractGame>) expressions[1];
        return true;
    }

    @Override
    protected Members[] get(Event event) {
        final AbstractGame game = abstractGame.getSingle(event);
        if (game == null) {
            Logger.error("게임이 null입니다.");
            return SkAbi.EMPTY_TEAM_ARRAY;
        }
        if (!game.isRunning()) {
            Logger.error("게임이 실행 중이지 않습니다.");
            return SkAbi.EMPTY_TEAM_ARRAY;
        }
        if (!(game instanceof TeamGame)) {
            Logger.error("팀 기능을 지원하는 게임 모드가 아닙니다.");
            return SkAbi.EMPTY_TEAM_ARRAY;
        }
        return new Members[] {((TeamGame) game).getTeam(teamName.getSingle(event))};
    }

    @Override
    public Class<? extends Members> getReturnType() {
        return Members.class;
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
