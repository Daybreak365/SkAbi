package daybreak.abilitywar.skabi;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import daybreak.abilitywar.ability.AbilityBase;
import daybreak.abilitywar.config.serializable.team.TeamPreset;
import daybreak.abilitywar.game.AbstractGame;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.game.event.GameEndEvent;
import daybreak.abilitywar.game.event.GameReadyEvent;
import daybreak.abilitywar.game.event.GameStartEvent;
import daybreak.abilitywar.game.event.GameWinEvent;
import daybreak.abilitywar.game.event.GameWinEvent.Winner;
import daybreak.abilitywar.game.event.participant.ParticipantDeathEvent;
import daybreak.abilitywar.game.team.interfaces.Members;
import daybreak.abilitywar.skabi.conditions.CondIsNull;
import daybreak.abilitywar.skabi.conditions.game.CondGameIsRunning;
import daybreak.abilitywar.skabi.conditions.game.CondHasAbility;
import daybreak.abilitywar.skabi.conditions.game.CondIsParticipating;
import daybreak.abilitywar.skabi.conditions.game.CondIsTeamGame;
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
import daybreak.abilitywar.skabi.events.EvtGameWin;
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
import daybreak.abilitywar.skabi.expressions.game.participant.ExpNameOfParticipant;
import daybreak.abilitywar.skabi.expressions.game.participant.ExpParticipantToPlayer;
import daybreak.abilitywar.skabi.expressions.game.participant.ExpParticipants;
import daybreak.abilitywar.skabi.expressions.game.participant.ExpSearchParticipant;
import daybreak.abilitywar.skabi.expressions.game.team.ExpSearchTeam;
import daybreak.abilitywar.skabi.expressions.game.team.ExpTeamMembers;
import daybreak.abilitywar.skabi.expressions.game.team.ExpTeamOfParticipant;
import org.bukkit.entity.Player;

class Registerer {

    static void register() {
        Classes.registerClass(new ClassInfo<>(AbstractGame.class, "game")
                .parser(new Parser<AbstractGame>() {
                    @Override
                    public AbstractGame parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
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
                    public Participant parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
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
                    public AbilityBase parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
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
        Classes.registerClass(new ClassInfo<>(TeamPreset.class, "teampreset")
                .parser(new Parser<TeamPreset>() {
                    @Override
                    public TeamPreset parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
                    @Override
                    public String toString(TeamPreset teamPreset, int i) {
                        return teamPreset.getName();
                    }

                    @Override
                    public String toVariableNameString(TeamPreset teamPreset) {
                        return teamPreset.getName();
                    }
                })
                .user("teampresets?")
                .name("TeamPreset")
        );
        Classes.registerClass(new ClassInfo<>(Members.class, "team")
                .parser(new Parser<Members>() {
                    @Override
                    public Members parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
                    @Override
                    public String toString(Members team, int i) {
                        return team.getName();
                    }

                    @Override
                    public String toVariableNameString(Members team) {
                        return team.getName();
                    }
                })
                .user("teams?")
                .name("Team")
        );
        Classes.registerClass(new ClassInfo<>(Winner.class, "winner")
                .parser(new Parser<Winner>() {
                    @Override
                    public Winner parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(Winner winner, int i) {
                        return String.join(", ", winner.getWinnerNames());
                    }

                    @Override
                    public String toVariableNameString(Winner winner) {
                        return String.join(", ", winner.getWinnerNames());
                    }
                })
                .user("winners?")
                .name("Winner")
        );
        Skript.registerCondition(CondStartGame.class, CondStartGame.patterns);
        Skript.registerCondition(CondStopGame.class, CondStopGame.patterns);
        Skript.registerCondition(CondIsNull.class, CondIsNull.patterns);
        Skript.registerCondition(CondGameIsRunning.class, CondGameIsRunning.patterns);
        Skript.registerCondition(CondIsParticipating.class, CondIsParticipating.patterns);
        Skript.registerCondition(CondParticipantIsExcluded.class, CondParticipantIsExcluded.patterns);
        Skript.registerCondition(CondHasAbility.class, CondHasAbility.patterns);
        Skript.registerCondition(CondParticipantIsTargetable.class, CondParticipantIsTargetable.patterns);

        Skript.registerCondition(CondIsTeamGame.class, CondIsTeamGame.patterns);

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

        Skript.registerExpression(ExpNameOfParticipant.class, String.class, ExpressionType.SIMPLE, ExpNameOfParticipant.pattern);
        Skript.registerExpression(ExpParticipantToPlayer.class, Player.class, ExpressionType.SIMPLE, ExpParticipantToPlayer.pattern);
        Skript.registerExpression(ExpSearchTeam.class, Members.class, ExpressionType.COMBINED, ExpSearchTeam.pattern);
        Skript.registerExpression(ExpTeamMembers.class, Participant.class, ExpressionType.SIMPLE, ExpTeamMembers.pattern);
        Skript.registerExpression(ExpTeamOfParticipant.class, Members.class, ExpressionType.PROPERTY, ExpTeamOfParticipant.pattern);

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
        Skript.registerEvent("Game Win", EvtGameWin.class, GameWinEvent.class, EvtGameWin.pattern);
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
        EventValues.registerEventValue(GameWinEvent.class, AbstractGame.class, new Getter<AbstractGame, GameWinEvent>() {
            @Override
            public AbstractGame get(GameWinEvent e) {
                return e.getGame();
            }
        }, 0);
        EventValues.registerEventValue(GameWinEvent.class, Winner.class, new Getter<Winner, GameWinEvent>() {
            @Override
            public Winner get(GameWinEvent e) {
                return e.getWinner();
            }
        }, 0);
    }

}
