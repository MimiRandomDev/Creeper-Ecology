package com.mimirandomdev.creeperecology;

import org.slf4j.Logger;

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
 * <p>Blocks and block items live in {@link ModBlocks}. This class only wires the registers to the
 * mod event bus and defines the mod's creative tab.</p>
 */
@Mod(CreeperEcology.MODID)
public class CreeperEcology {

    /** Mod id. Must match the entry in META-INF/neoforge.mods.toml. */
    public static final String MODID = "creeperecology";

    /** Shared logger for the whole mod. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /** Deferred register for creative tabs, under the "creeperecology" namespace. */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    /** Main creative tab ("creeperecology:main"), placed before the combat tab. */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
                    // Language key for the tab title, defined in the lang files.
                    .title(Component.translatable("itemGroup.creeperecology"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModBlocks.GUNPOWDER_SAND.asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // A DeferredBlock is an ItemLike, so it can be accepted directly.
                        output.accept(ModBlocks.GUNPOWDER_SAND);
                        output.accept(ModBlocks.GUNPOWDER_CALCITE);
                        output.accept(ModBlocks.SULFUROUS_DEEPSLATE);
                        output.accept(ModBlocks.CREEPER_CORE);
                    }).build());

    /**
     * Mod constructor. FML injects the {@link IEventBus} and {@link ModContainer} parameters.
     *
     * @param modEventBus  the mod event bus
     * @param modContainer the container of this mod
     */
    public CreeperEcology(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        // Without these calls nothing gets registered in the game.
        ModBlocks.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Needed because this class has @SubscribeEvent methods (onServerStarting).
        NeoForge.EVENT_BUS.register(this);

        // Creates and loads the common config file.
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
