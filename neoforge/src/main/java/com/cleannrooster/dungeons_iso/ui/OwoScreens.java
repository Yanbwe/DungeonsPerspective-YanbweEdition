package com.cleannrooster.dungeons_iso.ui;

import com.cleannrooster.dungeons_iso.ModCompat;
import net.minecraft.client.gui.screen.Screen;

/**
 * Opens the owo-lib screens when owo-lib is installed, and does nothing when it is not.
 *
 * <p>{@link FirstTimeScreen} extends owo's {@code BaseOwoScreen}, so merely
 * naming it from a call site is enough to make the JVM load owo. It is built
 * reflectively here, behind a class-presence test, so the rest of the mod only ever handles a
 * vanilla {@link Screen} and never mentions its type.
 */
public final class OwoScreens {

    private static final boolean AVAILABLE =
            ModCompat.isClassPresent("io.wispforest.owo.ui.base.BaseOwoScreen");

    private OwoScreens() {
    }

    /** The first-run prompt, or null if owo-lib is not installed. */
    public static Screen firstTime() {
        return build("com.cleannrooster.dungeons_iso.ui.FirstTimeScreen");
    }

    private static Screen build(String className) {
        if (!AVAILABLE) {
            return null;
        }
        try {
            return (Screen) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Throwable ignored) {
            return null;
        }
    }
}
