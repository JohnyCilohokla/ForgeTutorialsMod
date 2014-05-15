package com.forgetutorials.mod.multientity.entites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.lib.renderers.BlockTessallator;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.lib.renderers.VertexRenderer;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class MobHarvester extends InfernosProxyEntityBase {

	public final static String TYPE_NAME = "ftm.MobHarvester";

	@Override
	public String getTypeName() {
		return MobHarvester.TYPE_NAME;
	}

	@Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public boolean hasLiquids() {
		return false;
	}

	@Override
	public boolean isDynamiclyRendered() {
		return false;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(getSilkTouchItemStack());
		return droppedItems;
	}

	public MobHarvester(InfernosMultiEntityStatic entity) {
		super(entity);
		ArrayList<Pair<ItemStack, Double>> loot = new ArrayList<Pair<ItemStack, Double>>();
		loot.add(Pair.of(new ItemStack(Items.arrow, 1), 0.01));
		this.lootMap.put("net.minecraft.entity.monster.EntitySkeleton", loot);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	}

	static long i = 0;

	@Override
	public void addToDescriptionPacket(PacketMultiTileEntity packet) {
		MobHarvester.i++;
		System.out.println(MobHarvester.i);
	}

	static GLDisplayList frameBoxList[] = new GLDisplayList[7];

	@Override
	public void renderTileEntityAt(double x, double y, double z) {
		/*
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		int facing = this.entity.getFacingInt();
		if (this.frameBoxList[facing] == null) {
			this.frameBoxList[facing] = new GLDisplayList();
		}
		if (!this.frameBoxList[facing].isGenerated()) {
			this.frameBoxList[facing].generate();
			this.frameBoxList[facing].bind();
			MetaTechCraftModels.squareFrame.render(MetaTechCraftModels.boxFrameTexture, facing == 6 ? null : EnumFacing.getFront(facing));
			this.frameBoxList[facing].unbind();
		}
		this.frameBoxList[facing].render();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		*/
	}

	@Override
	public void onBlockPlaced(World world, EntityPlayer player, int side, int direction, int x, int y, int z, float hitX, float hitY, float hitZ, int metadata) {
	}

	static VertexRenderer v = new VertexRenderer();

	@Override
	public void renderStaticBlockAt(RenderBlocks blockRenderer, int x, int y, int z) {

		/*int i = this.entity.getWorldObj().getLightBrightnessForSkyBlocks(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord, 0);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		*/
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		BlockTessallator.enableOverlay();

		IIcon icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon("MetaTechCraft".toLowerCase() + ":overlay/creeper");
		BlockTessallator.addToRenderer(this.getBlock(), MobHarvester.v, blockRenderer, (x % 16) < 0 ? (16 + x % 16) : (x % 16), (y % 16) < 0 ? (16 + y % 16)
				: (y % 16), (z % 16) < 0 ? (16 + z % 16) : (z % 16), icon, Blocks.lava.getIcon(0, 0), x, y, z);

		MobHarvester.v.render();

		BlockTessallator.disableOverlay();
	}

	HashMap<String, List<Pair<ItemStack, Double>>> lootMap = new HashMap<String, List<Pair<ItemStack, Double>>>();


	@Override
	public void tick() {
		if (this.entity.getWorldObj().isRemote) {
			return;
		}
		List<?> list = this.entity.getWorldObj().getEntitiesWithinAABBExcludingEntity((Entity) null,//
				AxisAlignedBB.getAABBPool().getAABB(//
						this.entity.xCoord, this.entity.yCoord + 1.0D, this.entity.zCoord,//
						this.entity.xCoord + 1.0D, this.entity.yCoord + 2.0D, this.entity.zCoord + 1.0D)//
				, IEntitySelector.selectAnything);//
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			Object next = it.next();
			if (next instanceof Entity) {
				Entity enitity = (Entity) next;
				if (enitity instanceof EntityCreature) {
					EntityCreature mob = (EntityCreature) enitity;
					if (0.1 > Math.random()) {
						EntityHelper.dropFewItems(mob, true, 0, true);
						mob.setHealth(mob.getHealth() - 1);
					}
					/*String enitityName = mob.getClass().getCanonicalName();
					if (lootMap.containsKey(enitityName)){
						Iterator<Pair<ItemStack,Double>> itLoot = lootMap.get(enitityName).iterator();
						while (itLoot.hasNext()){
							Pair<ItemStack,Double> nextLoot = itLoot.next();
							if (nextLoot.getValue()>Math.random()){
							ItemUtilities.dropItem(this.entity.worldObj, this.entity.xCoord + 0.5D, this.entity.yCoord + 1.1D, this.entity.zCoord + 0.5D, nextLoot.getKey().copy());
							}
						}
					}*/
				}
			}
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object[] data) {
		IIcon icon = InfernosRegisteryProxyEntity.INSTANCE.getIcon("MetaTechCraft".toLowerCase() + ":overlay/creeper");
		IIcon icon2 = Blocks.lava.getIcon(0, 0);
		GL11.glPushMatrix();
		BlockTessallator.renderDualTextureBlockAsItem(v, this.getBlock(), type, icon, icon2, data);
		GL11.glPopMatrix();
	}

}