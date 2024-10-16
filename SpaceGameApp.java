package cs2.game;

import cs2.util.Vec2;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.*;

public class SpaceGameApp extends Application {
  private ArrayList<Bullet> enemyBullet = new ArrayList<>();
  private ArrayList<Bullet> playerbullet = new ArrayList<>();
  private Set<KeyCode> kpressed = new HashSet<>();
  private long lastShot = 0;
  private long cooldownMillis = 190;
  private int score = 0;
  private int lives = 3;
  private boolean gameOver = false;

  public void start(Stage stage) {
    stage.setTitle("Space Game");
    stage.show();
    //You can change the window size here if you want
    Canvas canvas = new Canvas(800,800);
    stage.setScene(new Scene(new StackPane(canvas)));
    GraphicsContext g = canvas.getGraphicsContext2D();
    g.setFill(Color.LIGHTBLUE);
    g.fillRect(0, 0, canvas.getWidth(),canvas.getHeight());
    

    /* Your main game logic will go here
     * This will likely mean creating instances of Player and EnemySwarm, along
     * with a collection (probably ArrayList) of Bullets.
     * 
     * You should create a KeyPressed event handler that moves the player
     * when the left or right arrow keys are pressed, and fires a bullet when
     * the space bar is pressed. Add the bullet created to the collection.
     * 
     * You should also create an AnimationTimer that displays everything and
     * moves the bullets around the screen. Also, an enemy chosen from the swarm
     * at random should shoot and have that bullet added to the collection.
     */
    Image bpic = new Image("file:Bulletr.png");

    Image p = new Image("file:PlayerShip.png");
    Player player = new Player(p, bpic, new Vec2(300,500));

    Image Enemy = new Image("file:EnemyShip.png");
    
    EnemySwarm test = new EnemySwarm(3, 9, Enemy, bpic);
    
    canvas.setOnKeyPressed((KeyEvent event) -> {  
      kpressed.add(event.getCode()); 
    });
    canvas.setOnKeyReleased(e -> {

      kpressed.remove(e.getCode());
    });
    
    AnimationTimer timer = new AnimationTimer() {
      public void handle(long t) {
        if(gameOver == false){
          g.setFill(Color.SKYBLUE);
          g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
          player.display(g);
          test.display(g);
      
          if (kpressed.contains(KeyCode.A) || kpressed.contains(KeyCode.LEFT))  {
            player.moveLeft();
          }
          if(kpressed.contains(KeyCode.RIGHT) || kpressed.contains(KeyCode.D)) {
            player.moveRight();
          }
          if (kpressed.contains(KeyCode.SPACE)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShot >= cooldownMillis) {
              Bullet bullet = player.shoot();
              playerbullet.add(bullet);
              lastShot = currentTime; // Update the last shot time
            }
          }
          if (kpressed.contains(KeyCode.UP) || kpressed.contains(KeyCode.W)) {
            player.moveUp();
          }
          if (kpressed.contains(KeyCode.DOWN) || kpressed.contains(KeyCode.S)) {
            player.moveDown();
          }

          if (Math.random() < 0.01) {
            Bullet shoot = test.shoot();
            enemyBullet.add(shoot);   
          }
          Iterator<Bullet> enemdisp = enemyBullet.iterator();
          while(enemdisp.hasNext()){
            Bullet dis = enemdisp.next();
            dis.update();
            dis.display(g);
          }
      
          Iterator<Bullet> playerdisp = playerbullet.iterator();
          while(playerdisp.hasNext()){
            Bullet dis = playerdisp.next();
            dis.update();
            dis.display(g);
          }

          Iterator<Bullet> enembulletIterator = enemyBullet.iterator();
          while (enembulletIterator.hasNext()) {
            Bullet b = enembulletIterator.next();
            if (player.intersection(b)) {
              if(lives > 0){
                lives--;
              }
              enembulletIterator.remove();
                Vec2 reset = new Vec2(300,500);
                player.pos = reset;
              }
              if(b.pos.getY()>= canvas.getHeight()-15){
                enembulletIterator.remove();
              }
            }
        
          Iterator<Bullet> bullIterator = playerbullet.iterator();
          while(bullIterator.hasNext()){
            Bullet b = bullIterator.next();
            Iterator<Enemy> enemyIterator = test.getSwarm().iterator();
            if(b.pos.getY() <= 0){
              bullIterator.remove();
            } 
            while(enemyIterator.hasNext()){
              Enemy e = enemyIterator.next();
              if(e.intersection(b)){
                score++;
                enemyIterator.remove();
                bullIterator.remove();
                if (test.getSwarm().isEmpty()) {
                  player.pos = new Vec2(300,500) ;
                  for(int i = 0; i<3;i++){
                    for(int j = 0; j<9; j++){
                      Enemy enm = new Enemy(Enemy, bpic, new Vec2(j*80,i*80));
                      test.getSwarm().add(enm);
                    }
                  }
              
                } 
              }
            }
          }
        
          
          if(player.pos.getX()>= canvas.getWidth()-100) {
            player.move(new Vec2(-6, 0));
          }else if(player.pos.getX() <= 0) {
            player.move(new Vec2(6,0));
          }else if(player.pos.getY() <= 0){  
            player.move(new Vec2(0,6));
          }else if(player.pos.getY()>= canvas.getHeight()-100){  
            player.move(new Vec2(0,-6));
          }
          g.setFill(Color.BLACK);    
          g.fillText("Player Score: " + score, 20 , 750);
          g.fillText("Player Lives: " + lives, 20 , 780);  
          if(lives <=0){
            gameOver = true;
          }
        }else if(gameOver == true){
          g.setFill(Color.BLACK);
          g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
          g.setFill(Color.WHITE);
          g.fillText("Game Over",340,400);
          g.fillText("Final Score: " + score, 340, 430);
          g.fillText("Press R to restart", 340, 460);
        }
        canvas.setOnKeyPressed((KeyEvent event) -> {  
          kpressed.add(event.getCode());
          if (event.getCode() == KeyCode.R && gameOver) {
            score = 0;
            lives = 3;
            gameOver = false;
              
            enemyBullet.clear();
            playerbullet.clear();
            player.pos = new Vec2(300, 500);
              
            test.getSwarm().clear();
            for (int i = 0; i < 3; i++) {
              for (int j = 0; j < 9; j++) {
                Enemy enm = new Enemy(Enemy, bpic, new Vec2(j * 80, i * 80));
                test.getSwarm().add(enm);
              }
            }
          }
        }); 
      }
    };

    timer.start();
    canvas.requestFocus();
    }
  }


  
