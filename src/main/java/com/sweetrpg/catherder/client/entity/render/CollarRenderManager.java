package com.sweetrpg.catherder.client.entity.render;

import com.sweetrpg.catherder.client.entity.render.layer.LayerFactory;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.common.entity.CatEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollarRenderManager {

    private static final List<LayerFactory<CatEntity, CatModel<CatEntity>>> backer = new ArrayList<>();
    private static final List<LayerFactory<CatEntity, CatModel<CatEntity>>> accessoryRendererMap = Collections.synchronizedList(backer);

    /**
     * Register a renderer for a collar type
     * Call this during {@link net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent}.
     * This method is safe to call during parallel mod loading.
     */
    public static void registerLayer(LayerFactory<CatEntity, CatModel<CatEntity>> shader) {
        accessoryRendererMap.add(shader);
    }

    @Nullable
    public static List<LayerFactory<CatEntity, CatModel<CatEntity>>> getLayers() {
        return Collections.unmodifiableList(backer);
    }
}
