package cs2.game;

import cs2.util.Vec2;
import javafx.scene.image.Image;

public class Player extends Sprite {
  private Image bulletPicture;

  
  //This constructor should initialize all fields
  //**Remember that some fields are inherited from Sprite
  public Player(Image avatar, Image bullPic, Vec2 p) { 
    super(avatar,p);
    this.bulletPicture = bullPic;
  }
  

  
    // This method should create a new Bullet object and return it
    // The Bullet should be initialized with the bulletPicture, the
    // current position of the player, and a velocity going up the screen
    
    
    public Bullet shoot() { 
      Vec2 bulletPosition = new Vec2(pos.getX()+50, pos.getY());
      Vec2 bulletVelocity = new Vec2(0, -1); 
      Bullet bullet = new Bullet(bulletPicture, bulletPosition, bulletVelocity);
      return bullet;
     }


  
  // This method should move the player left by some amount
  public void moveLeft() { 
    Vec2 left = new Vec2(-6, 0);
    move(left);
  }
  

  
  // This method should move the player right by some amount
  public void moveRight() { 
    Vec2 right = new Vec2(6, 0);
    move(right);
  }
  
  public void moveUp(){
    Vec2 up = new Vec2(0,-6);
    move(up);
  }
  public void moveDown(){
    Vec2 down = new Vec2(0,6);
    move(down);
  }
}
