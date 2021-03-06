package com.tomorrow.UI.items.loot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tomorrow.UI.UiFactory;
import com.tomorrow.UI.UiLabelStyle;
import com.tomorrow.UI.UiPopupMenu;
import com.tomorrow.UI.UiTextButtonStyle;
import com.tomorrow.UI.items.Item;
import com.tomorrow.UI.items.ItemContainer;
import com.tomorrow.UI.items.ItemObject;
import com.tomorrow.UI.items.UiItemTip;

/* describes short item data */

public class ItemLootField extends Button {

	private UiItemTip tip;
	private static UiPopupMenu submenu;

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	public ItemLootField(final ItemObject item, final ItemContainer container, final ItemContainer target) {
		super();
		setStyle(UiTextButtonStyle.style);
		int type = item.getType().ordinal();
		add(new Image(UiFactory.itemIcons[type > UiFactory.itemIcons.length - 1 ? Item.NULL.getImageIndex() : type])).expandX().left();
		Label name = new Label(item.getType().name(), UiLabelStyle.style);
		add(name).expandX().fillX().left();
		add(new Label(item.getCount() + "", UiLabelStyle.style)).right();
		addListener(new InputListener() {

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				if (tip != null && submenu == null) {
					Vector2 coords = localToStageCoordinates(new Vector2(x + 10, y - tip.getHeight() / 2));
					tip.setPosition(coords.x, coords.y);
				}
				return super.mouseMoved(event, x, y);
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if (tip == null) {
					tip = new UiItemTip();
					Label text = new Label("lorem ipsum dolor cit amet", UiLabelStyle.style);
					text.setWrap(true);
					tip.data.add(text).expand().fill();
					Vector2 coords = localToStageCoordinates(new Vector2(x + 10, y - tip.getHeight() / 2));
					getStage().addActor(tip);
					tip.setPosition(coords.x, coords.y);
				}
				/* TODO: ������� */
				if (submenu == null && pointer != 0 && !Gdx.input.isButtonPressed(Buttons.LEFT)) {
					tip.setVisible(true);
				}
				super.enter(event, x, y, pointer, fromActor);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if (tip != null) {
					tip.setVisible(false);
				}
				super.exit(event, x, y, pointer, toActor);
			}

		});

		addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				container.transferItem(target, item);
				super.clicked(event, x, y);
			}

		});

	}

}
