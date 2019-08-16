package snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game{
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private int turnDelay;
    private int score;
    private boolean isGameStopped;
    private Snake snake;
    private Apple apple;
    
    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame(){
        turnDelay = 300;
        score = 0;
        setScore(score);
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        isGameStopped = false;
        drawScene();
    }

    private void drawScene(){
        for (int i = 0; i < getScreenHeight(); i++){
            for (int j = 0; j < getScreenWidth(); j++)
                setCellValueEx(j, i, Color.DARKSEAGREEN, "");
        }

        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int delay){
        snake.move(apple);
        if (!apple.isAlive){
            setScore(score += 5);
            setTurnTimer(turnDelay -= 10);
            createNewApple();
        }

        if (!snake.isAlive)
            gameOver();
        if (snake.getLength() > GOAL)
            win();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key){
        if (key == Key.LEFT)
            snake.setDirection(Direction.LEFT);
        else if (key == Key.RIGHT)
            snake.setDirection(Direction.RIGHT);
        else if (key == Key.UP)
            snake.setDirection(Direction.UP);
        else if (key == Key.DOWN)
            snake.setDirection(Direction.DOWN);

        if (key == Key.SPACE && isGameStopped)
            createGame();

    }

    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while (snake.checkCollision(apple))
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
    }
    
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "GAME OVER", Color.YELLOW, 20);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLUE, "YOU WIN", Color.YELLOW, 20);
    }
}