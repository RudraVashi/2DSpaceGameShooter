package cs2.game;

import java.util.ArrayList;

import cs2.util.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.random.*;
import java.util.Iterator;

public class EnemySwarm {
  private ArrayList<Enemy> swarm;
  private int rows;
  private int col;
  private Image enem;
  private Image b;
  
  // This constructor should create a swarm of enemies in a grid
  // The grid should be nRows x nCols in size.
  // The enemPic and bullPic should be used to create the Enemy instances
  // that are added to the ArrayList. The enemies should be spaced out
  // in a grid pattern across the top of the screen.
  public EnemySwarm(int nRows, int nCols, Image enemPic, Image bullPic) {
    swarm = new  ArrayList<Enemy>();
    this.rows = nRows;
    this.col = nCols;
    this.enem = enemPic;
    this.b = bullPic;
    int x = 0;
    int y = 0;
    for(int i = 0; i<rows;i++){
      y+=80;
      for(int j = 0; j<col; j++){
        x +=80;
        swarm.add(new Enemy(enem, b, new Vec2(j+x,i+y)));
      }
      x = 0;
    }

   }
  

  
  // This method should display all enemies in the swarm
  public void display(GraphicsContext g) {
    for(int i = 0; i<swarm.size(); i++){
      swarm.get(i).display(g);
      
  }
}


  

  
  // This method should choose one enemy at random from the swarm,
  // and have that enemy shoot a bullet. Return that Bullet.
  public Bullet shoot() { 
    double rand = Math.random() * swarm.size() ;
    Enemy test = swarm.get((int) rand);
    return test.shoot();
  }
  public boolean intersection(Sprite sprite) {
    Iterator<Enemy> iterator = swarm.iterator();
    boolean gotHit = false;
    while (iterator.hasNext()) {
        Enemy enem = iterator.next();
        if (enem.intersection(sprite)) {
            
            gotHit = true;
        }
    }
    return gotHit;
  }
  
  public ArrayList<Enemy> getSwarm(){
    return this.swarm;
  }

  
  
    
}
 