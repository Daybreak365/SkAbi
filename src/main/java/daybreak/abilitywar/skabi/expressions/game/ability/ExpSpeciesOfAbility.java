package daybreak.abilitywar.skabi.expressions.game.ability;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import daybreak.abilitywar.ability.AbilityBase;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import java.util.Arrays;
import org.bukkit.event.Event;

public class ExpSpeciesOfAbility extends SimpleExpression<String> {
	public static final String[] patterns = {"%ability%'s species", "species of %ability%", "%ability%'s species name", "species name of %ability%"};

	private Expression<AbilityBase> ability;
	private boolean name;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
		this.ability = (Expression<AbilityBase>) expressions[0];
		this.name = matchedPattern >= 2;
		return true;
	}

	@Override
	protected String[] get(Event event) {
		final AbilityBase ability = this.ability.getSingle(event);
		if (ability == null) {
			Logger.error("능력이 null입니다.");
			return SkAbi.EMPTY_STRING_ARRAY;
		}
		return new String[] {name ? ability.getSpecies().getSpeciesName() : ability.getSpecies().name()};
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
