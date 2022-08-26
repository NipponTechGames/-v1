
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pong extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 700, HEIGHT = 500;
    private static final int PLAYER_HEIGHT = 100, PLAYER_WIDTH = 15;
    private static final int BALL_SIZE = 20;
    private static final int SPEED = 5;
    private static final int SCORE_TO_WIN = 10;

    private boolean gameOver = false;
    private int playerOneScore = 0, playerTwoScore = 0;

    private Timer timer;
    private int delay = 8;

    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballDx = -1, ballDy = 1;

    private int playerOneY = HEIGHT / 2 - PLAYER_HEIGHT / 2;
    private int playerTwoY = HEIGHT / 2 - PLAYER_HEIGHT / 2;

    public Pong() {
        setSize(WIDTH, HEIGHT);
        setTitle("Pong");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    movePlayerTwoUp();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    movePlayerTwoDown();
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    movePlayerOneUp();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    movePlayerOneDown();
                }
            }
        });

        timer = new Timer(delay, this);
        timer.start();
    }

    private void movePlayerOneUp() {
        if (playerOneY - SPEED > 0) {
            playerOneY -= SPEED;
        }
    }

    private void movePlayerOneDown() {
        if (playerOneY + PLAYER_HEIGHT + SPEED < HEIGHT) {
            playerOneY += SPEED;
        }
    }

    private void movePlayerTwoUp() {
        if (playerTwoY - SPEED > 0) {
            playerTwoY -= SPEED;
        }
    }

    private void movePlayerTwoDown() {
        if (playerTwoY + PLAYER_HEIGHT + SPEED < HEIGHT) {
            playerTwoY += SPEED;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ballX += ballDx;
            ballY += ballDy;

            if (ballX < 0) {
                if (ballY > playerOneY && ballY < playerOneY + PLAYER_HEIGHT) {
                    ballDx = -ballDx;
                } else {
                    playerTwoScore++;
                    ballX = WIDTH / 2 - BALL_SIZE / 2;
                    ballY = HEIGHT / 2 - BALL_SIZE / 2;
                    ballDx = -1;
                    ballDy = 1;
                }
            }
            if (ballX + BALL_SIZE > WIDTH) {
                if (ballY > playerTwoY && ballY < playerTwoY + PLAYER_HEIGHT) {
                    ballDx = -ballDx;
                } else {
                    playerOneScore++;
                    ballX = WIDTH / 2 - BALL_SIZE / 2;
                    ballY = HEIGHT / 2 - BALL_SIZE / 2;
                    ballDx = 1;
                    ballDy = 1;
                }
            }
            if (ballY < 0 || ballY + BALL_SIZE > HEIGHT) {
                ballDy = -ballDy;
            }

            if (playerOneScore == SCORE_TO_WIN || playerTwoScore == SCORE_TO_WIN) {
                gameOver = true;
            }
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(0, playerOneY, PLAYER_WIDTH, PLAYER_HEIGHT);
        g.fillRect(WIDTH - PLAYER_WIDTH, playerTwoY, PLAYER_WIDTH, PLAYER_HEIGHT);

        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        g.setFont(new Font("Arial", 1, 50));
        g.drawString(String.valueOf(playerOneScore), WIDTH / 4, 50);
        g.drawString(String.valueOf(playerTwoScore), 3 * WIDTH / 4, 50);

        if (gameOver) {
            g.setFont(new Font("Arial", 1, 30));
            g.drawString("Game Over", WIDTH / 2 - 75, HEIGHT / 2);
        }
    }

    public static void main(String[] args) {
        new Pong();
    }
}