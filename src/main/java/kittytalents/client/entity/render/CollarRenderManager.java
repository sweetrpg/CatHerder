package kittytalents.client.entity.render;

import kittytalents.client.entity.render.layer.LayerFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollarRenderManager {

    private static final List<LayerFactory<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>>> backer = new ArrayList<>();
    private static final List<LayerFactory<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>>> accessoryRendererMap = Collections.synchronizedList(backer);

    /**
     * Register a renderer for a collar type
     * Call this during {@link net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent}.
     * This method is safe to call during parallel mod loading.
     */
    public static void registerLayer(LayerFactory<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>> shader) {
        accessoryRendererMap.add(shader);
    }

    @Nullable
    public static List<LayerFactory<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>>> getLayers() {
        return Collections.unmodifiableList(backer);
    }
}
