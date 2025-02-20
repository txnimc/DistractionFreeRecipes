package toni.distractionfreerecipes.foundation;

import mezz.jei.gui.input.UserInput;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

public class JEIHelper {

    public static void tryClickShowRecipesButton(UserInput input) {
        if (!AllConfigs.client().showToggleButton.get())
            return;

        var bound = GuiHelpers.getShowRecipesBounds();

        if (bound.contains((int) input.getMouseX(), (int) input.getMouseY())) {
            AllConfigs.client().enabled.set(false);
        }
    }


    public static boolean tryClickHideRecipesButton(UserInput input) {
        if (!AllConfigs.client().showToggleButton.get())
            return false;

        var bound = GuiHelpers.getHideRecipesBounds();

        if (bound.contains((int) input.getMouseX(), (int) input.getMouseY())) {
            AllConfigs.client().enabled.set(true);
            return true;
        }

        return false;
    }
}
