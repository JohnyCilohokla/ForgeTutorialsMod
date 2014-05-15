package com.forgetutorials.mod.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Tabs extends CreativeTabs {

	public Tabs() {
		super("ForgeTutorialsMod");
	}

	@Override
	public ItemStack getIconItemStack() {
		return null;
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
