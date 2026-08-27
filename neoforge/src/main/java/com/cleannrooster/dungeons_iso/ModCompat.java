package com.cleannrooster.dungeons_iso;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.FMLLoader;

import java.nio.file.Path;

/**
 * NeoForge mod detection and environment utilities.
 * Works at any point in the startup lifecycle — including mixin application time.
 */
public class ModCompat {

    public static boolean isModLoaded(String modId) {
        try {
            return ModList.get().isLoaded(modId);
        } catch (Throwable ignored) {
            return false;
        }
    }

    /**
     * True when the class is on the classpath. Preferred over {@link #isModLoaded} whenever the
     * question is "can I call this code", because it is answerable at any point in startup —
     * NeoForge's {@code ModList} does not exist yet while the earliest mixins are running.
     */
    public static boolean isClassPresent(String className) {
        try {
            Class.forName(className, false, ModCompat.class.getClassLoader());
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    /**
     * The loader's config directory. Used by the no-YACL config backend, which cannot ask
     * {@code YACLPlatform} for it.
     */
    public static Path getConfigDir() {
        try {
            return FMLPaths.CONFIGDIR.get();
        } catch (Throwable ignored) {
            return Path.of("config").toAbsolutePath();
        }
    }

    public static boolean isDevelopmentEnvironment() {
        try {
            return !FMLLoader.isProduction();
        } catch (Throwable ignored) {
            return false;
        }
    }
}