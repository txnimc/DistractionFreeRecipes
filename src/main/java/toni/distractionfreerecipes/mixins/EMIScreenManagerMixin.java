package toni.distractionfreerecipes.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.distractionfreerecipes.foundation.EMIHelper;
import toni.distractionfreerecipes.foundation.GuiHelpers;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

import static dev.emi.emi.screen.EmiScreenManager.lastMouseX;


@Mixin(value = EmiScreenManager.class, remap = false)
public class EMIScreenManagerMixin {

    @Shadow public static int lastMouseY;

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private static void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (button != InputConstants.MOUSE_BUTTON_LEFT)
            return;

        var isEmpty = EmiScreenManager.search.getValue().isEmpty();
        var enabled = AllConfigs.client().enabled.get();

        if (!enabled) {
            if (GuiHelpers.tryClickHideRecipesButton(lastMouseX, lastMouseY))
                cir.setReturnValue(true);
        }
        else if (isEmpty) {
            if (GuiHelpers.tryClickShowRecipesButton(lastMouseX, lastMouseY))
                cir.setReturnValue(true);
        }
    }
}