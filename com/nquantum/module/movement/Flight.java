package com.nquantum.module.movement;

import cf.nquan.util.MovementUtil;
import cf.nquan.util.PacketUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.LinkedList;

public class Flight extends Module {

    public cf.nquan.util.Timer timer = new cf.nquan.util.Timer();

    public Flight() {
        super("Flight", Keyboard.KEY_F, Category.MOVEMENT);

    }



    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Vanilla");
        options.add("Verus-Packet");
        options.add("Verus");
        options.add("Verus-Nigger");
        options.add("Float");
        options.add("VictoryCraft");
        options.add("Kokscraft-Old");
        options.add("Kokscraft");
        options.add("Hypixel");
        options.add("Motion");
        options.add("Dev");

        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "Vanilla", options));
        Asyncware.instance.settingsManager.rSetting(new Setting("Fly Speed", this, 3, 0, 8, true));
    }

    @EventTarget
    public void onUpdate(EventUpdate nigger) {
        String mode = Asyncware.instance.settingsManager.getSettingByName("Fly Mode").getValString();
        boolean move = MovementUtil.isMoving();
        this.setDisplayName("Flight " + ChatFormatting.GRAY + mode);
        // MovementUtil.damagePlayer();
        //MovementUtil.damagePlayer();




            double speed = Asyncware.instance.settingsManager.getSettingByName("Fly Speed").getValDouble();

            if (mode.equalsIgnoreCase("Vanilla")) {

            }

            if (mode.equalsIgnoreCase("Float")) {
                MovementUtil.setSpeed(0.5f);
                mc.thePlayer.motionY = 0;
                mc.thePlayer.onGround = true;
                if (this.timer.hasTimeElapsed(622L, true)) {
                    mc.thePlayer.onGround = false;
                    MovementUtil.setSpeed(5f);
                    mc.thePlayer.motionY = 0.10;
                }
            }

            if(mode.equalsIgnoreCase("VictoryCraft")){
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.03f, mc.thePlayer.posZ, false));
                 // PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX - 0.6f, mc.thePlayer.posY + 0.03f, mc.thePlayer.posZ + 0.6f, false));

            }

            if (mode.equalsIgnoreCase("Verus-Packet")) {


                if (mc.thePlayer.ticksExisted == 3) {
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + .3, mc.thePlayer.posY - .4, mc.thePlayer.posZ + .3, false));
                }

                double y;
                mc.timer.timerSpeed = .7f;
                mc.thePlayer.setSpeed(2F);
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.2000, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.9000, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0020, mc.thePlayer.posZ, true));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));

                y = mc.thePlayer.posY - 1.0E-30D;
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true));


                mc.thePlayer.motionY = 0;
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + .03f, mc.thePlayer.posZ);
                for (int i = 0; i < 5; i++) {

                    mc.getNetHandler().addToSendQueue(new C00PacketKeepAlive());
                }


            }

            if (mode.equalsIgnoreCase("Verus")) {
                // this fly is coutrtesy of OlekAleksander, OlekAleksander#5599 on Discord (725619653749243906)
                mc.thePlayer.onGround = true;
                mc.thePlayer.motionY = 0;
                mc.thePlayer.setSpeed((float) 4D);
                if (this.timer.hasTimeElapsed(1585L, true)) {
                    mc.thePlayer.setSpeed((float) speed);
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0001, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                    mc.thePlayer.motionY = 0.25;

                }

            }

            if (mode.equalsIgnoreCase("Verus-Nigger")) {
                if (mc.thePlayer.isCollidedVertically) {
                    mc.thePlayer.onGround = true;
                    mc.timer.timerSpeed = 0.941f;
                    if (mc.thePlayer.ticksExisted % 6 == 0 && mc.gameSettings.keyBindJump.isKeyDown() && MovementUtil.isMoving()) {
                        if (move) {
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.11941, mc.thePlayer.posZ);
                        } else {
                            mc.thePlayer.motionX = 0;
                            mc.thePlayer.motionZ = 0;
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition());
                            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - (0.0789032489290815 * 2), mc.thePlayer.posZ);
                        }

                        mc.thePlayer.onGround = false;

                    } else mc.thePlayer.motionY = 0;

                    if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.thePlayer.isCollidedVertically = false;
                    }

                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.thePlayer.motionY = 0.5f;
                    }


                    mc.thePlayer.onGround = true;
                    if (mc.thePlayer.posY != Math.round(mc.thePlayer.posY) && mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, Math.round(mc.thePlayer.posY), mc.thePlayer.posZ);
                    }

                }
            }


            if (mode.equalsIgnoreCase("Kokscraft")) {
                // this fly is coutrtesy of OlekAleksander, OlekAleksander#5599 on Discord (725619653749243906)

                LinkedList queue = new LinkedList();

                mc.thePlayer.onGround = true;
                mc.thePlayer.motionY = 0;
                queue.clear();

                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
            }
            mc.thePlayer.setSpeed((float) 4D);
            if (mc.thePlayer.ticksExisted % 45 == 0) {
                // Clip into ground and silently accept the teleport from the server. (This fucks with teleport compensation LOL)
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 11.725, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
            }
            if (this.timer.hasTimeElapsed(1585L, true)) {
                mc.thePlayer.setSpeed((float) speed);
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.003198642135, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                mc.thePlayer.motionY = 0.005268723;

                for (int i = 0; i < 6; i++) {
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C00PacketKeepAlive());
                }

            }


            if (mode.equalsIgnoreCase("Motion")) {

            }

            if (mode.equalsIgnoreCase("Kokscraft-Old")) {


                mc.thePlayer.motionY = 0;


                double speedy = Asyncware.instance.settingsManager.getSettingByName("Speed").getValDouble();
                if (move == true) {
                    MovementUtil.setSpeed((int) speedy);
                }
                if (move == false) {
                    mc.thePlayer.onGround = true;
                    MovementUtil.setSpeed(2);
                }
                mc.timer.timerSpeed = 0.9f;
            }

            if (mode.equalsIgnoreCase("Hypixel")) {
                double y;
                double y1;
                mc.thePlayer.motionY = 0;
                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    y = mc.thePlayer.posY - 1.0E-10D;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true));
                }
                y1 = mc.thePlayer.posY + 1.0E-10D;
                mc.thePlayer.setPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ);
            }


    }




}
