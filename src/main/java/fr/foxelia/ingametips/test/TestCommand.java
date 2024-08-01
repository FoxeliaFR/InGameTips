package fr.foxelia.ingametips.test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import fr.foxelia.ingametips.client.BasicTip;
import fr.foxelia.ingametips.client.TranslatableTip;
import fr.foxelia.ingametips.datapack.TipRegistry;
import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.network.TipPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * REMOVE THIS CLASS
 * BEFORE PRODUCTION
 */
public class TestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("test_debug")
                .executes(TestCommand::execute);

        dispatcher.register(command);
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if(source.getEntity() == null) {
            return 0;
        }
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> TestCommand::test);
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static void test() {
        // Test client-side code here
    }
}
