package cs2.game;

import cs2.util.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
  protected Image img; // the image to be displayed for this sprite
  protected Vec2 pos; // the current position of this sprite

  /* The remained of the constructors and methods should be uncommented
   * as you write your code. I recommend keeping your project in a state
   * that it can always be run, even if it doesn't do much.
   * Then slowly over time, you can un-comment and add in additional
   * features.
   * DO NOT TRY TO WRITE EVERYTHING ALL AT ONCE. IT WILL NOT WORK.
   */


  
  // The constructor should initialize the fields of the class
  public Sprite(Image i, Vec2 p) { 
    this.img = i;
    this.pos = p;
  }
  

  
  // This method should draw the image at the current position
  public void display(GraphicsContext g) { 
    g.drawImage(img, pos.getX(), pos.getY());
  }
  

  
  // This method should change the location/position of the sprite
  // by the amount specified in the parameter delta
  public void move(Vec2 delta) { 
    pos = pos.add(delta);
  }
   
  public boolean intersection(Sprite other){
    double Left = pos.getX();
    double Right = pos.getX() + img.getWidth();
    double Up = pos.getY();
    double Down = pos.getY() + img.getHeight();

    double Left2 = other.pos.getX();
    double Right2 = other.pos.getX() + other.img.getWidth();
    double Up2 = other.pos.getY();
    double Down2 = other.pos.getY() + other.img.getHeight();

    if (Right >= Left2 && Left <= Right2 && Down >= Up2 && Up <= Down2) {
        return true; 
    }
    
    return false; 
  }
}


