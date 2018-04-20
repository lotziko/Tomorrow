package com.tomorrow.objects.interactives;

import com.tomorrow.game.ObjectsLog;

import blueberry.engine.objects.Object;

/* Do not need to add this to interactives list*/

public class Interactive extends Object {

	@Override
	public void step() {
		
		super.step();
	}

	@Override
	public void draw() {
		
		super.draw();
	}

	@Override
	public void create() {
		
		super.create();
	}
	
	@Override
	public void destroy() {
		ObjectsLog.interactives.remove(this);
		super.destroy();
	}
	
	public void triggered(Object instance) {}
	
	public Interactive() {
		ObjectsLog.interactives.add(this);
	}
	
}
