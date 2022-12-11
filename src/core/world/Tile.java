package core.world;

import core.Renderer;
import core.behavior.GameBehavior;
import core.sprite.Sprite;

public class Tile extends GameBehavior {
	public static final int SIZE = 32;
	
	private Sprite sprite = null;
	private boolean solid = false, transparent = false;
	private int x, y;
	
	public Tile(Sprite sprite, int x, int y) {
		this(sprite, x, y, 0);
	}
	
	public Tile(Sprite sprite, int x, int y, int layer) {
		super(layer);
		
		this.x = x * Tile.SIZE;
		this.y = y * Tile.SIZE;
		
		this.sprite = sprite;
	}
	
	public void setTransparent() {
		transparent = true;
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		if (sprite != null) {
			if (transparent) Renderer.setRenderOpacity(0.5f);
			Renderer.drawSprite(sprite, x, y, Tile.SIZE, Tile.SIZE);
		}
		
		transparent = false;
	}

	public boolean isSolid() {
		return solid;
	}
	
	public boolean isEmptyTile() {
		return sprite == null;
	}
	
}
