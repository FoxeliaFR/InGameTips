package fr.foxelia.igtips.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.data.HistoryManager;
import fr.foxelia.igtips.data.TipHistory;
import fr.foxelia.igtips.datapack.TipRegistry;
import fr.foxelia.igtips.network.NetworkHandler;
import fr.foxelia.igtips.network.TipPacket;
import fr.foxelia.igtips.platform.PlayerLanguageHelper;
import fr.foxelia.igtips.schedule.ScheduleManager;
import fr.foxelia.igtips.tip.TranslatableTip;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.*;

public class RandomTipCommand {

    protected static int execute(CommandContext<ServerCommandSource> ctx) {
        Collection<ServerPlayerEntity> players = TipCommand.getPlayersOrError(ctx);
        if(players == null) return 0;

        String modeInput = StringArgumentType.getString(ctx, "mode");
        RandomMode mode = RandomMode.byName(modeInput);

        ServerCommandSource source = ctx.getSource();

        if(mode == null) {
            source.sendError(Text.translatable("argument.igtips.tip.random.invalid", modeInput));
            return 0;
        }

        boolean save = false;

        if(mode == RandomMode.MATCH_CONFIGURATION) {
            if(CommonConfig.isIndividualTips()) {
                mode = RandomMode.INDIVIDUAL;
            } else {
                mode = RandomMode.ALL;
            }
        }

        try {
            String savingMode = StringArgumentType.getString(ctx, "save");
            if(savingMode.equalsIgnoreCase("true")) {
                save = true;
            } else if(!savingMode.equalsIgnoreCase("false")) {
                // Send error
                source.sendError(Text.translatable("argument.igtips.boolean.invalid", savingMode));
                return 0;
            }
        } catch (IllegalArgumentException e) {
            // Argument not provided, use default
            if(mode == RandomMode.ALL  && players.size() == source.getServer().getPlayerManager().getCurrentPlayerCount()) {
                save = true;
            }
        }

        Map<ServerPlayerEntity, TranslatableTip> tips = generateTipsForPlayers(players, mode, save);

        if(tips == null) {
            sendNoAvailableTipsError(source);
        }

        // Success / Failure lists
        Pair<List<ServerPlayerEntity>, List<ServerPlayerEntity>> result = new Pair<>(new ArrayList<>(), new ArrayList<>());
        for(Map.Entry<ServerPlayerEntity, TranslatableTip> entry : tips.entrySet()) {
            TranslatableTip tip = entry.getValue();
            if(tip == null) {
                result.getRight().add(entry.getKey());
                continue;
            }
            ServerPlayerEntity player = entry.getKey();
            NetworkHandler.CHANNEL.sendToPlayer(player, new TipPacket(tip.toBasicTip(PlayerLanguageHelper.getPlayerLanguage(player))));
            result.getLeft().add(player);
        }

        if(result.getRight().isEmpty()) {
            Collection<String> playerNames = result.getLeft().stream().map(player -> player.getDisplayName().getString()).toList();
            source.sendFeedback(() -> Text.translatable("commands.igtips.tip.random.success", playerNames), true);
        } else if(result.getLeft().isEmpty()) {
            Collection<String> playerNames = result.getRight().stream().map(player -> player.getDisplayName().getString()).toList();
            source.sendError(Text.translatable("commands.igtips.tip.random.no_tips_players", playerNames));
        } else {
            Collection<String> successNames = result.getLeft().stream().map(player -> player.getDisplayName().getString()).toList();
            Collection<String> failureNames = result.getRight().stream().map(player -> player.getDisplayName().getString()).toList();
            source.sendFeedback(() -> Text.translatable("commands.igtips.tip.random.partial_success", successNames, failureNames), true);
        }
        return 1;
    }

    /**
     * Generate the tips for the players according to the selected mode
     * @param players : players to send the tips to
     * @param mode  : mode to use
     * @param saving : if we need to save the tips in the history of the players
     * @return Map of players and their tips, or null if no tip is available
     */
    private static Map<ServerPlayerEntity, TranslatableTip> generateTipsForPlayers(Collection<ServerPlayerEntity> players, RandomMode mode, boolean saving) {
        Map<ServerPlayerEntity, TranslatableTip> tips = new HashMap<>(players.size());

        switch (mode) {
            case DUMB -> {
                Identifier id = TipRegistry.getRandomTip();
                if (common(players, saving, tips, id)) return null;
            }
            case DUMB_INDIVIDUAL -> {
                for(ServerPlayerEntity player : players) {
                    Identifier id = TipRegistry.getRandomTip();
                    individualPlayer(saving, tips, player, id);
                }
            }
            case INDIVIDUAL -> {
                for(ServerPlayerEntity player : players) {
                    TipHistory history = HistoryManager.getHistoryOf(player.getUuidAsString());
                    Identifier id = history.getRandomTip(false);
                    individualPlayer(saving, tips, player, id);
                }
            }
            case ALL -> {
                TipHistory history = HistoryManager.getHistoryOf(ScheduleManager.COMMON_SCHEDULE);
                Identifier id = history.getRandomTip(false);
                if (common(players, saving, tips, id)) return null;
            }
            default -> {
                return null;
            }
        }

        return tips;
    }

    /*
     * Avoid code duplication common or individual modes
     */
    private static boolean common(Collection<ServerPlayerEntity> players, boolean saving, Map<ServerPlayerEntity, TranslatableTip> tips, Identifier id) {
        if(id == null) return true;
        TranslatableTip tip = TipRegistry.getTip(id);
        if(tip == null) return true;
        checkAndSave(saving, ScheduleManager.COMMON_SCHEDULE, id);
        for(ServerPlayerEntity player : players) {
            tips.put(player, tip);
        }
        return false;
    }

    private static void individualPlayer(boolean saving, Map<ServerPlayerEntity, TranslatableTip> tips, ServerPlayerEntity player, Identifier id) {
        if(id == null) {
            tips.put(player, null);
            return;
        }
        TranslatableTip tip = TipRegistry.getTip(id);
        if(tip == null) return;
        tips.put(player, tip);
        checkAndSave(saving, player.getUuidAsString(), id);
    }

    /**
     * Check if we need to save the tip in the history of the player and do it
     * @param saving : if we need to save the tip
     * @param uuid  : UUID of the player (or common schedule ID)
     * @param tipId : Identifier of the tip to save
     */
    private static void checkAndSave(boolean saving, String uuid, Identifier tipId) {
        if(saving) {
            TipHistory history = HistoryManager.getHistoryOf(uuid);
            history.addTip(tipId);
            history.saveHistory();
        }
    }


    /**
     * Send an error message to the command source when no tip is available
     * @param source : Command source
     * @return 0
     */
    private static int sendNoAvailableTipsError(ServerCommandSource source) {
        source.sendError(Text.translatable("commands.igtips.tip.random.no_tips"));
        return 0;
    }

    /**
     * Modes for random tip command<br>
     * <br>dumb            : Random tip for all players
     * <br>dumb_individual : Random tip for each player
     * <br>individual      : Random tip for each player, avoiding recently sent tips
     * <br>all             : Random tip for all players, avoiding recently sent tips
     * <br>configlike      : Match the configuration (individual or all)
     */
    enum RandomMode {
        DUMB("dumb_global"),
        DUMB_INDIVIDUAL("dumb_individual"),
        INDIVIDUAL("individual"),
        MATCH_CONFIGURATION("configlike"),
        ALL("all");

        private final String name;

        RandomMode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static RandomMode byName(String name) {
            for (RandomMode type : values()) {
                if (type.getName().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
        }
    }

}


