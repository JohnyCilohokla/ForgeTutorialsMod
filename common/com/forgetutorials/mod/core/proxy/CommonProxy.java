package com.forgetutorials.mod.core.proxy;

import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.mod.mod.ForgeTutorialsMod;
import com.forgetutorials.mod.multientity.entites.MobHarvester;
import com.forgetutorials.multientity.InfernosMultiEntityType;

public class CommonProxy {

	public void initizeRendering() {

	}

	public void registerTileEntities() {
		InfernosRegisteryProxyEntity.INSTANCE.addMultiEntity(MobHarvester.TYPE_NAME, MobHarvester.class, InfernosMultiEntityType.STATIC_BASIC,
				ForgeTutorialsMod.tabs);
	}
}
