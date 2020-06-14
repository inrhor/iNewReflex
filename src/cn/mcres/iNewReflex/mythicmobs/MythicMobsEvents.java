package cn.mcres.iNewReflex.mythicmobs;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.load.CheckFeatures;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicConditionLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MythicMobsEvents implements Listener{

	@EventHandler
	void onMythicAttack(EntityDamageByEntityEvent ev) {
		if (!CheckFeatures.openMMAttack) return;
		Entity attacker = ev.getDamager();
		String metadata = "iNewReflex:mob";
		if (attacker.hasMetadata("iNewReflex:mob")) {
			ModelEntityManager modelEntityManager = (ModelEntityManager) attacker.getMetadata(metadata).get(0).value();
			int attackTime = attacker.getMetadata("iNewReflex:attackTime").get(0).asInt();
			modelEntityManager.setState("attack");
			Bukkit.getScheduler().runTaskLater(INewReflex.getMain(), () -> {
				if (!attacker.isDead()) {
					modelEntityManager.setState("idle");
				}
			}, attackTime*20);
		}
	}

	@EventHandler
	public void onMythicMechanicLoad(MythicMechanicLoadEvent e) {
		SkillMechanic m;
		if ("inewrxmodel".equals(e.getMechanicName().toLowerCase())) {
			m = new ModelMechanic(e.getContainer(), e.getConfig());
			e.register(m);
				/*case "state":
			m = new ModelStateMechanic(e.getContainer(), e.getConfig());
			e.register(m);
			break;
		}*/
		}
	}

/*	@EventHandler
	public void onMythicConditionLoad(MythicConditionLoadEvent e) {
		SkillCondition m;
		if ("inewrxattack".equals(e.getConditionName().toUpperCase())) {
//			m = new ModelStateMechanic(e.getContainer(), e.getConfig());
//			e.register(m);
		}

	}*/
	
	/*@EventHandler
	public void onMythicConditionLoad(MythicConditionLoadEvent e) {
		SkillCondition m;
		if ("STATE".equals(e.getConditionName().toUpperCase())) {
			m = new ModelStateCondition(e.getContainer().getConditionArgument(), e.getConfig());
			e.register(m);
		}
			
	}*/
	
}
