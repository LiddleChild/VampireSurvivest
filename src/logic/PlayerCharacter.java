package logic;

import core.item.Item;
import core.item.MagicWand;
import core.item.RustySword;
import core.item.ShortSword;

public enum PlayerCharacter {
	
	/*
	 * - Sprite
	 * - Default item
	 */
	
	BRAVES(		"entity/braves.png",		new ShortSword(), 5.f),
	KNIGHT(		"entity/knight.png",		new RustySword(), 4.f),
	SPELLCASTER("entity/spellcaster.png",	new MagicWand() , 5.f);
	
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
