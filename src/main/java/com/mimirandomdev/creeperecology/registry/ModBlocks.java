package com.mimirandomdev.creeperecology.registry;

import com.mimirandomdev.creeperecology.CreeperEcology;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central registry for every block (and its matching block item) added by Creeper Ecology.
 *
 * <p>Both deferred registers declared here MUST be attached to the mod event bus by calling
 * {@link #register(IEventBus)} from the mod constructor. Otherwise nothing declared in this
 * class is ever registered in the game.</p>
 *
 * <p>Each block is declared with {@link #registerBlockWithItem(String, BlockBehaviour.Properties)},
 * which registers the block and its item under the same name. A {@link DeferredBlock} can be used
 * wherever an item is expected (for example in a creative tab), so no separate item fields are kept.</p>
 *
 * <p>Blocks that belong to the underground "nest geode" structure:</p>
 * <ul>
 *   <li>{@link #GUNPOWDER_SAND}: interior floor of the geode and scattered patches outside it.</li>
 *   <li>{@link #GUNPOWDER_CALCITE}: inner wall of the geode.</li>
 *   <li>{@link #SULFUROUS_DEEPSLATE}: outer crust of the geode.</li>
 *   <li>{@link #CREEPER_CORE}: geometric center of the geode, origin of juvenile creepers.</li>
 * </ul>
 */
public final class ModBlocks {

    private ModBlocks() {
        // Static registry holder, not meant to be instantiated.
    }

    /** Deferred register for all blocks of the mod, under the "creeperecology" namespace. */
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreeperEcology.MODID);

    /** Deferred register for all items of the mod, under the "creeperecology" namespace. */
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CreeperEcology.MODID);

    /**
     * Registers a simple block and its block item under the same name.
     *
     * <p>The item registration works by side effect, so only the block holder is returned.</p>
     *
     * @param name       registry name, without namespace (for example {@code "gunpowder_sand"})
     * @param properties properties of the block
     * @return the deferred holder of the registered block
     */
    private static DeferredBlock<Block> registerBlockWithItem(
            String name,
            BlockBehaviour.Properties properties
    ) {
        DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(name, properties);
        ITEMS.registerSimpleBlockItem(name, block);
        return block;
    }

    /**
     * Gunpowder Sand. Soft, sand-like block that forms the interior floor of the geode.
     * Currently a plain (non-falling) block.
     */
    public static final DeferredBlock<Block> GUNPOWDER_SAND =
            registerBlockWithItem(
                    "gunpowder_sand",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SAND)
                            .strength(0.5F)
                            .sound(SoundType.SAND)
            );

    /** Gunpowder Calcite. Crystalline lining of the inner wall of the geode. */
    public static final DeferredBlock<Block> GUNPOWDER_CALCITE =
            registerBlockWithItem(
                    "gunpowder_calcite",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_WHITE)
                            .strength(1.5F)
                            .sound(SoundType.CALCITE)
            );

    /** Sulfurous Deepslate. Hard outer crust that isolates the geode from the surrounding stone. */
    public static final DeferredBlock<Block> SULFUROUS_DEEPSLATE =
            registerBlockWithItem(
                    "sulfurous_deepslate",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DEEPSLATE)
                            .strength(3.0F)
                            .sound(SoundType.DEEPSLATE)
            );

    /**
     * Creeper Core. Placed at the geometric center of the geode.
     *
     * <p>Provisional: this is a plain block for now. It will later become a block with a
     * {@code BlockEntity} holding the persistent state that drives juvenile creeper spawning.</p>
     */
    public static final DeferredBlock<Block> CREEPER_CORE =
            registerBlockWithItem(
                    "creeper_core",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW)
                            .strength(2.0F)
                            .sound(SoundType.STONE)
            );

    /**
     * Attaches the block and item registers to the given mod event bus.
     * Must be called once from the mod constructor.
     *
     * @param modEventBus the mod event bus received by the mod constructor
     */
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
