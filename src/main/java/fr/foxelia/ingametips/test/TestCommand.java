package fr.foxelia.ingametips.test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import fr.foxelia.ingametips.PopUpManager;
import fr.foxelia.ingametips.client.PopUp;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

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
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> TestCommand::test);

        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static void test() {
        // Test client-side code here
        PopUpManager.getInstance().queue(new PopUp());

    }
}
