package com.cleannrooster.dungeons_iso.mixin;

import com.cleannrooster.dungeons_iso.api.cullers.room.TerrainCulling;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Culls terrain on every non-Sodium chunk renderer, by lying to the mesher about what is there.
 *
 * <p>The obvious hook — cancelling {@code BlockRenderManager.renderBlock} — does not work in
 * practice. NeoForge's patched chunk meshing can route block rendering through its own render path,
 * sending anything with a non-vanilla model elsewhere instead, so the injection sits on a call that
 * is never made.
 *
 * <p>{@code ChunkRendererRegion} is the snapshot of the world a section is built from, and it is
 * upstream of that fork: the vanilla chunk renderer and NeoForge's patched mesher both ask it what
 * block is at a position. Reporting air is therefore a single hook that removes the block from every
 * one of them — and it removes it properly, because everything else the mesher derives from that
 * state follows along:
 *
 * <ul>
 *   <li>the fluid is gone, since {@code SectionBuilder} reads it off the returned state;</li>
 *   <li>the block entity is gone, for the same reason;</li>
 *   <li>faces on the neighbouring blocks come back, because vanilla's occlusion test asks this
 *       region what is next door and now hears air — no separate face hook needed;</li>
 *   <li>smooth lighting relights those faces as if the space were open;</li>
 *   <li>the section stops being marked closed in the visibility graph, so traversal reaches
 *       through the opening instead of hiding what the roof used to cover.</li>
 * </ul>
 *
 * <p>Sodium uses its own rendering pipeline and bypasses this class entirely, so this
 * vanilla/NeoForge hook is the active path.
 */
@Mixin(ChunkRendererRegion.class)
public abstract class ChunkRendererRegionMixin {

    @ModifyReturnValue(method = "getBlockState", at = @At("RETURN"))
    private BlockState cullTerrainXIV(BlockState original, BlockPos pos) {
        // Hot path: called for every block and every neighbour of every section build.
        if (TerrainCulling.idle()) {
            return original;
        }
        try {
            if (TerrainCulling.shouldRemove(original, pos.getX(), pos.getY(), pos.getZ())) {
                return Blocks.AIR.getDefaultState();
            }
        } catch (Exception ignored) {
        }
        return original;
    }
}
