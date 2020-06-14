package cn.mcres.iNewReflex.mobsBook;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.fileYaml.mobsBook.CreateMobsBookYaml;
import cn.mcres.iNewReflex.utils.ScriptUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class MobsBookManager implements Listener {
    public static INewReflex getMain() {
        return INewReflex.getMain();
    }

    private static int checkTime, checkRange, msgStayTime;

    public static String notFine, isFine;

    private static boolean msgEnable;
    private static String msgMain, msgSub;

    public static HashMap<String, MobsBookData> mobDataMap = new LinkedHashMap<>();
    public static List<MobsBookData> mobDataList = new ArrayList<>();

    public static void load() {
        FileConfiguration mobsBookYaml = CreateMobsBookYaml.mobsBookYaml;

        checkTime = mobsBookYaml.getInt("mobsBook.checkTime")*20;
        checkRange = mobsBookYaml.getInt("mobsBook.checkRange");

        notFine = mobsBookYaml.getString("mobsBook.fineTag.notFine").replaceAll("&", "§");
        isFine = mobsBookYaml.getString("mobsBook.fineTag.isFine").replaceAll("&", "§");

        msgEnable = mobsBookYaml.getBoolean("mobsBook.msg.enable");
        if (msgEnable) {
            msgMain = mobsBookYaml.getString("mobsBook.msg.mainMsg").replaceAll("&", "§");
            msgSub = mobsBookYaml.getString("mobsBook.msg.subMsg").replaceAll("&", "§");
            msgStayTime = mobsBookYaml.getInt("mobsBook.msg.stayTime") * 20;
        }

        for (String modelId : mobsBookYaml.getConfigurationSection("mobsBook.list").getKeys(false)) {
            if (modelId.contains(":")) continue;
            MobsBookData mobsBookData = new MobsBookData(modelId, mobsBookYaml);
            mobDataMap.put(modelId, mobsBookData);
            mobDataList.add(mobsBookData);
        }
    }

    @EventHandler
    void clickEntity(PlayerInteractAtEntityEvent ev) {
        Entity entity = ev.getRightClicked();
        Player player = ev.getPlayer();
        if (!entity.hasMetadata("imipet:modelId")) return;
        String modelId = entity.getMetadata("imipet:modelId").get(0).asString();
        if (mobDataMap.get(modelId) == null) return;
        if (player.hasPermission("inewrx.mobsbook."+modelId)) return;
        MobsBookData mobsBookData = mobDataMap.get(modelId);
        String mobName = mobsBookData.getMobName();
        String mainMsg = msgMain.replaceAll("@inewrx_mobName", mobName);
        String subMsg = msgSub.replaceAll("@inewrx_mobName", mobName);
        if (msgEnable) {
            player.sendTitle(mainMsg, subMsg, 0, msgStayTime, 0);
        }
        List<String> scriptList = mobsBookData.getScript();
        ScriptUtil.run(scriptList, player);
    }
}
