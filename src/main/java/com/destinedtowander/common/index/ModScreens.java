package com.destinedtowander.common.index;

import com.destinedtowander.client.gui.screen.ingame.KilnScreen;
import com.destinedtowander.client.gui.screen.ingame.PyrotechnicsTableScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreens {
    static{
        HandledScreens.register(ModScreenHandlers.PYROTECHNICS_TABLE, PyrotechnicsTableScreen::new);
        HandledScreens.register(ModScreenHandlers.KILN, KilnScreen::new);
    }

    public static void register(){}
}
