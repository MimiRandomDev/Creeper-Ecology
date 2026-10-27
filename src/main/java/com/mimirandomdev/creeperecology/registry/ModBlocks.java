package com.mimirandomdev.creeperecology.registry;

import com.mimirandomdev.creeperecology.CreeperEcology;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

/**
 * Central registry for all blocks and their associated items in Creeper Ecology.
 *
 * <p>Block definitions are declared in {@link BlockDefinition} and registered
 * automatically during class initialization. Each block is stored in an
 * {@link EnumMap} for lookup by its definition.</p>
 *
 * <p>Order matters in this class: {@link #BLOCKS}, {@link #ITEMS} and the map must be declared
 * before the static initializer that fills them, otherwise they would still be {@code null}.</p>
 */
public final class ModBlocks {

    private ModBlocks() {
        // Static registry holder, not meant to be instantiated.
    }

    /** Deferred register for all blocks of the mod. */
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreeperEcology.MODID);

    /** Deferred register for all items of the mod. */
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CreeperEcology.MODID);

    /** Registered block of each definition, filled by the static initializer below. */
    private static final Map<BlockDefinition, DeferredBlock<Block>> BLOCKS_BY_DEFINITION =
            new EnumMap<>(BlockDefinition.class);

    /**
     * Registers a block and its item using the given definition.
     *
     * @param definition the definition with the name, properties and factory of the block
     * @return the deferred holder of the registered block
     */
    private static DeferredBlock<Block> registerBlockWithItem(BlockDefinition definition) {
        String name = definition.getName();

        BlockBehaviour.Properties properties = definition.getPropertiesSupplier().get();

        DeferredBlock<Block> block = BLOCKS.registerBlock(
                name,
                definition.getBlockFactory(),
                properties
        );

        ITEMS.registerSimpleBlockItem(name, block);

        return block;
    }

    // Registers every constant of BlockDefinition. Must stay below the fields it uses.
    static {
        for (BlockDefinition definition : BlockDefinition.values()) {
            BLOCKS_BY_DEFINITION.put(definition, registerBlockWithItem(definition));
        }
    }

    /**
     * Returns the registered block of the given definition.
     * Every constant of {@link BlockDefinition} is registered, so the result is never {@code null}.
     *
     * @param definition the definition identifying the block
     * @return the deferred holder of the registered block
     */
    public static DeferredBlock<Block> getBlock(BlockDefinition definition) {
        return BLOCKS_BY_DEFINITION.get(definition);
    }

    /**
     * Attaches the block and item registers to the mod event bus.
     * Must be called once from the mod constructor.
     *
     * @param modEventBus the mod event bus provided by the mod constructor
     */
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
