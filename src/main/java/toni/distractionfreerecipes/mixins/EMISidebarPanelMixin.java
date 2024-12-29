package toni.distractionfreerecipes.mixins;

import dev.emi.emi.config.SidebarType;
import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

@Mixin(value = EmiScreenManager.SidebarPanel.class, remap = false)
public class EMISidebarPanelMixin {
    @Inject(method = "isVisible", at = @At("HEAD"), cancellable = true)
    private void beforeRender(CallbackInfoReturnable<Boolean> cir) {
        if (((EmiScreenManager.SidebarPanel)(Object)this).getType() == SidebarType.INDEX && EmiScreenManager.search.getValue().isEmpty() && AllConfigs.client().enabled.get()) {
            cir.setReturnValue(false);
            return;
        }
    }
}
