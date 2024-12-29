package toni.distractionfreerecipes.foundation.data;

#if FABRIC
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import toni.distractionfreerecipes.DistractionFreeRecipes;

public class DistractionFreeRecipesDatagen  implements DataGeneratorEntrypoint {

    @Override
    public String getEffectiveModId() {
        return DistractionFreeRecipes.ID;
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(ConfigLangDatagen::new);
    }
}
#endif