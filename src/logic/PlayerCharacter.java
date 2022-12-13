package logic;

import core.game.item.Item;
import core.game.item.MagicWand;
import core.game.item.RustySword;
import core.game.item.ShortSword;

/*
 * 
 * PlayerCharacter
 * - holds character sprite, default weapon, default movement speed
 * 
 */

public enum PlayerCharacter {
	
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
