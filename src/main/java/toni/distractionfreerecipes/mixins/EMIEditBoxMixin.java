package toni.distractionfreerecipes.mixins;

import dev.emi.emi.screen.widget.EmiSearchWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

@Mixin(EditBox.class)
public class EMIEditBoxMixin {
    #if mc >= 211
    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private void beforeRender(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, int w, int h) {
        if ((Object) this instanceof EmiSearchWidget) {
            if (AllConfigs.client().enabled.get() && AllConfigs.client().lowerOpacity.get())
            {
                var color = FastColor.ARGB32.color(70, 0, 0, 0);
                instance.fill(x, y, x + w, y + h, color);
                instance.fill(x + 1, y + 1, x + w - 1, y + h - 1, color);
                return;
            }
        }

        instance.blitSprite(resourceLocation, x, y, w, h);
    }
    #else
    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), index = 4)
    private int beforeRender(int minX) {
        if ((Object) this instanceof EmiSearchWidget) {
            if (AllConfigs.client().enabled.get() && AllConfigs.client().lowerOpacity.get())
                return FastColor.ARGB32.color(55, 0, 0, 0);
        }

        return minX;
    }
    #endif
}
