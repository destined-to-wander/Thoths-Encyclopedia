package com.dest1ny.index;

import com.dest1ny.client.gui.screen.PyrotechnicsTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.*;

import static com.dest1ny.ThothsEncyclopedia.id;

public class ModScreenHandlers{
    public static final ScreenHandlerType<PyrotechnicsTableScreenHandler> PYROTECHNICS_TABLE = registerHandler("pyrotechnics_table", new ScreenHandlerType<>(PyrotechnicsTableScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    private static <T extends ScreenHandler> net.minecraft.screen.ScreenHandlerType<T> registerHandler(String id, ScreenHandlerType handlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, id(id), handlerType);
    }

    public static void register(){}
}
