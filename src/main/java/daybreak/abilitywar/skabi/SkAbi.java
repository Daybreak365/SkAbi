package daybreak.abilitywar.skabi;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import daybreak.abilitywar.ability.AbilityBase;
import daybreak.abilitywar.addon.Addon;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.game.event.GameEndEvent;
import daybreak.abilitywar.game.event.GameReadyEvent;
import daybreak.abilitywar.game.event.GameStartEvent;
import daybreak.abilitywar.game.event.participant.ParticipantDeathEvent;
import daybreak.abilitywar.skabi.conditions.CondIsNull;
import daybreak.abilitywar.skabi.conditions.game.CondGameIsRunning;
import daybreak.abilitywar.skabi.conditions.game.CondHasAbility;
import daybreak.abilitywar.skabi.conditions.game.CondIsParticipating;
import daybreak.abilitywar.skabi.conditions.game.CondParticipantIsExcluded;
import daybreak.abilitywar.skabi.conditions.game.CondParticipantIsTargetable;
import daybreak.abilitywar.skabi.conditions.game.CondStartGame;
import daybreak.abilitywar.skabi.conditions.game.CondStopGame;
import daybreak.abilitywar.skabi.effects.EffReDrawAbilities;
import daybreak.abilitywar.skabi.effects.EffSpecPlayer;
import daybreak.abilitywar.skabi.effects.EffStartGame;
import daybreak.abilitywar.skabi.effects.EffStartInvincibility;
import daybreak.abilitywar.skabi.effects.EffStopGame;
import daybreak.abilitywar.skabi.effects.EffStopInvincibility;
import daybreak.abilitywar.skabi.events.EvtGameEnd;
import daybreak.abilitywar.skabi.events.EvtGameReady;
import daybreak.abilitywar.skabi.events.EvtGameStart;
import daybreak.abilitywar.skabi.events.EvtParticipantDeath;
import daybreak.abilitywar.skabi.expressions.game.ExpCurrentGame;
import daybreak.abilitywar.skabi.expressions.game.ExpGame;
import daybreak.abilitywar.skabi.expressions.game.ExpNameOfGame;
import daybreak.abilitywar.skabi.expressions.game.ExpParticipant;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpAbilityOfParticipant;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpAllAbilities;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpAllAbilitiesAvailable;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpNameOfAbility;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpRandomAbility;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpRandomAbilityAvailable;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpRankOfAbility;
import daybreak.abilitywar.skabi.expressions.game.ability.ExpSpeciesOfAbility;
import daybreak.abilitywar.skabi.expressions.game.participant.ExpParticipants;
import daybreak.abilitywar.skabi.expressions.game.participant.ExpSearchParticipant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SkAbi extends Addon {

    public static final AbilityBase[] EMPTY_ABILITY_ARRAY = new AbilityBase[0];
    public static final Participant[] EMPTY_PARTICIPANT_ARRAY = new Participant[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    public static final String prefix = "§2《§aSkAbi§2》§f";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "애드온이 활성화되었습니다. §8(§7v" + getDescription().getVersion() + "§8)");
        new BukkitRunnable() {
            @Override
            public void run() {
                Classes.registerClass(new ClassInfo<>(AbstractGame.class, "game")
                        .parser(new Parser<AbstractGame>() {
                            @Override
                            public String toString(AbstractGame abstractGame, int i) {
                                return abstractGame.getRegistration().getManifest().name();
                            }

                            @Override
                            public String toVariableNameString(AbstractGame abstractGame) {
                                return abstractGame.getRegistration().getManifest().name();
                            }
                        })
                        .user("games?")
                        .name("Game")
                );
                Classes.registerClass(new ClassInfo<>(Participant.class, "participant")
                        .parser(new Parser<Participant>() {
                            @Override
                            public String toString(Participant participant, int i) {
                                return participant.getPlayer().getName();
                            }

                            @Override
                            public String toVariableNameString(Participant participant) {
                                return participant.getPlayer().getName();
                            }
                        })
                        .user("participants?")
                        .name("Participant")
                );
                Classes.registerClass(new ClassInfo<>(AbilityBase.class, "ability")
                        .parser(new Parser<AbilityBase>() {
                            @Override
                            public String toString(AbilityBase abilityBase, int i) {
                                return abilityBase.getName();
                            }

                            @Override
                            public String toVariableNameString(AbilityBase abilityBase) {
                                return abilityBase.getParticipant().getPlayer().getName() + ":" + abilityBase.hashCode();
                            }
                        })
                        .user("abilit(y|ies)")
                        .name("Ability")
                );
                Skript.registerCondition(CondStartGame.class, CondStartGame.patterns);
                Skript.registerCondition(CondStopGame.class, CondStopGame.patterns);
                Skript.registerCondition(CondIsNull.class, CondIsNull.patterns);
                Skript.registerCondition(CondGameIsRunning.class, CondGameIsRunning.patterns);
                Skript.registerCondition(CondIsParticipating.class, CondIsParticipating.patterns);
                Skript.registerCondition(CondParticipantIsExcluded.class, CondParticipantIsExcluded.patterns);
                Skript.registerCondition(CondHasAbility.class, CondHasAbility.patterns);
                Skript.registerCondition(CondParticipantIsTargetable.class, CondParticipantIsTargetable.patterns);
                Skript.registerExpression(ExpCurrentGame.class, AbstractGame.class, ExpressionType.SIMPLE, ExpCurrentGame.pattern);
                Skript.registerExpression(ExpGame.class, AbstractGame.class, ExpressionType.SIMPLE, ExpGame.pattern);
                Skript.registerExpression(ExpParticipant.class, Participant.class, ExpressionType.SIMPLE, ExpParticipant.pattern);
                Skript.registerExpression(ExpNameOfGame.class, String.class, ExpressionType.PROPERTY, ExpNameOfGame.pattern);
                Skript.registerExpression(ExpParticipants.class, Participant.class, ExpressionType.PROPERTY, ExpParticipants.pattern);
                Skript.registerExpression(ExpSearchParticipant.class, Participant.class, ExpressionType.COMBINED, ExpSearchParticipant.pattern);
                Skript.registerExpression(ExpAbilityOfParticipant.class, AbilityBase.class, ExpressionType.PROPERTY, ExpAbilityOfParticipant.pattern);
                Skript.registerExpression(ExpNameOfAbility.class, String.class, ExpressionType.PROPERTY, ExpNameOfAbility.patterns);
                Skript.registerExpression(ExpRankOfAbility.class, String.class, ExpressionType.PROPERTY, ExpRankOfAbility.patterns);
                Skript.registerExpression(ExpSpeciesOfAbility.class, String.class, ExpressionType.PROPERTY, ExpSpeciesOfAbility.patterns);
                Skript.registerExpression(ExpRandomAbility.class, String.class, ExpressionType.SIMPLE, ExpRandomAbility.patterns);
                Skript.registerExpression(ExpAllAbilities.class, String.class, ExpressionType.SIMPLE, ExpAllAbilities.patterns);
                Skript.registerExpression(ExpAllAbilitiesAvailable.class, String.class, ExpressionType.PROPERTY, ExpAllAbilitiesAvailable.patterns);
                Skript.registerExpression(ExpRandomAbilityAvailable.class, String.class, ExpressionType.PROPERTY, ExpRandomAbilityAvailable.patterns);
                Skript.registerEffect(EffStartGame.class, EffStartGame.pattern);
                Skript.registerEffect(EffStopGame.class, EffStopGame.pattern);
                Skript.registerEffect(EffStartInvincibility.class, EffStartInvincibility.pattern);
                Skript.registerEffect(EffStopInvincibility.class, EffStopInvincibility.pattern);
                Skript.registerEffect(EffReDrawAbilities.class, EffReDrawAbilities.pattern);
                Skript.registerEffect(EffSpecPlayer.class, EffSpecPlayer.pattern);
                Skript.registerEvent("AbilityWar Game Ready", EvtGameReady.class, GameReadyEvent.class, EvtGameReady.pattern);
                Skript.registerEvent("AbilityWar Game Start", EvtGameStart.class, GameStartEvent.class, EvtGameStart.pattern);
                Skript.registerEvent("AbilityWar Game End", EvtGameEnd.class, GameEndEvent.class, EvtGameEnd.pattern);
                Skript.registerEvent("Participant Death", EvtParticipantDeath.class, ParticipantDeathEvent.class, EvtParticipantDeath.pattern);
                EventValues.registerEventValue(GameReadyEvent.class, AbstractGame.class, new Getter<AbstractGame, GameReadyEvent>() {
                    @Override
                    public AbstractGame get(GameReadyEvent e) {
                        return e.getGame();
                    }
                }, 0);
                EventValues.registerEventValue(GameStartEvent.class, AbstractGame.class, new Getter<AbstractGame, GameStartEvent>() {
                    @Override
                    public AbstractGame get(GameStartEvent e) {
                        return e.getGame();
                    }
                }, 0);
                EventValues.registerEventValue(GameEndEvent.class, AbstractGame.class, new Getter<AbstractGame, GameEndEvent>() {
                    @Override
                    public AbstractGame get(GameEndEvent e) {
                        return e.getGame();
                    }
                }, 0);
                EventValues.registerEventValue(ParticipantDeathEvent.class, Participant.class, new Getter<Participant, ParticipantDeathEvent>() {
                    @Override
                    public Participant get(ParticipantDeathEvent e) {
                        return e.getParticipant();
                    }
                }, 0);
                EventValues.registerEventValue(ParticipantDeathEvent.class, AbstractGame.class, new Getter<AbstractGame, ParticipantDeathEvent>() {
                    @Override
                    public AbstractGame get(ParticipantDeathEvent e) {
                        return e.getParticipant().getGame();
                    }
                }, 0);
                EventValues.registerEventValue(ParticipantDeathEvent.class, Player.class, new Getter<Player, ParticipantDeathEvent>() {
                    @Override
                    public Player get(ParticipantDeathEvent e) {
                        return e.getParticipant().getPlayer();
                    }
                }, 0);
                Bukkit.getConsoleSender().sendMessage(prefix + "모든 구문이 등록되었습니다.");
            }
        }.runTask(getPlugin());
    }

}
