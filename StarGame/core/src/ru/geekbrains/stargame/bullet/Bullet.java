package ru.geekbrains.stargame.bullet;
/**
 * Created by Andrey Zverkov on 27.12.2017.
 */

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.sprite.Sprite;

public class Bullet extends Sprite {

    private Rect worldBound;
    private final Vector2 v = new Vector2();
    private int damage;
    private Object owner;

    Bullet() {
        regions = new TextureRegion[1];
    }


    public void set(Object owner,
                    TextureRegion textureRegion,
                    Vector2 pos0,
                    Vector2 v0,
                    float height,
                    Rect worldBounds,
                    int damage) {
        this.owner = owner;
        regions[0] = textureRegion;
        pos.set(pos0);
        v.set(v0);
        setHeightProportion(height);
        this.worldBound = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v, deltaTime);
        if (isOutside(worldBound)) destroy();
    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }
}
