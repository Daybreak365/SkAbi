package daybreak.abilitywar.skabi.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import org.bukkit.event.Event;

public class EvtGameStart extends SkriptEvent {

	public static final String pattern = "[a(w|bilitywar) ]game start";

	@Override
	public boolean init(Literal<?>[] literals, int i, ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean check(Event event) {
		return true;
	}

	@Override
	public String toString(Event event, boolean b) {
		return pattern;
	}
}
