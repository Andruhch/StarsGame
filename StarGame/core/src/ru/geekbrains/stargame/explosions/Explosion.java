package ru.geekbrains.stargame.explosions;

/**
 * Created by Andrey Zverkov on 27.12.2017.
 */

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.sprite.Sprite;


public class Explosion extends Sprite {

    private final Sound sndExplosion;
    private float animateInterval = 0.017f;
    private float animateTimer;

    Explosion(TextureRegion region, int rows, int cols, int frames, Sound sndExplosion) {
        super(region, rows, cols, frames);
        this.sndExplosion = sndExplosion;
    }

    public void set(float height, Vector2 pos){
        frame = 0;
        this.pos.set(pos);
        setHeightProportion(height);
        if (sndExplosion.play() == -1) throw new RuntimeException();
    }

    @Override
    public void update(float deltaTime) {
        animateTimer += deltaTime;
        if (animateTimer >= animateInterval){
            animateTimer = 0f;
            if (++frame == regions.length) destroy();
        }
    }
}
