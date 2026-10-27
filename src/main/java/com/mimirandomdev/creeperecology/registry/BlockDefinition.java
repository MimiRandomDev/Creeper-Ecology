package com.mimirandomdev.creeperecology.registry;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

/**
 * Data table describing every block added by Creeper Ecology.
 *
 * <p>Each constant is one row: the registry name, a supplier of the block properties and a
 * factory that builds the block. {@link ModBlocks} iterates over {@link #values()} to register
 * each block together with its block item, so adding a block only requires adding a constant here.</p>
 *
 * <p>The registry name is stored explicitly instead of being derived from the constant name.
 * Renaming a Java constant must never silently change a registry id, because that would break
 * existing worlds.</p>
 */
public enum BlockDefinition {

    /** Interior floor of the nest geode and scattered patches outside it. */
    GUNPOWDER_SAND(
            "gunpowder_sand",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .strength(0.5F)
                    .sound(SoundType.SAND),
            properties -> new ColoredFallingBlock(
                    new ColorRGBA(0xE5C84B),
                    properties
            )
    ),

    /** Crystalline lining of the inner wall of the geode. */
    GUNPOWDER_CALCITE(
            "gunpowder_calcite",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(1.5F)
                    .sound(SoundType.CALCITE),
            Block::new
    ),

    /** Hard outer crust that isolates the geode from the surrounding stone. */
    SULFUROUS_DEEPSLATE(
            "sulfurous_deepslate",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(3.0F)
                    .sound(SoundType.DEEPSLATE),
            Block::new
    ),

    /**
     * Geometric center of the geode, origin of juvenile creepers.
     * Provisional: it will get its own block class with a {@code BlockEntity}.
     */
    CREEPER_CORE(
            "creeper_core",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(2.0F)
                    .sound(SoundType.STONE),
            Block::new
    );

    private final String name;
    private final Supplier<BlockBehaviour.Properties> propertiesSupplier;
    private final Function<BlockBehaviour.Properties, Block> blockFactory;

    /**
     * Creates a block definition.
     *
     * @param name               registry name, without namespace
     * @param propertiesSupplier supplier that creates a fresh set of properties on every call,
     *                           because a properties instance must not be shared between blocks
     * @param blockFactory       factory that builds the block from its properties
     */
    BlockDefinition(
            String name,
            Supplier<BlockBehaviour.Properties> propertiesSupplier,
            Function<BlockBehaviour.Properties, Block> blockFactory
    ) {
        this.name = name;
        this.propertiesSupplier = propertiesSupplier;
        this.blockFactory = blockFactory;
    }

    /** @return the registry name of the block, without namespace */
    public String getName() {
        return name;
    }

    /** @return the supplier that creates the block properties */
    public Supplier<BlockBehaviour.Properties> getPropertiesSupplier() {
        return propertiesSupplier;
    }

    /** @return the factory that builds the block from its properties */
    public Function<BlockBehaviour.Properties, Block> getBlockFactory() {
        return blockFactory;
    }
}
