package cs2.game;

import cs2.util.Vec2;
import javafx.scene.image.Image;

public class Enemy extends Sprite {
  private Image bulletPicture;

  
  //This constructor should initialize all fields
  //**Remember that some fields are inherited from Sprite
  public Enemy(Image avatar, Image bulletPic, Vec2 p) { 
    super(avatar,p);
    this.bulletPicture = bulletPic;
  }
  // The Bullet should be initialized with the bulletPicture, the
  // current position of the enemy, and a velocity going down the screen
  public Bullet shoot() { 
    Vec2 bulletPosition = new Vec2(pos.getX()+20, pos.getY()+30);
    Vec2 bulletVelocity = new Vec2(0, 1); 
    Bullet bullet = new Bullet(bulletPicture, bulletPosition, bulletVelocity);
    return bullet;
   }
  
  
}

