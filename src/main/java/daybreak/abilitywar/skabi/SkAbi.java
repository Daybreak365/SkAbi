package daybreak.abilitywar.skabi;

import daybreak.abilitywar.ability.AbilityBase;
import daybreak.abilitywar.addon.Addon;
import daybreak.abilitywar.game.AbstractGame.Participant;
import daybreak.abilitywar.game.team.interfaces.Members;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SkAbi extends Addon {

    public static final AbilityBase[] EMPTY_ABILITY_ARRAY = new AbilityBase[0];
    public static final Player[] EMPTY_PLAYER_ARRAY = new Player[0];
    public static final Participant[] EMPTY_PARTICIPANT_ARRAY = new Participant[0];
    public static final Members[] EMPTY_TEAM_ARRAY = new Members[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    public static final String prefix = "§2《§aSkAbi§2》§f";

    @Override
    public void onEnable() {
        final Plugin skriptPlugin = Bukkit.getPluginManager().getPlugin("Skript");
        if (skriptPlugin == null) {
            Bukkit.getConsoleSender().sendMessage(prefix + "Skript 플러그인이 설치되지 않았습니다.");
            return;
        }
        Bukkit.getConsoleSender().sendMessage(prefix + "Skript v" + skriptPlugin.getDescription().getVersion() + " 설치 확인.");
        Bukkit.getConsoleSender().sendMessage(prefix + "애드온이 활성화되었습니다. §8(§7v" + getDescription().getVersion() + "§8)");
        Registerer.register();
        Bukkit.getConsoleSender().sendMessage(prefix + "모든 구문이 등록되었습니다.");
    }

}
