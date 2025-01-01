package fr.foxelia.igtips.client.addons.clothconfig;

import fr.foxelia.igtips.config.CommonConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class InGameTipsMainConfigScreen extends Screen {

    private final Screen parent; // Parent screen (for returning)

    public InGameTipsMainConfigScreen(Screen parent) {
        super(Text.translatable("config.igtips.main.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        // Button dimensions and position
        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = width / 2;
        int centerY = height / 2;

        // Client settings button
        addDrawableChild(ButtonWidget.builder(Text.translatable("config.igtips.main.button.client"), button -> {
            client.setScreen(InGameTipsClothClientConfigScreen.createConfigScreen(this));
        }).dimensions(centerX - buttonWidth / 2, centerY - 30, buttonWidth, buttonHeight).build());

        // Check if common settings is available
        boolean isCommonSettingsAvailable = isCommonSettingsEnabled();

        // Common settings button
        ButtonWidget commonSettingsButton = ButtonWidget.builder(
                isCommonSettingsAvailable
                        ? Text.translatable("config.igtips.main.button.common.available") // If isCommonSettingsEnabled() returns true
                        : Text.translatable("config.igtips.main.button.common.unavailable"), // If isCommonSettingsEnabled() returns false
                button -> client.setScreen(InGameTipsClothCommonConfigScreen.createConfigScreen(this))
                ).dimensions(centerX - buttonWidth / 2, centerY + 10, buttonWidth, buttonHeight).build();

        // Disable the button if common settings is not available
        if (!isCommonSettingsAvailable) {
            commonSettingsButton.active = false;
        }

        addDrawableChild(commonSettingsButton);

        // Add done button
        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> client.setScreen(parent))
                .dimensions(centerX - buttonWidth / 2, centerY + 50, buttonWidth, buttonHeight)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    private static boolean isCommonSettingsEnabled() {
        return CommonConfigManager.SERVER_CONFIG != null || isLocal();
    }

    protected static boolean isLocal() {
        MinecraftClient mc = MinecraftClient.getInstance();
        return mc.getNetworkHandler() == null || mc.isInSingleplayer();
    }

}
