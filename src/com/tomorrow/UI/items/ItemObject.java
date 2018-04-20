package com.tomorrow.UI.items;

import java.util.HashMap;

import com.google.gson.annotations.Expose;

public class ItemObject {
	
	@Expose
	private Item type;
	@Expose
	private HashMap<String, Integer> specialData;
	@Expose
	private int count;
	
	public Item getType() {
		return type;
	}
	
	public Integer getData(String key) {
		return specialData.get(key);
	}
	
	public int getCount() {
		return count;
	}
	
	public float getWeight() {
		return type.getWeight();
	}
	
	public float getAllWeight() {
		return type.getWeight() * count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public ItemObject clone() {
		return new ItemObject(this.type, this.count);
	}
	
	ItemObject(Item type, int count) {
		this.type = type;
		this.count = count;
		specialData = new HashMap<String, Integer>();
	}
	
}