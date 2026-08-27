package com.cleannrooster.dungeons_iso.api.cullers.room;

/**
 * Re-meshes a chunk section, whichever chunk renderer is installed.
 *
 * <p>Culling is baked into chunk meshes, so a change in what the scanners cull only becomes visible
 * once the affected sections are rebuilt.
 */
public interface ChunkRebuildScheduler {

    /** Schedules a rebuild of the section at the given section coordinates. */
    void scheduleSection(int sectionX, int sectionY, int sectionZ);

    /** The implementation for this installation, resolved once on first use. */
    static ChunkRebuildScheduler get() {
        return Holder.INSTANCE;
    }

    final class Holder {
        static final ChunkRebuildScheduler INSTANCE = new VanillaRebuildScheduler();

        private Holder() {
        }
    }
}
