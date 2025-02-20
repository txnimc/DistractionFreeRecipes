package toni.distractionfreerecipes.foundation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.util.FastColor;
import toni.distractionfreerecipes.foundation.config.AllConfigs;

public class GuiHelpers {

    public static Rect2i searchBounds = new Rect2i(0, 0, 0, 0);

    public static void drawShowRecipesButton(GuiGraphics guiGraphics) {
        var toggle = getShowRecipesBounds();
        var font = Minecraft.getInstance().font;

        if (AllConfigs.client().showText.get())
        {
            var bound = searchBounds;
            var str = "Search to View Recipes";
            var fontWidth = font.width(str);

            guiGraphics.drawString(
                font,
                str,
                bound.getX() + ((bound.getWidth() - fontWidth) / 2),
                toggle.getY() - 20,
                FastColor.ARGB32.color(128, 200, 200, 200));
        }

        if (AllConfigs.client().showToggleButton.get())
        {
            guiGraphics.fill(toggle.getX(), toggle.getY(), toggle.getX() + toggle.getWidth(), toggle.getY() + toggle.getHeight(), FastColor.ARGB32.color(50,0,0,0));

            var toggleStr = "Show Recipes";
            var toggleFontWidth = font.width(toggleStr);

            guiGraphics.drawString(
                font,
                toggleStr,
                toggle.getX() + ((toggle.getWidth() - toggleFontWidth) / 2),
                toggle.getY() + 4,
                FastColor.ARGB32.color(128, 200, 200, 200));
        }
    }

    public static Rect2i getShowRecipesBounds() {
        var bound = searchBounds;

        var x = bound.getX() + bound.getWidth() / 2;
        var y = bound.getY() + bound.getHeight() / 2;
        var width = bound.getWidth();

        //return new ImmutableRect2i(x - 10, y, 20, 20);

        return new Rect2i((int) (x - Math.min(50, (int) (width * 0.25f))), Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + 20, Math.min(100, (int) (width * 0.5f)), 15);
    }

    public static Rect2i getHideRecipesBounds() {
        var bound = searchBounds;

        var x = bound.getX() + bound.getWidth() / 2;
        var y = bound.getY() + bound.getHeight();
        var width = bound.getWidth();

        //return new ImmutableRect2i(x - 10, y, 20, 20);

        var offset = Math.max(0, (int) (width * 0.5f) - 100);
        return new Rect2i((int) (x - (width * 1.0f) - 5 + offset), 8, Math.min(100, (int) (width * 0.5f)), 15);
    }


    public static void drawHideRecipesButton(GuiGraphics guiGraphics) {
        var toggle = getHideRecipesBounds();
        var font = Minecraft.getInstance().font;

        guiGraphics.fill(toggle.getX(), toggle.getY(), toggle.getX() + toggle.getWidth(), toggle.getY() + toggle.getHeight(), FastColor.ARGB32.color(50,0,0,0));

        var toggleStr = "Hide Recipes";
        var toggleFontWidth = font.width(toggleStr);

        guiGraphics.drawString(
            font,
            toggleStr,
            toggle.getX() + ((toggle.getWidth() - toggleFontWidth) / 2),
            toggle.getY() + 4,
            FastColor.ARGB32.color(128, 200, 200, 200));
    }

    public static boolean tryClickShowRecipesButton(int lastMouseX, int lastMouseY) {
        if (!AllConfigs.client().showToggleButton.get())
            return false;

        var bound = GuiHelpers.getShowRecipesBounds();

        if (bound.contains(lastMouseX, lastMouseY)) {
            AllConfigs.client().enabled.set(false);
            return true;
        }

        return false;
    }


    public static boolean tryClickHideRecipesButton(int lastMouseX, int lastMouseY) {
        if (!AllConfigs.client().showToggleButton.get())
            return false;

        var bound = GuiHelpers.getHideRecipesBounds();
        if (bound.contains(lastMouseX, lastMouseY)) {
            AllConfigs.client().enabled.set(true);
            return true;
        }

        return false;
    }
}
