package com.cleannrooster.dungeons_iso.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;

public class Config {
    public static final ConfigClassHandler<Config> GSON = ConfigClassHandler
            .createBuilder(Config.class)
            .serializer(config -> GsonConfigSerializerBuilder
                    .create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("dungeons_iso_v5.json"))
                    .build())
            .build();
    @SerialEntry
    public boolean XIV =  false;

    /**
     * "Controller mode" master toggle. When on, it forces a controller/one-handed-friendly preset,
     * overriding the individual toggles below at their behavior read sites (the stored values are
     * left untouched so turning Controller mode back off restores them):
     * turn-to-mouse OFF, contextual targeting ON, movement-based targeting ON, contextual block
     * interaction ON, click-to-move OFF, roll-towards-cursor (Combat Roll compat) OFF.
     */
    @SerialEntry
    public boolean controllerMode =  false;

    @SerialEntry
    public boolean onStartup =  true;
    @SerialEntry
    public boolean force =  false;
    @SerialEntry
    public boolean fogOfWar =  false;
    @SerialEntry
    public boolean distanceFog =  true;
    @SerialEntry
    public boolean renderDistanceCap =  true;
    @SerialEntry
    public float cullAngle = 6.0F;
    @SerialEntry
    public float coneHalfAngle = 45.0F;
    @SerialEntry
    public boolean backCull = false;
    @SerialEntry
    public boolean scrollWheelZoom = true;
    @SerialEntry
    public boolean dynamicCamera = false;
    @SerialEntry
    public boolean forceNoDefer =  false;
    @SerialEntry
    public boolean cameraRelative =  true;

    /**
     * Native gamepad left-stick movement. When on (and a controller is connected) the left
     * analog stick drives movement through the same camera-relative pipeline as WASD; while the
     * stick is deflected past the deadzone it overrides the keyboard for that tick.
     */
    @SerialEntry
    public boolean joystickMovement =  true;

    @SerialEntry
    public boolean turnToMouse =  true;
    @SerialEntry
    public boolean clipToSpace =  false;

    @SerialEntry
    public boolean additionalMeleeAssistance =  false;
    @SerialEntry
    public boolean contextualTargeting =  false;
    @SerialEntry
    public boolean movementTargeting =  false;
    @SerialEntry
    public boolean contextualInteract =  false;
    @SerialEntry
    public boolean forceAutoJump =  true;
    @SerialEntry
    public boolean rollTowardsCursor =  true;

    @SerialEntry

    public float moveFactor_v3 = 0.5F;
    @SerialEntry
    public float fov = 45.0F;
    @SerialEntry
    public float zoomFactor = 1.5F;
    @SerialEntry
    public float zNearFactor = 1F;

    @SerialEntry
    public boolean ortho = false;
    @SerialEntry

    public boolean clickToMove = false;

    @SerialEntry

    public boolean frustumCulling = false;

    @SerialEntry
    public boolean showFirstTimeGui = true;

    @SerialEntry
    public float soundListenerBias = 0.66F;

    // --- Effective accessors -----------------------------------------------------------------
    // Read these (not the raw fields) at behavior sites so "Controller mode" can override them.

    /** Turn-to-mouse facing; forced OFF in Controller mode (movement drives facing instead). */
    public boolean isTurnToMouse() {
        return !controllerMode && turnToMouse;
    }

    /** Click-to-move; forced OFF in Controller mode. */
    public boolean isClickToMove() {
        return !controllerMode && clickToMove;
    }

    /** Contextual combat targeting; forced ON in Controller mode. */
    public boolean isContextualTargeting() {
        return controllerMode || contextualTargeting;
    }

    /** Movement-based contextual targeting; forced ON in Controller mode. */
    public boolean isMovementTargeting() {
        return controllerMode || movementTargeting;
    }

    /** Contextual interactable-block targeting; forced ON in Controller mode. */
    public boolean isContextualInteract() {
        return controllerMode || contextualInteract;
    }

    /** Combat Roll compat "roll towards cursor"; forced OFF in Controller mode. */
    public boolean isRollTowardsCursor() {
        return !controllerMode && rollTowardsCursor;
    }

    /** Native gamepad left-stick movement; forced ON in Controller mode. */
    public boolean isJoystickMovement() {
        return controllerMode || joystickMovement;
    }
}
