package core.ui.components;

import core.sprite.Sprite;

public class Image extends UIComponent {

	private SubWindow bound;
	private Sprite sprite;
	private float scale;
	
	public Image(String path, int x, int y, int size) {
		sprite = new Sprite(path);
		bound = new SubWindow(x, y, size, size);
		scale = (float) size / ((sprite.getWidth() > sprite.getHeight()) ? sprite.getWidth() : sprite.getHeight());
	}
	
	@Override
	public void update(float deltaTime) {
		bound.update(deltaTime);
		gc.drawImage(sprite.getImage(),
				bound.getBound().x - (sprite.getWidth() * scale - bound.getBound().width) / 2,
				bound.getBound().y - (sprite.getHeight() * scale - bound.getBound().height) / 2,
				sprite.getWidth() * scale,
				sprite.getHeight() * scale);
	}

	public SubWindow getBound() {
		return bound;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
}
