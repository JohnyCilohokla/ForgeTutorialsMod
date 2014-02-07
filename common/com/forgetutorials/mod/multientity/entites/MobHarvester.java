package com.forgetutorials.mod.multientity.entites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.renderers.BlockTessallator;
import com.forgetutorials.lib.renderers.GLDisplayList;
import com.forgetutorials.lib.renderers.VertexRenderer;
import com.forgetutorials.lib.utilities.ItemStackUtilities;
import com.forgetutorials.lib.utilities.ItemUtilities;
import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.forgetutorials.multientity.renderers.InfernosMultiItemRenderer;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
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
	public ItemStack getSilkTouchItemStack() {
		ItemStack stack = new ItemStack(FTA.infernosMultiBlock, 1, 3);
		ItemStackUtilities.addStringTag(stack, "MES", getTypeName());
		return stack;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		droppedItems.add(getSilkTouchItemStack());
		return droppedItems;
	}

	public MobHarvester(InfernosMultiEntity entity) {
		super(entity);
		ArrayList<Pair<ItemStack,Double>> loot = new ArrayList<Pair<ItemStack,Double>>();
		loot.add(Pair.of(new ItemStack(Item.arrow,1),0.01));
		lootMap.put("net.minecraft.entity.monster.EntitySkeleton", loot);
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

	@Override
	public void addToDescriptionPacket(PacketMultiTileEntity packet) {
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

	static boolean SWITCH = false;

	public void enableOverlay() {
		OpenGlHelper.setActiveTexture(33986);
		if (!MobHarvester.SWITCH) {
			MobHarvester.SWITCH = true;
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL13.GL_COMBINE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_RGB, GL13.GL_INTERPOLATE);
			
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_RGB, GL13.GL_PREVIOUS);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
			
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE1_RGB, GL11.GL_TEXTURE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);

			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE2_RGB, GL13.GL_PREVIOUS);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_ALPHA, GL11.GL_ADD);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_ALPHA, GL11.GL_TEXTURE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

	}

	public void disableOverlay() {
		OpenGlHelper.setActiveTexture(33986);

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	static VertexRenderer v = new VertexRenderer();

	@Override
	public void renderStaticBlockAt(RenderBlocks blockRenderer, int x, int y, int z) {
		System.out.println("renderStaticBlockAt");

		Tessellator tessellator = Tessellator.instance;
		// tessellator.draw();
		/*if (this.frameBoxList[facing] == null) {
			this.frameBoxList[facing] = new GLDisplayList();
		}
		if (!this.frameBoxList[facing].isGenerated()) {
			this.frameBoxList[facing].generate();
			this.frameBoxList[facing].bind();
			MetaTechCraftModels.squareFrame.render(MetaTechCraftModels.boxFrameTexture, facing == 6 ? null : EnumFacing.getFront(facing));
			this.frameBoxList[facing].unbind();
		}*/
		// GL11.glPushMatrix();
		// GL11.glPushAttrib(GL11.GL_ENABLE_BIT); // very evil
		// GL11.glDisable(GL11.GL_LIGHTING);
		// GL11.glTranslated(x%16, y%16, z%16);
		// if (frameBoxList[facing] != null) {
		// frameBoxList[facing].render();
		// }
		// GL11.glEnable(GL11.GL_LIGHTING);
		// GL11.glPopAttrib(); // very evil

		// GL11.glPushMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		// GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

		enableOverlay();

		// Tessellator.instance.startDrawingQuads();
		// BlockTessallator.addToTessallator(Tessellator.instance, x, y, z,
		// Block.cactus.getIcon(0, 0));
		// Tessellator.instance.draw();

		BlockTessallator.addToRenderer(FTA.infernosMultiBlock, MobHarvester.v, blockRenderer, x + tessellator.xOffset, y + tessellator.yOffset, z + tessellator.zOffset,
				Block.leaves.getIcon(0, 0), Block.lavaMoving.getIcon(0, 0),x,y,z);

		// System.out.println(
		MobHarvester.v.render();
		// );

		/*v.addQuadUV(
				varX1, varY1, varZ1, varU1, varV1,
				varX2, varY2, varZ2, varU2, varV2,
				varX3, varY3, varZ3, varU3, varV3,
				varX4, varY4, varZ4, varU4, varV4);*/

		// GL11.glPopAttrib();
		// GL11.glPopMatrix();

		// GL11.glPopMatrix();

		// Clean Up

		// FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		// GL11.glDepthMask(true);
		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_ALPHA_TEST);
		// GL11.glEnable(GL11.GL_FOG);
		// GL11.glDisable(GL11.GL_LIGHTING);

		disableOverlay();

		// Tessellator.instance.startDrawingQuads();
	}
	
	HashMap<String,List<Pair<ItemStack,Double>>> lootMap = new HashMap<>();

	@Override
	public void tick() {
		if (this.entity.worldObj.isRemote){
			return;
		}
		List<?> list = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null,//
				AxisAlignedBB.getAABBPool().getAABB(//
						this.entity.xCoord, this.entity.yCoord + 1.0D, this.entity.zCoord,//
						this.entity.xCoord + 1.0D, this.entity.yCoord + 2.0D, this.entity.zCoord + 1.0D)//
				, IEntitySelector.selectAnything);//
		Iterator<?> it = list.iterator();
		while (it.hasNext()){
			Object next = it.next();
			if (next instanceof Entity){
				Entity enitity = (Entity) next;
				if (enitity instanceof EntityMob){
					EntityMob mob = (EntityMob) enitity;
					if (0.1>Math.random()){
						EntityHelper.dropFewItems(mob, true, 0, true);
						mob.setHealth(mob.getHealth()-1);
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
	public void renderItem(ItemRenderType type, Object[] data) {

		//System.out.println("renderItem");
		GL11.glPushMatrix();
		int facing = this.entity.getFacingInt();

		GL11.glPushMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		if (MobHarvester.frameBoxList[facing] == null) {
			MobHarvester.frameBoxList[facing] = new GLDisplayList();
		}
		if (!MobHarvester.frameBoxList[facing].isGenerated()) {
		MobHarvester.frameBoxList[facing].delete();
			MobHarvester.frameBoxList[facing].generate();
			MobHarvester.frameBoxList[facing].bind();

			enableOverlay();
			BlockTessallator.addToRenderer(FTA.infernosMultiBlock,MobHarvester.v, null, 0, 0, 0, Block.leaves.getIcon(0, 0), Block.lavaMoving.getIcon(0, 0),0,0,0);

			InfernosMultiItemRenderer.disableLightmap(); // no idea... don't ask...
			MobHarvester.v.render();
			GL11.glPopMatrix();

			disableOverlay();

			MobHarvester.frameBoxList[facing].unbind();
		}
		
		
		if (type.equals(ItemRenderType.INVENTORY))
        GL11.glDisable(GL11.GL_LIGHTING); // no idea... don't ask...
		MobHarvester.frameBoxList[facing].render();

		GL11.glPopMatrix();

	}

}