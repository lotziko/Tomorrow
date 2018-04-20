package com.tomorrow.game;

import java.util.ArrayList;
import java.util.List;

import com.tomorrow.objects.Level;
import com.tomorrow.objects.Player;
import com.tomorrow.objects.interactives.Interactive;

/* stores important game objects as statics to access them from everywhere like Enemies or Player */

public class ObjectsLog {

	public static Player player;
	public static Level level;
	public static List<Interactive> interactives = new ArrayList<Interactive>();
}
