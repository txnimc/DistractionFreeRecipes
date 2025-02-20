package toni.distractionfreerecipes.foundation;

import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.client.renderer.Rect2i;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

public class EMIHelper {
    public static Rect2i getBounds(EmiScreenManager.SidebarPanel sidebarPanel, EmiDrawContext context, EmiScreenManager.ScreenSpace space) {
        return new Rect2i(space.tx, space.ty, space.tw  * 18 , space.th * 18 );
    }
}
