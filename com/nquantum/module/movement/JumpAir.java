package com.nquantum.module.movement;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;

import java.util.ArrayList;

public class JumpAir extends Module {


    public JumpAir() {
        super("JumpAir", 0, Category.MOVEMENT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();

    }

    @EventTarget
    public void onUpdate(EventUpdate event) {

        this.setDisplayName("JumpAir");

        mc.thePlayer.onGround = true;

    }
}
