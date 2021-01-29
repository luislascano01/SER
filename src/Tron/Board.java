/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tron;

import SER.Food;
import SER.Panel;
import SER.Puntaje;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Luis Lascano
 */
public class Board extends JPanel implements ActionListener    {
   {
        Object ex = null;
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
private final static int BOARDWIDTH = 1000;
private final static int BOARDHEIGHT = 980;

// Used to represent pixel size of food & our snake's joints
private final static int PIXELSIZE = 25;

// The total amount of pixels the game could possibly have.
// We don't want less, because the game would end prematurely.
// We don't more because there would be no way to let the player win.

private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT)
        / (PIXELSIZE * PIXELSIZE);

// Check to see if the game is running
private boolean inGame = true;

// Timer used to record tick times
private Timer timer;

// Used to set game speed, the lower the #, the faster the snake travels
// which in turn
// makes the game harder.
private static int speed = 80;

// Instances of our snake & food so we can use their methods
private Bike snake = new Bike();
private Food food = new Food();
Puntaje pointy=new Puntaje();

public Board() throws IOException {

  
    
    

    addKeyListener(new Keys());
    setBackground(Color.BLACK);
    setFocusable(true);

    setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));

    initializeGame();
}

// Used to paint our components to the screen
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    draw(g);
}

// Draw our snake & Food (Called on repaint()).
void draw(Graphics g) {
    // Only draw if the game is running / the snake is alive
    if (inGame == true) {
        g.setColor(Color.green);
        g.fillRect(food.getFoodX(), food.getFoodY(), PIXELSIZE, PIXELSIZE); // food

        // Draw our snake.
        for (int i = 0; i < snake.getJoints(); i++) {
            // snake's head
            if (i == 0) {
                g.setColor(Color.RED);
                g.fillRect(snake.getsnakeX(i), snake.getsnakeY(i),
                        PIXELSIZE, PIXELSIZE);
                // Body of snake
            } else {
                g.fillRect(snake.getsnakeX(i), snake.getsnakeY(i),
                        PIXELSIZE, PIXELSIZE);
            }
        }

        // Sync our graphics together
        Toolkit.getDefaultToolkit().sync();
    } else {
        // If we're not alive, then we end our game
        endGame(g);
    }
}

void initializeGame() {
    snake.setJoints(3); // set our snake's initial size

    // Create our snake's body
    for (int i = 0; i < snake.getJoints(); i++) {
        snake.setsnakeX(BOARDWIDTH / 2);
        snake.setsnakeY(BOARDHEIGHT / 2);
    }
    // Start off our snake moving right
    snake.setMovingRight(true);

    // Generate our first 'food'
    food.createFood();

    // set the timer to record our game's speed / make the game move
    timer = new Timer(speed, this);
    timer.start();
}

// if our snake is in the close proximity of the food..
public void checkFoodCollisions() {

    if ((proximity(snake.getsnakeX(0), food.getFoodX(), 20))
            && (proximity(snake.getsnakeY(0), food.getFoodY(), 20))) {

        System.out.println("intersection");
        // Add a 'joint' to our snake
        snake.setJoints(snake.getJoints() + 1);
        // Create new food
        food.createFood();
        pointy.subirPuntaje();
    }
}

// Used to check collisions with snake's self and board edges
void checkCollisions() {

    // If the snake hits its' own joints..
    for (int i = snake.getJoints(); i > 0; i--) {

        // snake cant intersect with itself if it's not larger than 5
        if ((i > 5)
                && (snake.getsnakeX(0) == snake.getsnakeX(i) && (snake
                        .getsnakeY(0) == snake.getsnakeY(i)))) {
            inGame = false; // then the game ends
            
           
        }
        else{}
    }

    // If the snake intersects with the board edges..
    if (snake.getsnakeY(0) >= BOARDHEIGHT) {
        inGame = false;
    }

    if (snake.getsnakeY(0) < 0) {
        inGame = false;
    }

    if (snake.getsnakeX(0) >= BOARDWIDTH) {
        inGame = false;
    }

    if (snake.getsnakeX(0) < 0) {
        inGame = false;
    }

    // If the game has ended, then we can stop our timer
    if (!inGame) {
        timer.stop();
    }
}

void endGame(Graphics g) {
    String message = "Game Over";
    pointy.tire();

    Font font = new Font("Nazalisation", Font.BOLD, 60);
    FontMetrics metrics = getFontMetrics(font);
    g.setColor(Color.red);
    g.setFont(font);

    // Dibuja el mensaje de que perdio en el tablero
    g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2,
            BOARDHEIGHT / 2);

    System.out.println("Game Ended");

}

// Correr constantemente siempre y cuando este en el juego
@Override
public void actionPerformed(ActionEvent e) {
    if (inGame == true) {

        checkFoodCollisions();
        checkCollisions();
        snake.move();

        System.out.println(snake.getsnakeX(0) + " " + snake.getsnakeY(0)
                + " " + food.getFoodX() + ", " + food.getFoodY());
    }
    repaint();
}

private class Keys extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!snake.isMovingRight())) {
            snake.setMovingLeft(true);
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_RIGHT) && (!snake.isMovingLeft())) {
            snake.setMovingRight(true);
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_UP) && (!snake.isMovingDown())) {
            snake.setMovingUp(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_DOWN) && (!snake.isMovingUp())) {
            snake.setMovingDown(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_ENTER) && (inGame == false)) {

            inGame = true;
            snake.setMovingDown(false);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
            snake.setMovingUp(false);

            initializeGame();
        }
    }
}

private boolean proximity(int a, int b, int closeness) {
    return Math.abs((long) a - b) <= closeness;
}

public static int getAllDots() {
    return TOTALPIXELS;
}

public static int getDotSize() {
    return PIXELSIZE;
}
public void close(){
    this.close();
    
    
}}