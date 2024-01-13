package com.sweetrpg.catherder.common.util;

import com.sweetrpg.catherder.api.registry.IDyeMaterial;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import net.minecraft.util.Tuple;

import java.util.Arrays;
import java.util.List;

import static com.sweetrpg.catherder.common.registry.ModMaterials.*;

public class ColorDyeUtil {

    private static final List<Tuple<IDyeMaterial, IColorMaterial>> colorDyeMapping = Arrays.asList(
            new Tuple<>(WHITE_DYE.get(), WHITE_WOOL.get()),
            new Tuple<>(BLACK_DYE.get(), BLACK_WOOL.get()),
            new Tuple<>(ORANGE_DYE.get(), ORANGE_WOOL.get()),
            new Tuple<>(BLUE_DYE.get(), BLUE_WOOL.get()),
            new Tuple<>(GREEN_DYE.get(), GREEN_WOOL.get()),
            new Tuple<>(YELLOW_DYE.get(), YELLOW_WOOL.get()),
            new Tuple<>(RED_DYE.get(), RED_WOOL.get()),
            new Tuple<>(PINK_DYE.get(), PINK_WOOL.get()),
            new Tuple<>(PURPLE_DYE.get(), PURPLE_WOOL.get()),
            new Tuple<>(BROWN_DYE.get(), BROWN_WOOL.get()),
            new Tuple<>(LIGHT_BLUE_DYE.get(), LIGHT_BLUE_WOOL.get()),
            new Tuple<>(GRAY_DYE.get(), GRAY_WOOL.get()),
            new Tuple<>(LIGHT_GRAY_DYE.get(), LIGHT_GRAY_WOOL.get()),
            new Tuple<>(LIME_DYE.get(), LIME_WOOL.get()),
            new Tuple<>(CYAN_DYE.get(), CYAN_WOOL.get()),
            new Tuple<>(MAGENTA_DYE.get(), MAGENTA_WOOL.get())
    );

    public static IColorMaterial getColorFromDye(IDyeMaterial dye) {
        for (Tuple<IDyeMaterial, IColorMaterial> colorTuple : colorDyeMapping) {
            if (colorTuple.getA().equals(dye)) {
                return colorTuple.getB();
            }
        }

        return null;
    }
}
