package daybreak.abilitywar.skabi.expressions.game.ability;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.game.manager.AbilityList;
import org.bukkit.event.Event;

import java.util.Arrays;

public class ExpAllAbilities extends SimpleExpression<String> {
    public static final String[] patterns = {"all abilities"};

    private String[] names;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        return true;
    }

    @Override
    protected String[] get(Event event) {
        if (names == null || names.length != AbilityList.values().size()) {
            this.names = AbilityList.nameValues().toArray(new String[0]);
        }
        return names;
    }

    @Override
    public String toString(Event event, boolean b) {
        return Arrays.toString(patterns);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
