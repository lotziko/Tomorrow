package com.tomorrow.UI.items;

public enum Item {
	NULL(0, 0), WOOD(1, 1), SCRAP(2, 1.2f), RATIONS(3, 0.5f), POWER_CELL(4, 2f), MEDKIT(5, 0.25f);

	private float weight;
	private int imageIndex;

	public int getImageIndex() {
		return imageIndex;
	}

	public float getWeight() {
		return weight;
	}

	private Item(int imageIndex, float weight) {
		this.imageIndex = imageIndex;
		this.weight = weight;
	}
}
