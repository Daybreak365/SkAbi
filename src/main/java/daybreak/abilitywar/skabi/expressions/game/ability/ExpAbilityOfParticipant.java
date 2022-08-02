package daybreak.abilitywar.skabi.expressions.game.ability;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import daybreak.abilitywar.ability.AbilityBase;
import daybreak.abilitywar.ability.AbilityFactory;
import daybreak.abilitywar.ability.AbilityFactory.AbilityRegistration;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.game.manager.AbilityList;
import daybreak.abilitywar.skabi.SkAbi;
import daybreak.abilitywar.skabi.utils.Logger;
import org.bukkit.event.Event;

public class ExpAbilityOfParticipant extends SimpleExpression<AbilityBase> {
	public static final String pattern = "%participant%'s ability";

	private Expression<Participant> participant;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult) {
		this.participant = (Expression<Participant>) expressions[0];
		return true;
	}

	@Override
	protected AbilityBase[] get(Event event) {
		final Participant participant = this.participant.getSingle(event);
		if (participant == null) {
			Logger.error("참가자가 null입니다.");
			return SkAbi.EMPTY_ABILITY_ARRAY;
		}
		if (!participant.getGame().isRunning()) {
			Logger.error("게임이 실행 중이지 않습니다.");
			return SkAbi.EMPTY_ABILITY_ARRAY;
		}
		return new AbilityBase[] {participant.getAbility()};
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(String.class);
		} else if (mode == ChangeMode.DELETE) {
			return SkAbi.EMPTY_CLASS_ARRAY;
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		final Participant participant = this.participant.getSingle(e);
		if (participant != null) {
			if (mode == ChangeMode.SET) {
				final String abilityName = (String) delta[0];
				if (!AbilityList.isRegistered(abilityName)) {
					Logger.error("존재하지 않는 능력입니다: " + abilityName);
					return;
				}
				final AbilityRegistration registration = AbilityFactory.getRegistration(AbilityList.getByString(abilityName));
				if (registration != null) {
					try {
						participant.setAbility(registration.getAbilityClass());
					} catch (Exception ex) {
						Logger.error(registration.getAbilityClass().getSimpleName() + " 능력 부여 중 오류가 발생했습니다.");
						ex.printStackTrace();
					}
				} else {
					Logger.error("존재하지 않는 능력입니다: " + abilityName);
				}
			} else if (mode == ChangeMode.DELETE) {
				participant.removeAbility();
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
	public Class<? extends AbilityBase> getReturnType() {
		return AbilityBase.class;
	}
}
