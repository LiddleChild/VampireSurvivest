package logic;

import core.item.Item;
import core.item.ItemCollection;

public enum PlayerCharacter {
	
	/*
	 * - Sprite
	 * - Default item
	 */
	
	BRAVES(		"entity/braves.png",		ItemCollection.SHORT_SWORD,  5.f),
	KNIGHT(		"entity/knight.png",		ItemCollection.RUSTY_SWORD,	3.5f),
	SPELLCASTER("entity/spellcaster.png",	ItemCollection.MAGIC_WAND,	 5.f);
	
	private String spritePath;
	private Item item;
	private float movementSpeed;
	
	PlayerCharacter(String spritePath, Item item, float movementSpeed) {
		this.spritePath = spritePath;
		this.item = item;
		this.movementSpeed = movementSpeed;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public Item getItem() {
		return item;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}
	
}
