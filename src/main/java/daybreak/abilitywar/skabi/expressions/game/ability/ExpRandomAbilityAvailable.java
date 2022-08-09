package daybreak.abilitywar.skabi.expressions.game.ability;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.ability.AbilityFactory.AbilityRegistration;
import daybreak.abilitywar.ability.AbilityFactory.AbilityRegistration.Flag;
import daybreak.abilitywar.config.Configuration.Settings;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.manager.AbilityList;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import daybreak.abilitywar.utils.base.random.Random;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpRandomAbilityAvailable extends SimpleExpression<String> {
    public static final String[] patterns = {"random ability available from %game%"};

    private Expression<AbstractGame> abstractGame;
    private static final Random random = new Random();

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
        this.abstractGame = (Expression<AbstractGame>) expressions[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {
        final AbstractGame game = this.abstractGame.getSingle(event);
        if (game == null) {
            Logger.error("게임이 null입니다.");
            return SkAbi.EMPTY_STRING_ARRAY;
        }
        final List<String> abilities = new ArrayList<>();
        for (AbilityRegistration registration : AbilityList.values()) {
            if (!Settings.isBlacklisted(registration.getManifest().name()) && registration.isAvailable(game.getClass()) && (Settings.isUsingBetaAbility() || !registration.hasFlag(Flag.BETA))) {
                abilities.add(registration.getManifest().name());
            }
        }
        return new String[] {random.pick(abilities)};
    }

    @Override
    public String toString(Event event, boolean b) {
        return Arrays.toString(patterns);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
