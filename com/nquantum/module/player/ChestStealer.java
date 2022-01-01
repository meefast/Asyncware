package com.nquantum.module.player;

import cf.nquan.util.ESPUtil;
import cf.nquan.util.Timer;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntityChest;

public class ChestStealer extends Module {

    private Timer timer = new Timer();
    private int picked;
    private boolean fast;

    public ChestStealer(){
        super("ChestStealer", 0, Category.PLAYER);
    }

    @EventTarget
    public void onRender3D(Event3D e){
        for(Object o : mc.theWorld.loadedTileEntityList){
            if(o instanceof TileEntityChest){
                ESPUtil.blockESPBox(((TileEntityChest) o).getPos());
            }
        }
    }
    @EventTarget
    public void onUpdate(EventUpdate e){

        if (mc.theWorld != null) {
            if (!(this.mc.currentScreen instanceof GuiChest)) {
                this.picked = 0;
                this.fast = true;
            } else {
                if (this.picked >= 10) {
                    this.fast = !this.fast;
                    this.picked = 0;
                }

                int delay = this.fast ? 80 : 100;
                if (this.timer.hasTimeElapsed((long)delay, true) && !this.mc.inGameHasFocus && this.mc.currentScreen instanceof GuiChest) {
                    if (!this.isEmpty(mc.thePlayer.openContainer)) {
                        int index = this.getNextSlot(mc.thePlayer.openContainer);
                        this.mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, index, 0, 1, mc.thePlayer);
                        ++this.picked;
                    } else {
                        mc.thePlayer.closeScreen();

                        this.picked = 0;
                        this.fast = true;
                    }

                    this.timer.reset();
                }

            }
        }
    }
    private int getNextSlot(Container container) {
        int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;

        for(int i = 0; i < slotAmount; ++i) {
            if (container.getInventory().get(i) != null) {
                return i;
            }
        }

        return -1;
    }

    public boolean isEmpty(Container container) {
        int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;

        for(int i = 0; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isShit(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        } else if (itemStack.getItem().getUnlocalizedName().contains("bow")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("arrow")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("stick")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("egg")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("stick")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("string")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("flint")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("compass")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("feather")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("bucket")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("snow")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("fish")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("enchant")) {
            return true;
        } else if (itemStack.getItem().getUnlocalizedName().contains("exp")) {
            return true;
        } else if (itemStack.getItem() instanceof ItemPickaxe) {
            return true;
        } else if (itemStack.getItem() instanceof ItemTool) {
            return true;
        } else {
            return itemStack.getItem().getUnlocalizedName().contains("potion");
        }
    }
}
