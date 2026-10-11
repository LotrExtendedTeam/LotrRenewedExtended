package com.github.maximuslotro.lotrrextended.client.gui.widget;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractOptionList;

public abstract class ExtendedScrollList<E extends AbstractOptionList.Entry<E>> extends AbstractOptionList<E> {

	public ExtendedScrollList(Minecraft pMinecraft, int pWindowHeight, int pX0, int pScrollPlaneWidth, int pY0, int pY1, int pItemHeight) {
		super(pMinecraft, pScrollPlaneWidth, pWindowHeight, pY0, pY1, pItemHeight);
		setLeftPos(pX0);
	}

	@Override
	public int getRowWidth() {
		return this.width;
	}

	@Override
	protected int getScrollbarPosition() {
		return this.x1 - 6;
	}

	@Override
	protected boolean removeEntry(E pEntry) {
		boolean flag = this.children().remove(pEntry);
		if(flag) {
			if(pEntry == this.getSelected()) {
				this.setSelected((E)null);
			}
			// Fix: Clamp scroll amount immediately after removing an entry
			this.setScrollAmount(this.getScrollAmount());
		}
		return flag;
	}

	@Override
	protected E remove(int pIndex) {
		// Handle changed list size
		if (pIndex < 0 || pIndex >= this.children().size()) {
			return null;
		}
		E e = this.children().get(pIndex);
		return (E)(this.removeEntry(e) ? e : null);
	}

	@Override
	protected void replaceEntries(Collection<E> pEntries) {
		this.children().clear();
		this.children().addAll(pEntries);
		this.setSelected((E)null);
		this.setScrollAmount(0.0D); // Reset scroll on bulk replacement
	}
}
