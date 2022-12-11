package logic;

import core.item.Item;
import core.item.ItemCollection;

public enum PlayerCharacter {
	
	/*
	 * - Sprite
	 * - Default item
	 */
	
	BRAVES(		"entity/braves.png",		ItemCollection.SHORT_SWORD),
	KNIGHT(		"entity/knight.png",		ItemCollection.RUSTY_SWORD),
	SPELLCASTER("entity/spellcaster.png",	ItemCollection.MAGIC_WAND);
	
	private String spritePath;
	private Item item;
	
	PlayerCharacter(String spritePath, Item item) {
		this.spritePath = spritePath;
		this.item = item;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public Item getItem() {
		return item;
	}
	
}
