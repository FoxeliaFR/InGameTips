package fr.foxelia.ingametips.commands;

import com.mojang.brigadier.CommandDispatcher;
import fr.foxelia.ingametips.test.TestCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommandRegistry {

    private static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        TestCommand.register(dispatcher); // REMOVE THIS LINE BEFORE PRODUCTION
        TipCommand.register(dispatcher);
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event)
    {
        registerCommands(event.getServer().getCommands().getDispatcher());
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        registerCommands(event.getServerResources().getCommands().getDispatcher());
    }
}
