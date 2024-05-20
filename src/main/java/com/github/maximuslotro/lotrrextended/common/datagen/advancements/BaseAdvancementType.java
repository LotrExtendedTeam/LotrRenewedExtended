package com.github.maximuslotro.lotrrextended.common.datagen.advancements;

import java.util.function.Consumer;

import com.github.maximuslotro.lotrrextended.common.datagen.DataGenValueHolder;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class BaseAdvancementType implements Consumer<Consumer<Advancement>>{
	Consumer<Advancement> con;
	@Override
	public void accept(Consumer<Advancement> t) {
		con= t;
	}

	public abstract void generateAdvancements();

	public abstract String getFolder();

	public Advancement.Builder make(){
		return Advancement.Builder.advancement();
	}

	public BaseData data(Item icon, String name, boolean mainCat, FrameType frame, boolean showToast, boolean announceChat, boolean ishidden){
		return new BaseData(new ItemStack(icon), name, mainCat, frame, showToast, announceChat, ishidden);
	}

	public BaseData data(ItemStack icon, String name, boolean mainCat, FrameType frame, boolean showToast, boolean announceChat, boolean ishidden){
		return new BaseData(icon, name, mainCat, frame, showToast, announceChat, ishidden);
	}

	public Advancement build(BaseData data, Advancement.Builder builder){
		builder.display(data.makeInfo());
		return builder.save(con, getFolder()+"/"+data.name);
	}

	public class BaseData {
		private final String name;
		private String titleMain;
		private String descriptionMain;
		private final ItemStack icon;
		private final boolean mainCat;
		private final FrameType frame;
		private final boolean showToast;
		private final boolean announceChat;
		private final boolean hidden;
		private float x = 0.34F;
		private float y = 0.34F;
		
		public BaseData(ItemStack icon, String name, boolean mainCat, FrameType frame, boolean showToast, boolean announceChat, boolean ishidden) {
			this.icon = icon;
			this.name = name;
			this.mainCat = mainCat;
			this.frame = frame;
			this.showToast = showToast;
			this.announceChat = announceChat;
			this.hidden = ishidden;
		}

		public DisplayInfo makeInfo() {
			TranslationTextComponent titleI = new TranslationTextComponent("advancements."+getFolder()+"."+name+".title");
			TranslationTextComponent descriptionI = new TranslationTextComponent("advancements."+getFolder()+"."+name+".description");
			ResourceLocation backgroundI = null;
			if(mainCat) {
				backgroundI = new ResourceLocation("textures/gui/advancements/backgrounds/"+getFolder()+".png");
			}
			DisplayInfo info = new DisplayInfo(icon, titleI, descriptionI, backgroundI, frame, showToast, announceChat, hidden);
			if(x!=0.34F && y != 0.34F) {
				info.setLocation(x, y);
			}
			if(titleMain!=null) {
				DataGenValueHolder.advancementHolder.put("advancements."+getFolder()+"."+name+".title", titleMain);
			}
			if(descriptionMain!=null) {
				DataGenValueHolder.advancementHolder.put("advancements."+getFolder()+"."+name+".description", descriptionMain);
			}
			return info;
		}

		public void setLocation(float p_192292_1_, float p_192292_2_) {
			this.x = p_192292_1_;
			this.y = p_192292_2_;
		}

		public void setTitle(String title) {
			this.titleMain = title;
		}

		public void setDescription(String description) {
			this.descriptionMain = description;
		}
	}
}
