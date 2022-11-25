package core.entity;

import java.util.ArrayList;

import core.Renderer;
import core.world.Tile;
import core.world.World;
import javafx.scene.paint.Color;
import util.Vector2;

public class Enemy extends BaseEntity {

	public Enemy(World world) {
		super("enemy", world);
		
		super.speed /= 2.5f;
		
		position.subtractEqual(new Vector2(0, Tile.SIZE * 3));
	}

	@Override
	public void update() {
		Vector2 playerPos = world.getPlayer().getPosition();
		super.move(playerPos.subtract(position));

		Renderer.setFill(Color.DARKRED);
		Renderer.fillRect(position, Tile.SIZE, Tile.SIZE);
		
		super.drawHealthBar();
	}

	@Override
	public void onHit(ArrayList<BaseEntity> entities) {
		
	}

}
