package com.tomorrow.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class HpBar extends Widget implements Disableable {
	
	ProgressBarStyle style = UiShipHpProgressBarStyle.style;
	boolean disabled;
	int maxHp, hp;
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();
		
		Drawable bg = style.background;
		Drawable knob = style.knob;
		
		if (bg != null) {
			bg.draw(batch, x, y, width, height);
		}
		
		if (knob != null) {
			knob.draw(batch, x + bg.getLeftWidth(), y + bg.getTopHeight(), (width - bg.getLeftWidth() - bg.getRightWidth()) * hp / maxHp, height - bg.getBottomHeight() - bg.getTopHeight());
		}
		
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public boolean isDisabled() {
		return disabled;
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public HpBar(int hp, int maxHp) {
		this.hp = hp;
		this.maxHp = maxHp;
	}

}
