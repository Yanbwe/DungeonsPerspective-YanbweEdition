package com.cleannrooster.dungeons_iso.mixin;

import com.cleannrooster.dungeons_iso.ModCompat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.cleannrooster.dungeons_iso.config.Config;
import com.cleannrooster.dungeons_iso.mod.Mod;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {

    @Inject(
            method = "tick", at = @At("TAIL")
    )
    private void movementXIV(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        if (Mod.enabled ) {
            MinecraftClient client = MinecraftClient.getInstance();
            assert client.player != null;

            Vector2f movement = new Vector2f(this.movementForward, this.movementSideways);

            // Cancel the tick-aligned player yaw (the same value vanilla's updateVelocity rotates the
            // movement by), NOT the render-interpolated getYaw(tickDelta). While aiming/using and
            // strafing, lookAt() changes the player yaw every tick; using the interpolated yaw here
            // leaves a tickDelta-dependent residual, so the strafe direction (and thus the camera,
            // cursor and cursor-facing) wobbles in a feedback loop. Using the tick yaw cancels
            // vanilla's rotation exactly, keeping movement precisely camera-relative.
            float yaw = client.gameRenderer.getCamera().getYaw() - client.player.getYaw();
            if(Config.GSON.instance().cameraRelative) {
                Mod.relativeYaw = yaw;
                movement.mul(new Matrix2f().rotate((float) Math.toRadians(-yaw)));
            }


            this.movementForward = movement.x;
            this.movementSideways = movement.y;

        }
        if(this.pressingBack || this.pressingForward || this.pressingLeft || this.pressingRight){
            Mod.useTimer = 0;
        }
        else{
            Mod.useTimer++;
        }
    }

}
