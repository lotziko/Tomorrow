package com.tomorrow.UI.items;

import java.util.HashMap;

import com.tomorrow.game.ObjectsLog;

public class EventExecutor {

	public static EventExecutor healPlayer, transportItem;

	public void execute(HashMap<String, Object> arguments) {
	}

	static {

		healPlayer = new EventExecutor() {

			@Override
			public void execute(HashMap<String, Object> arguments) {
				if (ObjectsLog.player != null) {
					Integer healRate = (Integer) arguments.get("hp");
					if (healRate != null)
						ObjectsLog.player.changeHp(healRate);
				}
			}

		};

		transportItem = new EventExecutor() {

			@Override
			public void execute(HashMap<String, Object> arguments) {
				ItemContainer home = (ItemContainer) arguments.get("home");
				ItemContainer destination = (ItemContainer) arguments.get("destination");
				ItemObject item = (ItemObject) arguments.get("item");
				if (home != null && destination != null && item != null)
					home.transferItem(destination, item);
			}

		};

	}

}
