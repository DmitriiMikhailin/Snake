package snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private Direction direction;
    public boolean isAlive = true;

    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y){
        direction = Direction.LEFT;
        for (int i = 0; i < 3; i++){
            GameObject go = new GameObject(x + i, y);
            snakeParts.add(go);
        }
        
    }

    public void draw(Game game){
        boolean head = true;

        for (GameObject go : snakeParts){
            if (head){
                game.setCellValueEx(go.x, go.y, Color.NONE, HEAD_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
                head = false;
            }
            else
                game.setCellValueEx(go.x, go.y, Color.NONE, BODY_SIGN, isAlive ? Color.BLACK : Color.RED, 75);

        }

    }

    public void setDirection(Direction direction){
        if ((this.direction == Direction.LEFT && direction == Direction.RIGHT) ||
                (this.direction == Direction.RIGHT && direction == Direction.LEFT) ||
                (this.direction == Direction.UP && direction == Direction.DOWN) ||
                (this.direction == Direction.DOWN && direction == Direction.UP))
            return;
        if (((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) ||
                (this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y)
            return;
        this.direction = direction;
    }

    public void  move(Apple apple){
        GameObject go = createNewHead();

        if (go.x >= 0 && go.x < SnakeGame.WIDTH && go.y >= 0 && go.y < SnakeGame.HEIGHT){
            if (checkCollision(go)){
                isAlive = false;
                return;
            }
            snakeParts.add(0, go);
            if (snakeParts.get(0).x == apple.x && snakeParts.get(0).y == apple.y)
                apple.isAlive = false;
            else
                removeTail();
        }
        else
            isAlive = false;


    }

    public GameObject createNewHead(){
        GameObject go = null;
        switch (direction){
            case UP:
                go = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
                break;
            case DOWN:
                go =  new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
                break;
            case LEFT:
                go = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
                break;
            case RIGHT:
                go = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
                break;
        }
        return go;
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject gameObj){
        for (GameObject go : snakeParts){
            if (go.x == gameObj.x && go.y == gameObj.y)
                return true;
        }

        return false;
    }

    public int getLength(){
        return snakeParts.size();
    }

    public boolean checkCollison(GameObject go){
        for (GameObject sn : snakeParts){
            if (sn.x == go.x && sn.y == go.y)
                return true;
        }
        return false;
    }
}
