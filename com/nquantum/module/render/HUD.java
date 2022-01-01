package com.nquantum.module.render;

import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;

import java.util.ArrayList;

public class HUD extends Module {
    public HUD(){
        super("HUD", 0, Category.RENDER);
    }

    @Override
    public void setup(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Neverlose");
        options.add("Gamesense");
        Asyncware.instance.settingsManager.rSetting(new Setting("CSGO Mode", this, "Vanilla", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("Font Size", this, 20, 2, 100, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("CSGO", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Big", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Is Gabrik?", this, false));


    }


    @EventTarget
    public void onUpdate(EventUpdate e){
        double size = Asyncware.instance.settingsManager.getSettingByName("Font Size").getValDouble();
        Asyncware.fontSize = (int) size;
    }
}
