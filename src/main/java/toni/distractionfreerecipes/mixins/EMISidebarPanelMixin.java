package toni.distractionfreerecipes.mixins;

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

@Mixin(value = EmiScreenManager.SidebarPanel.class, remap = false)
public class EMISidebarPanelMixin {
    @Shadow public EmiScreenManager.ScreenSpace space;

    @Inject(method = "isVisible", at = @At("HEAD"), cancellable = true)
    private void beforeRender(CallbackInfoReturnable<Boolean> cir) {
        if (((EmiScreenManager.SidebarPanel)(Object)this).getType() == SidebarType.INDEX && EmiScreenManager.search.getValue().isEmpty() && AllConfigs.client().enabled.get()) {
            cir.setReturnValue(false);
            return;
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void beforeRender(EmiDrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!(((EmiScreenManager.SidebarPanel)(Object)this).getType() == SidebarType.INDEX))
            return;

        var isEmpty = EmiScreenManager.search.getValue().isEmpty();
        var enabled = AllConfigs.client().enabled.get();
        GuiHelpers.searchBounds = EMIHelper.getBounds((EmiScreenManager.SidebarPanel) (Object) this, context, space);

        if (!enabled) {
            GuiHelpers.drawHideRecipesButton(context.raw());
        }
        else if (isEmpty) {
            GuiHelpers.drawShowRecipesButton(context.raw());
            ci.cancel();
        }

    }
}
