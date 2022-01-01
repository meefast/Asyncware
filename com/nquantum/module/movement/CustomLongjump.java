package com.nquantum.module.movement;

import cf.nquan.util.PacketUtil;
import cf.nquan.util.Timer;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class CustomLongjump extends Module {
    private Timer timer = new Timer();
    public CustomLongjump(){
        super("Custom LongJump", Keyboard.KEY_O, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        float direction = mc.thePlayer.rotationYaw + ((mc.thePlayer.moveForward < 0) ? 180 : 0) + ((mc.thePlayer.moveStrafing > 0 ) ? (-90F * ((mc.thePlayer.moveForward < 0) ? -.5F : ((mc.thePlayer.moveForward > 0) ? .4F : 1F))) : 0);
        float xDir = (float) Math.cos((direction + 90F) * Math.PI / 180);
        float zDir = (float) Math.sin((direction + 90F) * Math.PI / 180);

        float speed = 1.30f;

        if(this.timer.hasTimeElapsed(10L, true)){
            speed += Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionY + 0.14F) + speed;
        }

        if(mc.thePlayer.hurtTime > 1 & mc.thePlayer.fallDistance < 1){
            PacketUtil.sendPacketPlayer(new C03PacketPlayer());
            mc.thePlayer.motionX = xDir * speed;
            mc.thePlayer.motionZ = zDir * speed;


        }



    }
}
