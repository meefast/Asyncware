package com.nquantum.module.player;

import cf.nquan.util.Timer;
import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.sun.javafx.runtime.async.AbstractAsyncOperation;
import com.sun.org.apache.xpath.internal.operations.Mod;
import nig.hero.settings.Setting;

import java.util.ArrayList;

public class Scaffold2 extends Module {
    private Timer placeDelay;

    private Object[] placeInfo;

    private int slot;

    private int stage;

    private boolean canPlace;

    public Scaffold2(){
        super("Scaffold2", 0, Category.PLAYER);
    }

    @Override
    public void setup() {
        super.setup();

        ArrayList<String> nigger = new ArrayList<>();
        nigger.add("Watchdog");
        nigger.add("Kokscraft");
        nigger.add("AAC");
        nigger.add("NCP");
        nigger.add("Intave");



        Asyncware.instance.settingsManager.rSetting(new Setting("Sprint", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Expand", this, 0, 0, 5, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Sneak", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Silent", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Delay (MS)", this, 20, 1, 1000, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Timer Speed", this, 1, 0.1, 3, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Scaffold Rotations", this, "Watchdog", nigger));


    }
}
