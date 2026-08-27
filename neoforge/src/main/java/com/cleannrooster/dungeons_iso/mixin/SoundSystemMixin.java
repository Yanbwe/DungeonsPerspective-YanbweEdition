package com.cleannrooster.dungeons_iso.mixin;

import com.cleannrooster.dungeons_iso.config.Config;
import com.cleannrooster.dungeons_iso.mod.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.sound.SoundExecutor;
import net.minecraft.client.sound.SoundListener;
import net.minecraft.client.sound.SoundListenerTransform;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {

    @Shadow private boolean started;
    @Shadow private SoundListener listener;
    @Shadow  private  SoundExecutor taskQueue;


    @Inject(method = "updateListenerPosition", at = @At("HEAD"),cancellable = true)
    private void repositionListenerToPlayer(Camera camera, CallbackInfo ci) {
        if (!Mod.enabled || !started || !camera.isReady()) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        var pt = camera.getPos().add(client.player.getEyePos().subtract(camera.getPos()).multiply(Config.GSON.instance().soundListenerBias));
        taskQueue.execute(() -> listener.setTransform(new SoundListenerTransform(
                pt,
                Vec3d.fromPolar(0,  camera.getYaw()),
                new Vec3d(0,1F,0F)
        )));
        ci.cancel();
    }
}
