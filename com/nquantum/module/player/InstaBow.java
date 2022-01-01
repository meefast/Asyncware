package com.nquantum.module.player;

import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class InstaBow extends Module {

    public InstaBow(){
        super("InstaBow", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate nigger){
        if (mc.thePlayer.onGround &&  mc.thePlayer.inventory.getCurrentItem() != null &&  mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && mc.gameSettings.keyBindUseItem.pressed) {
            int i = 0;
            while (i < 5) {
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(true));
                ++i;
            }
            this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.stopUsingItem();
        }
        /*
        if(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && mc.thePlayer.isUsingItem()){
            for(int i = 0; i < 50; i++){
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            }
            mc.rightClick();
          //  mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging());
        }

         */
    }
}
