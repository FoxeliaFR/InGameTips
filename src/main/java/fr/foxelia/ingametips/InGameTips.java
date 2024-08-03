package fr.foxelia.ingametips;

import com.mojang.logging.LogUtils;
import fr.foxelia.ingametips.commands.CommandRegistry;
import fr.foxelia.ingametips.config.InGameTipsClientConfigs;
import fr.foxelia.ingametips.config.InGameTipsCommonConfigs;
import fr.foxelia.ingametips.data.TipHistory;
import fr.foxelia.ingametips.datapack.TipLoader;
import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.schedule.ScheduleSubscriber;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(InGameTips.MOD_ID)
public class InGameTips
{

    /*
     * TODO:
     *  - Server config : Scheduled tips (every x minutes) / Disable mod specific tips
     */

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "igtips";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static MinecraftServer SERVER = null;

    public InGameTips()
    {
        // Register the config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, InGameTipsClientConfigs.CLIENT_CONFIG, MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, InGameTipsCommonConfigs.COMMON_CONFIG, MOD_ID + "-common.toml");

        // Register the packet handler
        InGameTipsPacketHandler.registerPackets();

        // Register ourselves for server and other game events we are interested in
        IEventBus modEventBus = MinecraftForge.EVENT_BUS;

        TipLoader.register(modEventBus);
        modEventBus.register(CommandRegistry.class);
        modEventBus.addListener(InGameTips::onServerStarting);
        modEventBus.register(TipHistory.class);
        modEventBus.register(ScheduleSubscriber.class);
    }

    public static void onServerStarting(ServerStartingEvent event)
    {
        SERVER = event.getServer();
        TipHistory.registerWorldData(event.getServer().getWorldPath(LevelResource.ROOT));
    }
}
