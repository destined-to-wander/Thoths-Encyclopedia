package com.destinedtowander.common.index;

import com.destinedtowander.common.screens.PyrotechnicsTableScreenHandler;
import com.destinedtowander.common.screens.KilnScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.*;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModScreenHandlers{
    public static final ScreenHandlerType<PyrotechnicsTableScreenHandler> PYROTECHNICS_TABLE = registerHandler("pyrotechnics_table", new ScreenHandlerType<>(PyrotechnicsTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    public static final ScreenHandlerType<KilnScreenHandler> KILN = registerHandler("kiln", new ScreenHandlerType<>(KilnScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    private static <T extends ScreenHandler> net.minecraft.screen.ScreenHandlerType<T> registerHandler(String id, ScreenHandlerType handlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, id(id), handlerType);
    }

    public static void register(){}
}
