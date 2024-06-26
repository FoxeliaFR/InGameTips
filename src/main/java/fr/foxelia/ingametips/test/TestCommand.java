package fr.foxelia.ingametips.test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import fr.foxelia.ingametips.PopUpManager;
import fr.foxelia.ingametips.client.PopUp;
import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.network.TipPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;

/**
 * REMOVE THIS CLASS
 * BEFORE PRODUCTION
 */
public class TestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("test")
                .executes(TestCommand::execute);

        dispatcher.register(command);
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if(source.getEntity() == null) {
            return 0;
        }
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> TestCommand::test);
        TipPacket packet = new TipPacket(new PopUp("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", 5000));
        InGameTipsPacketHandler.CHANNEL.sendTo(packet, source.getPlayer().connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static void test() {
        // Test client-side code here
    }
}
