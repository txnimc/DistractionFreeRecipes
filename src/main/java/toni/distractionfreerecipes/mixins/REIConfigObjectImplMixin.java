package toni.distractionfreerecipes.mixins;

import me.shedaniel.rei.impl.client.config.ConfigObjectImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

@Mixin(value = ConfigObjectImpl.class, remap = false)
public class REIConfigObjectImplMixin {
   @Inject(method = "isHidingEntryPanelIfIdle", at = @At("HEAD"), cancellable = true)
    void isHidingEntryPanelIfIdle(CallbackInfoReturnable<Boolean> cir) {
       if (AllConfigs.client().enabled.get())
        cir.setReturnValue(true);
   }
}