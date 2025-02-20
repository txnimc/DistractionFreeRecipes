package toni.distractionfreerecipes.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.impl.client.REIRuntimeImpl;
import me.shedaniel.rei.impl.client.gui.ScreenOverlayImpl;
import me.shedaniel.rei.impl.client.gui.widget.entrylist.EntryListWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toni.distractionfreerecipes.foundation.EMIHelper;
import toni.distractionfreerecipes.foundation.GuiHelpers;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

@Mixin(value = ScreenOverlayImpl.class, priority = 500 #if FORGE , remap = false #endif)
@Pseudo
public abstract class REIOverlayMixin {
    @Shadow public abstract void queueReloadOverlay();

    @Shadow public abstract void queueReloadSearch();

    @WrapOperation(method = "renderWidgets", at = @At(value = "INVOKE", target = "Lme/shedaniel/rei/api/client/gui/widgets/Widget;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private void redirect$rendering(Widget instance, GuiGraphics graphics, int mouseX, int mouseY, float deltaTick, Operation<Void> original) {
        if (!(instance instanceof EntryListWidget))
        {
            original.call(instance, graphics, mouseX, mouseY, deltaTick);
            return;
        }

        var isEmpty = REIRuntimeImpl.getSearchField().getText().isEmpty();
        var enabled = AllConfigs.client().enabled.get();
        if(!isEmpty || !enabled)
            original.call(instance, graphics, mouseX, mouseY, deltaTick);

        var bound = ((EntryListWidget) instance).getBounds();
        GuiHelpers.searchBounds = new Rect2i(bound.x, bound.y, bound.width, bound.height);

        if (!enabled) {
            GuiHelpers.drawHideRecipesButton(graphics);
        }
        else if (isEmpty) {
            GuiHelpers.drawShowRecipesButton(graphics);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (button != InputConstants.MOUSE_BUTTON_LEFT)
            return;

        var isEmpty = REIRuntimeImpl.getSearchField().getText().isEmpty();
        var enabled = AllConfigs.client().enabled.get();

        if (!enabled) {
            if (GuiHelpers.tryClickHideRecipesButton((int) mouseX, (int) mouseY))
                cir.setReturnValue(true);
        }
        else if (isEmpty) {
            if (GuiHelpers.tryClickShowRecipesButton((int) mouseX, (int) mouseY))
            {
                queueReloadOverlay();
                cir.setReturnValue(true);
            }
        }
    }
}