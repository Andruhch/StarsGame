package ru.geekbrains.stargame.screen.game;

/**
 * Created by Andrey Zverkov on 27.12.2017.
 */

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.ship.Ship;
import ru.geekbrains.stargame.bullet.BulletPool;
import ru.geekbrains.stargame.explosions.ExplosionPool;
import ru.geekbrains.engine.math.Rect;

public class MainShip extends Ship{

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;


    private final Vector2 v0 = new Vector2(0.5f, 0f);


     MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2, bulletPool, explosionPool, worldBounds);
        setHeightProportion(SHIP_HEIGHT);
         bulletRegion = atlas.findRegion("bulletMainShip");
         this.bulletSound = bulletSound;
         setToNewGame();
    }

    void setToNewGame(){
        pos.x = worldBounds.pos.x;
        bulletHeight = 0.01f;
        reloadInterval = 0.15f;
        bulletV.set(0f, 0.5f);
        bulletDamage = 1;
        hp = 100;
        stop();
        flushDestroy();

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x){
           if (leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) moveRight(); else stop();
        } else if (pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) moveLeft(); else stop();
        }
        return false;
    }

    private boolean pressedLeft;
    private boolean pressedRight;

    void keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                break;
        }

    }

    void keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) moveRight(); else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) moveLeft(); else stop();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
    }

    private void moveRight(){
        v.set(v0);
    }

    private void moveLeft(){
        v.set(v0).rotate(180);
    }

    private void stop(){
        v.setZero();
    }

    private final float hpRegenInterval = 1f;
    private float hpRegenTimer;


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        reloadTimer += deltaTime;
        if (reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
        hpRegenTimer += deltaTime;
        if (hpRegenTimer >= hpRegenInterval){
            hpRegenTimer = 0;
            if (hp < 100) hp++;
        }
        if (getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public int getHP(){
        return hp;
    }

    public Vector2 getV() {
        return v;
    }

    public boolean isBulletCollision (Rect bullet) {
        return !(bullet.getRight() < getLeft() || bullet.getLeft() > getRight() || bullet.getBottom() > pos.y || bullet.getTop() < getBottom());
    }
}
