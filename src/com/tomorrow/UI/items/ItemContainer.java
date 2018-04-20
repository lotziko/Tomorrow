package com.tomorrow.UI.items;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import com.google.gson.annotations.Expose;

public class ItemContainer {

	@Expose
	private float weight, maxWeight = 10;
	@Expose
	private List<ItemObject> items = new CopyOnWriteArrayList<ItemObject>();
	private List<UpdateListener> listeners = new ArrayList<UpdateListener>();

	private void update() {
		for (UpdateListener listener : listeners) {
			listener.handle();
		}
	}

	public void addListener(UpdateListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(UpdateListener listener) {
		listeners.remove(listener);
	}

	public int size() {
		return items.size();
	}

	public float getWeight() {
		return weight;
	}

	public float getMaxWeight() {
		return maxWeight;
	}

	public ItemObject getItem(int i) {
		if (i < items.size()) {
			return items.get(i);
		}
		return null;
	}

	public ItemObject addItem(ItemObject item) {
		for (ItemObject listItem : items) {
			if (listItem.getType() == item.getType()) {
				int count = 0;
				for(int i = 0; i < item.getCount(); i++) {
					if (weight + item.getAllWeight() <= maxWeight) {
						listItem.setCount(listItem.getCount() + 1);
						count += 1;
						weight += item.getAllWeight();
						weight = Math.round(weight * 100f)/100f;
					}
				}
				update();
				return ItemFactory.createItem(item.getType(), count);
			}
		}
		if (weight + item.getAllWeight() <= maxWeight) {
			items.add(item.clone());
			weight += item.getAllWeight();
			weight = Math.round(weight * 100f)/100f;
			sort();
			update();
			return item;
		}
		return null;
	}
	
	public void transferItem(ItemContainer destination, ItemObject item) {
		this.removeItem(destination.addItem(item));
	}

	public boolean removeItem(ItemObject item) {
		if (item != null) {
			for(ItemObject listItem : items) {
				if (listItem.getType() == item.getType()) {
					weight -= item.getAllWeight();
					weight = Math.round(weight * 100f)/100f;
					listItem.setCount(listItem.getCount() - item.getCount());
					if (listItem.getCount() <= 0) {
						items.remove(listItem);
					}
				}
			}
			sort();
			update();
			return true;
		}
		return false;
	}

	public boolean removeItem(int type, int count) {
		//if (countItemsOfType(type) >= count) {
		//	ItemObject temp;
			/*
			 * for (int i = 0; i < size; i++) { if ((temp =
			 * items.get(i)).getType() == type) { while(temp.getCount() > 0 &&
			 * count > 0) { temp.setCount(temp.getCount() - 1); --count; } } }
			 */
			sort();
			update();
			return true;
		//}
		//return false;
	}

	public void sort() {
		items.removeIf(new Predicate<ItemObject>() {
			@Override
			public boolean test(ItemObject i) {
				return i.getCount() <= 0;
			}
		});
		items.sort(new Comparator<ItemObject>() {

			@Override
			public int compare(ItemObject o1, ItemObject o2) {
				return Integer.compare(o1.getType().ordinal(), o1.getType().ordinal());
			}

		});
	}
	
	public ItemContainer(int maxWeight) {
		this.maxWeight = maxWeight;
	}

}
