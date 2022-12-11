package core.ui.components;

import core.sprite.Sprite;

public class Image extends UIComponent {

	private SubWindow bound;
	private Sprite sprite;
	
	public Image(String path, int x, int y, int w, int h) {
		sprite = new Sprite(path);
		bound = new SubWindow(x, y, w, h);
	}
	
	@Override
	public void update(float deltaTime) {
		bound.update(deltaTime);
		gc.drawImage(sprite.getImage(),
				bound.getBound().x,
				bound.getBound().y,
				bound.getBound().width,
				bound.getBound().height);
	}

	public SubWindow getBound() {
		return bound;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
}
