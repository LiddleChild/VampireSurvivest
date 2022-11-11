package core.collision;

import javafx.scene.shape.Rectangle;

public interface HittableObject {
	
	Rectangle getHitBox();
	
	void onHit(HittableObject o);
	
}
