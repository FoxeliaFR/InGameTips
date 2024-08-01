package fr.foxelia.ingametips;

import com.mojang.logging.LogUtils;
import fr.foxelia.ingametips.datapack.TipLoader;
import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.subscribers.PopUpRenderer;
import fr.foxelia.ingametips.test.TestCommand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(InGameTips.MOD_ID)
public class InGameTips
{

    /*
     * TODO:
     *  - Client config : enable/disable tips
     *  - Server config : Scheduled tips (every x minutes) / Disable mod specific tips
     *  - Optimize rendering (draw text on a texture)
     *  - Command to display a tip
     */

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "igtips";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public InGameTips()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        modEventBus.addListener(this::commonSetup);

        // Register the packet handler
        InGameTipsPacketHandler.registerPackets();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        TipLoader.register(MinecraftForge.EVENT_BUS);

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        TestCommand.register(event.getServer().getCommands().getDispatcher()); // REMOVE THIS LINE BEFORE PRODUCTION
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            new PopUpManager();
            IEventBus eventBus = MinecraftForge.EVENT_BUS;
            eventBus.register(new PopUpRenderer());
        }
    }
}
