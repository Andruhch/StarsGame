package ru.geekbrains.engine.ui;

/**
 * Created by Andrey Zverkov on 27.12.2017.
 */

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.sprite.Sprite;

public class ScaledTouchUpButton extends Sprite{

    private int pointer;
    private boolean pressed;
    private final ActionListener listener;
    private float pressScale;

    public ScaledTouchUpButton(TextureRegion region, ActionListener listener, float pressScale) {
        super(region);
        this.listener = listener;
        this.pressScale = pressScale;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (pressed || !isMe(touch)) return false;
        this.pointer = pointer;
        scale = pressScale;
        pressed = true;
        return true;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed) return false;
        pressed = false;
        scale = 1f;
        if (isMe(touch)){
            listener.actionPerformed(this);
            return true;
        }

        return false;
    }

}

