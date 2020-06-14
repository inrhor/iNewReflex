package cn.mcres.iNewReflex.mythicmobs;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.imiPet.api.model.ModelEntityManager;
import org.bukkit.entity.Entity;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.mechanics.CustomMechanic;
import org.bukkit.metadata.FixedMetadataValue;

public class ModelMechanic extends SkillMechanic implements ITargetedEntitySkill{

	private String modelId;
	private int attackTime;
	
	public ModelMechanic(CustomMechanic holder, MythicLineConfig mythicLineConfig) {
		super(holder.getConfigLine(), mythicLineConfig);
		
		setAsyncSafe(false);

		String string = mythicLineConfig.getString(new String[] {"model"});
		String[] strings = string.split("@");
		
		modelId = strings[0];
		attackTime = Integer.parseInt(strings[1]);
//		attackTime = mythicLineConfig.getInteger(new String[] {"time"}, 3);
//		addon = mlc.getBoolean(new String[] {"add", "addon"}, false);
		
	}

	@Override
	public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
		Entity entity = target.getBukkitEntity();
		ModelEntityManager modelEntityManager = new ModelEntityManager(entity, this.modelId);
		ModelEntityManager.fastSpawnModel(modelEntityManager, this.modelId);
		entity.setMetadata("iNewReflex:mob", new FixedMetadataValue(INewReflex.getMain(), modelEntityManager));
		entity.setMetadata("iNewReflex:attackTime", new FixedMetadataValue(INewReflex.getMain(), this.attackTime));
		return true;
	}

}
