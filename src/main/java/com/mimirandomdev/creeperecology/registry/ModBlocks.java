package com.mimirandomdev.creeperecology.registry;

import com.mimirandomdev.creeperecology.CreeperEcology;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central registry for every block (and its matching {@link BlockItem}) added by Creeper Ecology.
 *
 * <p>Both deferred registers declared here MUST be attached to the mod event bus by calling
 * {@link #register(IEventBus)} from the mod constructor. Otherwise nothing declared in this
 * class is ever registered in the game.</p>
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

    // ---------------------------------------------------------------------
    // Gunpowder Sand
    // ---------------------------------------------------------------------

    /**
     * Gunpowder Sand. Soft, sand-like block that forms the interior floor of the geode.
     * Currently a plain (non-falling) block.
     */
    public static final DeferredBlock<Block> GUNPOWDER_SAND =
            BLOCKS.registerSimpleBlock(
                    "gunpowder_sand",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SAND)
                            .strength(0.5F)
                            .sound(SoundType.SAND)
            );

    /** Item form of {@link #GUNPOWDER_SAND}. */
    public static final DeferredItem<BlockItem> GUNPOWDER_SAND_ITEM =
            ITEMS.registerSimpleBlockItem("gunpowder_sand", GUNPOWDER_SAND);

    // ---------------------------------------------------------------------
    // Gunpowder Calcite
    // ---------------------------------------------------------------------

    /** Gunpowder Calcite. Crystalline lining of the inner wall of the geode. */
    public static final DeferredBlock<Block> GUNPOWDER_CALCITE =
            BLOCKS.registerSimpleBlock(
                    "gunpowder_calcite",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_WHITE)
                            .strength(1.5F)
                            .sound(SoundType.CALCITE)
            );

    /** Item form of {@link #GUNPOWDER_CALCITE}. */
    public static final DeferredItem<BlockItem> GUNPOWDER_CALCITE_ITEM =
            ITEMS.registerSimpleBlockItem("gunpowder_calcite", GUNPOWDER_CALCITE);

    // ---------------------------------------------------------------------
    // Sulfurous Deepslate
    // ---------------------------------------------------------------------

    /** Sulfurous Deepslate. Hard outer crust that isolates the geode from the surrounding stone. */
    public static final DeferredBlock<Block> SULFUROUS_DEEPSLATE =
            BLOCKS.registerSimpleBlock(
                    "sulfurous_deepslate",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DEEPSLATE)
                            .strength(3.0F)
                            .sound(SoundType.DEEPSLATE)
            );

    /** Item form of {@link #SULFUROUS_DEEPSLATE}. */
    public static final DeferredItem<BlockItem> SULFUROUS_DEEPSLATE_ITEM =
            ITEMS.registerSimpleBlockItem("sulfurous_deepslate", SULFUROUS_DEEPSLATE);

    // ---------------------------------------------------------------------
    // Creeper Core
    // ---------------------------------------------------------------------

    /**
     * Creeper Core. Placed at the geometric center of the geode.
     *
     * <p>Provisional: this is a plain block for now. It will later become a block with a
     * {@code BlockEntity} holding the persistent state that drives juvenile creeper spawning.</p>
     */
    public static final DeferredBlock<Block> CREEPER_CORE =
            BLOCKS.registerSimpleBlock(
                    "creeper_core",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW)
                            .strength(2.0F)
                            .sound(SoundType.STONE)
            );

    /** Item form of {@link #CREEPER_CORE}. */
    public static final DeferredItem<BlockItem> CREEPER_CORE_ITEM =
            ITEMS.registerSimpleBlockItem("creeper_core", CREEPER_CORE);

    // ---------------------------------------------------------------------
    // Registration
    // ---------------------------------------------------------------------

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
