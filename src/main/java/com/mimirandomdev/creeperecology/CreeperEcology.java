package com.mimirandomdev.creeperecology;

import org.slf4j.Logger;

import com.mimirandomdev.creeperecology.registry.BlockDefinition;
import com.mimirandomdev.creeperecology.registry.ModBlocks;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Main entry point of the Creeper Ecology mod.
 *
 * <p>Blocks and their associated items are registered through {@link ModBlocks}.
 * This class connects the registries to the mod event bus and defines the
 * mod's creative tab.</p>
 */
@Mod(CreeperEcology.MODID)
public class CreeperEcology {

    /** The mod ID. Must match the entry in META-INF/neoforge.mods.toml. */
    public static final String MODID = "creeperecology";

    /** Shared logger for the mod. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /** Deferred register for the mod's creative tabs. */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    /**
     * Main creative tab for Creeper Ecology.
     *
     * <p>Its icon is the gunpowder sand block, and its contents are generated
     * automatically from the constants declared in {@link BlockDefinition}.</p>
     */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.creeperecology"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModBlocks.getBlock(BlockDefinition.GUNPOWDER_SAND)
                            .asItem()
                            .getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (BlockDefinition definition : BlockDefinition.values()) {
                            output.accept(ModBlocks.getBlock(definition));
                        }
                    })
                    .build());

    /**
     * Initializes the mod and registers its event listeners and registries.
     *
     * @param modEventBus  the event bus provided by NeoForge for this mod
     * @param modContainer the container associated with this mod
     */
    public CreeperEcology(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        // Register blocks and their associated items.
        ModBlocks.register(modEventBus);

        // Register the creative tab.
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register this instance on the NeoForge event bus.
        NeoForge.EVENT_BUS.register(this);

        // Register the common configuration.
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * Performs common setup tasks.
     *
     * @param event the common setup event
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(),
                Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach(
                item -> LOGGER.info("ITEM >> {}", item)
        );
    }

    /**
     * Handles the server starting event.
     *
     * @param event the server starting event
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
