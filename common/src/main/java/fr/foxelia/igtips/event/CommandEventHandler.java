package fr.foxelia.igtips.event;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import fr.foxelia.igtips.commands.TipCommand;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandEventHandler {

    public static void registerCommands() {
        CommandRegistrationEvent.EVENT.register(CommandEventHandler::onRegisterCommands);
    }

    private static void onRegisterCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        TipCommand.register(dispatcher);
    }
}
