package com.tomorrow.UI.items;

public class ItemFactory {

	public static ItemObject createItem(Item type, int count) {
		return new ItemObject(type, count);
	}
	
}
