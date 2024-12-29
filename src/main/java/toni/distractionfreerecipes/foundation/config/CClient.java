package toni.distractionfreerecipes.foundation.config;

import toni.lib.config.ConfigBase;

public class CClient extends ConfigBase {
    public final ConfigBool enabled = b(true, "Enable Distraction-Free Mode", "Turns the mod's effects on/off.");
    public final ConfigBool lowerOpacity = b(false, "Lower Opacity ?", "Lowers the opacity of the search bar to make it blend in more.");

    @Override
    public String getName() {
        return "client";
    }
}
