package clientname.mods.impl;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import clientname.gui.hud.ScreenPosition;
import clientname.mods.ModDraggable;

public class ModCPS extends ModDraggable {

	private List<Long> clicks = new ArrayList<Long>();
	private boolean wasPressed;
    private long lastPress;
	
	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public int getWidth() {
		return font.getStringWidth("CPS: 00");
	}

	@Override
	public void render(ScreenPosition pos) {
		final boolean pressed = Mouse.isButtonDown(0);
        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            this.lastPress = System.currentTimeMillis();
            if (pressed) {
                this.clicks.add(this.lastPress);
            }
        }
        final int cps = this.getCPS();
        font.drawString("CPS: " + cps, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
	}
	
	private int getCPS() {
        final long time = System.currentTimeMillis();
        this.clicks.removeIf(aLong -> aLong + 1000L < time);
        return this.clicks.size();
    }

}
