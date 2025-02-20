package toni.distractionfreerecipes.mixins;

import mezz.jei.gui.elements.GuiIconToggleButton;
import mezz.jei.gui.input.GuiTextFieldFilter;
import mezz.jei.gui.overlay.IngredientGridWithNavigation;
import mezz.jei.gui.overlay.IngredientListOverlay;
import mezz.jei.gui.overlay.ScreenPropertiesCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toni.distractionfreerecipes.foundation.JEIAccessor;
import toni.distractionfreerecipes.foundation.GuiHelpers;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

@Mixin(value = IngredientListOverlay.class #if FORGE , remap = false #endif)
public class JEIIngredientListOverlayMixin implements JEIAccessor {
    @Shadow @Final private GuiTextFieldFilter searchField;
    @Shadow @Final private GuiIconToggleButton configButton;
    @Shadow @Final private ScreenPropertiesCache screenPropertiesCache;
    @Shadow @Final private IngredientGridWithNavigation contents;

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lmezz/jei/gui/overlay/IngredientGridWithNavigation;draw(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/GuiGraphics;IIF)V"), cancellable = true)
    public void inject$renderOverlay(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (GuiHelpers.searchBounds == null) {
            GuiHelpers.searchBounds = contents.getBackgroundArea().toMutable();
        } else {
            var bounds = contents.getBackgroundArea().toMutable();
            GuiHelpers.searchBounds.setPosition(bounds.getX(), bounds.getY());
            GuiHelpers.searchBounds.setHeight(bounds.getHeight());
            GuiHelpers.searchBounds.setWidth(bounds.getWidth());
        }

        if (screenPropertiesCache.hasValidScreen())
            configButton.draw(guiGraphics, mouseX, mouseY, partialTicks);

        var isEmpty = distractionFreeRecipes$isSearchEmpty();
        var enabled = AllConfigs.client().enabled.get();

        if (!enabled) {
            GuiHelpers.drawHideRecipesButton(guiGraphics);
        }
        else if (isEmpty) {
            GuiHelpers.drawShowRecipesButton(guiGraphics);
            ci.cancel();
        }
    }

    @Inject(method = "drawTooltips", at = @At(value = "HEAD"), cancellable = true)
    public void inject$renderOverlay(Minecraft minecraft, GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci) {
        if (AllConfigs.client().enabled.get() && searchField.getValue().isEmpty())
            ci.cancel();
    }

    @Override
    public boolean distractionFreeRecipes$isSearchEmpty() {
        return searchField.getValue().isEmpty();
    }
}