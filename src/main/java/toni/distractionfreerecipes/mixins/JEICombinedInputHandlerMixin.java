package toni.distractionfreerecipes.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import mezz.jei.common.Internal;
import mezz.jei.common.input.IInternalKeyMappings;
import mezz.jei.gui.input.IUserInputHandler;
import mezz.jei.gui.input.UserInput;
import mezz.jei.gui.overlay.IngredientListOverlay;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.distractionfreerecipes.foundation.JEIAccessor;
import toni.distractionfreerecipes.foundation.JEIHelper;
import toni.distractionfreerecipes.foundation.GuiHelpers;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

import java.util.Optional;

@Mixin(targets = "mezz/jei/gui/overlay/IngredientGridWithNavigation$UserInputHandler" #if FORGE , remap = false #endif)
public class JEICombinedInputHandlerMixin {

    @Inject(method = "handleUserInput", at = @At(value = "HEAD"), cancellable = true)
    public void inject$renderOverlay(Screen screen, UserInput input, IInternalKeyMappings keyBindings, CallbackInfoReturnable<Optional<IUserInputHandler>> cir) {
        if (input.getKey().getValue() != InputConstants.MOUSE_BUTTON_LEFT)
            return;

        var list = (IngredientListOverlay) Internal.getJeiRuntime().getIngredientListOverlay();
        if (GuiHelpers.searchBounds == null)
            return;

        var isEmpty = ((JEIAccessor) list).distractionFreeRecipes$isSearchEmpty();
        var enabled = AllConfigs.client().enabled.get();

        if (!enabled) {
            if (JEIHelper.tryClickHideRecipesButton(input))
                cir.setReturnValue(Optional.of((IUserInputHandler) this));
        }
        else if (isEmpty) {
            if (!GuiHelpers.searchBounds.contains((int) input.getMouseX(), (int) input.getMouseY()))
                return;

            JEIHelper.tryClickShowRecipesButton(input);
            cir.setReturnValue(Optional.of((IUserInputHandler) this));
        }
    }
}
