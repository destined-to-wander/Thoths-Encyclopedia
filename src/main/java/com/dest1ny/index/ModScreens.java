package com.dest1ny.index;

import com.dest1ny.client.gui.screen.ingame.PyrotechnicsTableScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreens {
    static{
        HandledScreens.register(ModScreenHandlers.PYROTECHNICS_TABLE, PyrotechnicsTableScreen::new);
    }

    public static void register(){}
}
