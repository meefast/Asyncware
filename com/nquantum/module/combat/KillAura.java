package com.nquantum.module.combat;

import cf.nquan.util.Colors;
import cf.nquan.util.MovementUtil;
import cf.nquan.util.PacketUtil;
import cf.nquan.util.Timer;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.*;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.module.render.TargetStrafe;
import nig.hero.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

public class KillAura extends Module {

    public EntityLivingBase target;
    public long current, last;
    public int delay = 4;
    TargetStrafe s;
    private int direction = -1;
    public float yaw, pitch;
    public boolean others;
    private double animHealth = 1;
    private double width;
    private int colorPrimary;
    private int colorSecondary;


    public Random random = new Random();

    public KillAura() {
            super("KillAura", Keyboard.KEY_R, Category.COMBAT);
        }

        @Override
        public void setup() {

            Asyncware.instance.settingsManager.rSetting(new Setting("Crack Size", this, 5, 0, 65, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("Range", this, 3.0D, 1.0D, 120.0D, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("ClicksPerSecond", this, 9, 1, 20, true));


            Asyncware.instance.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("TPAura", this, false));

            Asyncware.instance.settingsManager.rSetting(new Setting("AutoBlock", this, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));

            Asyncware.instance.settingsManager.rSetting(new Setting("Players", this, true));
            Asyncware.instance.settingsManager.rSetting(new Setting("Animals", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("Teams", this, false));
            Asyncware.instance.settingsManager.rSetting(new Setting("LockView", this, false));
        }




    @EventTarget
        public void onPre(EventPreMotionUpdate event) {
            target = getClosest(mc.playerController.getBlockReachDistance());
            if(target == null)
            return;
        updateTime();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;

        mc.thePlayer.rotationYaw = (getRotations((Entity)target)[0] + this.random.nextInt(20) - 10.0F);
        mc.thePlayer.rotationPitch = (getRotations((Entity)target)[1] + this.random.nextInt(20) - 10.0F);


        int APS = (int)Asyncware.instance.settingsManager.getSettingByName("ClicksPerSecond").getValDouble();
        int range = (int)Asyncware.instance.settingsManager.getSettingByName("Range").getValDouble();

        if(Asyncware.instance.settingsManager.getSettingByName("TPAura").getValBoolean() == true && !target.isDead) {
            mc.thePlayer.rotationYaw = (getRotations((Entity)target)[0] + this.random.nextInt(20) - 10.0F);
            mc.thePlayer.rotationPitch = (getRotations((Entity)target)[1] + this.random.nextInt(20) - 10.0F);

            // mc.thePlayer.setPosition(target.posX + random.nextFloat() * (2.5f - -1.5f), mc.thePlayer.posY, target.posZ + random.nextFloat() * (2.5f - -1.5f));
        }

        event.setYaw(40f);
        boolean block = target != null && Asyncware.instance.settingsManager.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;

        if(block && target.getDistanceToEntity(mc.thePlayer) < range)
            mc.playerController.sendFakeUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());

        boolean twojStaryNigger = Asyncware.instance.moduleManager.getModuleByName("TargetStrafe").isToggled();
        double radius = Asyncware.instance.settingsManager.getSettingByName("Radius").getValDouble();

        if (mc.thePlayer.isCollidedHorizontally) {
            this.switchDirection();
        }

        if (mc.gameSettings.keyBindLeft.isPressed()) {
            this.direction = 1;
        }

        if (mc.gameSettings.keyBindRight.isPressed()) {
            this.direction = -1;
        }


        if(twojStaryNigger){

            if ( (double)mc.thePlayer.getDistanceToEntity(target) <= radius) {
                MovementUtil.setSpeed(MovementUtil.getBaseMoveSpeed(), (getRotations((Entity)target)[0]), direction, 0.0D);
            } else {
                MovementUtil.setSpeed( MovementUtil.getBaseMoveSpeed(), (getRotations((Entity)target)[0]), direction, 1.0D);
            }

        }


        if((Asyncware.instance.settingsManager.getSettingByName("TPAura").getValBoolean())){
            if(block && target.getDistanceToEntity(mc.thePlayer) < (float) range)
                mc.thePlayer.isBlocking();
          //  mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), 20);

        } else{
            if(block && target.getDistanceToEntity(mc.thePlayer) < (float) 400)
                mc.thePlayer.isBlocking();
         //   mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), 20);
        }


        if(current - last > 1000 / APS) {
            mc.thePlayer.rotationYaw = (getRotations((Entity)target)[0] + this.random.nextInt(10) - 5.0F);
            mc.thePlayer.rotationPitch = (getRotations((Entity)target)[1] + this.random.nextInt(10) - 5.0F);


            attack(target);
            resetTime();
        }
    }

    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        if(target == null)
            return;



        final double oldX = mc.thePlayer.lastTickPosX;
        final double oldY = mc.thePlayer.lastTickPosY;
        final double oldZ = mc.thePlayer.lastTickPosZ;

        if(Asyncware.instance.settingsManager.getSettingByName("TPAura").getValBoolean() == true && !target.isDead) {

            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(target.posX + 5, target.posY,  target.posZ + 5, true));
            mc.playerController.sendFakeUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
            attack(target);
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(oldX, oldY, oldZ, true));



            //  mc.thePlayer.setPosition(oldX, oldY, oldZ);
        }
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;


      //  mc.thePlayer.rotationYaw = (getRotations((Entity)target)[0] + this.random.nextInt(20) - 10.0F);
      //  mc.thePlayer.rotationPitch = (getRotations((Entity)target)[1] + this.random.nextInt(20) - 10.0F);



    }


    private void attack(Entity entity) {
        for(int i = 0; i < Asyncware.instance.settingsManager.getSettingByName("Crack Size").getValDouble(); i++)
            mc.thePlayer.onCriticalHit(entity);

        if(entity.getName() == "King_of_Vodka"){
            return;
        }
        double oldX = mc.thePlayer.lastTickPosX;
        double oldY = mc.thePlayer.lastTickPosY;
        double oldZ = mc.thePlayer.lastTickPosZ;



        //PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(target.posX - 2.0f, target.posY, target.posZ - 2.0f, true));
     //  PacketUtil.sendPacketPlayer(new C03PacketPlayer.C04PacketPlayerPosition(oldX, oldY, oldZ, true));


           mc.thePlayer.swingItem();
           mc.playerController.attackEntity(mc.thePlayer, entity);


    }

    @EventTarget
    public void onMove(EventMove event){
        if(canAttack(target)) {

        }
    }

    public static double randomNumber(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }


    public float[] getRotations(double posX, double posY, double posZ) {
        final EntityPlayerSP player = mc.thePlayer;
        double x = posX - player.posX;
        double y = posY - (player.posY + player.getEyeHeight());
        double z = posZ - player.posZ;

        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }



    private void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    private void resetTime() {
        last = (System.nanoTime() / 1000000L);
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;
                if (canAttack(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }
                }
            }
        }
        return target;
    }

    @EventTarget
    public void onRender3D(Event3D e){

      //  Vec3 vec = new Vec3(target.posX, target.posY, target.posZ);
     // // double x = vec.xCoord - RenderManager.renderPosX; double y = vec.yCoord - RenderManager.renderPosY; double z = vec.zCoord - RenderManager.renderPosZ;
     //  double width = 0.3;
     //  double height = mc.thePlayer.getEyeHeight();
     //  RenderUtil.pre3D();
     //  GL11.glLoadIdentity();
     //  mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
     //  GL11.glColor4f(200, 20, 20, 255);
     //  GL11.glLineWidth(1.5f);
     //  GL11.glBegin(GL11.GL_LINE_STRIP);
     //  GL11.glVertex3d(x-width, y,z-width);
     //  GL11.glVertex3d(x-width, y,z-width);
     //  GL11.glVertex3d(x-width, y + height,z-width);
     //  GL11.glVertex3d(x+width, y + height,z-width);
     //  GL11.glVertex3d(x+width, y,z-width);
     //  GL11.glVertex3d(x-width, y,z-width);
     //  GL11.glVertex3d(x-width, y,z+width);
     //  GL11.glEnd();
     //  GL11.glBegin(GL11.GL_LINE_STRIP);
     //  GL11.glVertex3d(x+width, y,z+width);
     //  GL11.glVertex3d(x+width, y + height,z+width);
     //  GL11.glVertex3d(x-width, y + height,z+width);
     //  GL11.glVertex3d(x-width, y,z+width);
     //  GL11.glVertex3d(x+width, y,z+width);
     //  GL11.glVertex3d(x+width, y,z-width);
     //  GL11.glEnd();
     //  GL11.glBegin(GL11.GL_LINE_STRIP);
     //  GL11.glVertex3d(x+width, y + height,z+width);
     //  GL11.glVertex3d(x+width, y + height,z-width);
     //  GL11.glEnd();
     //  GL11.glBegin(GL11.GL_LINE_STRIP);
     //  GL11.glVertex3d(x-width, y + height,z+width);
     //  GL11.glVertex3d(x-width, y + height,z-width);
     //  GL11.glEnd();
     //  RenderUtil.post3D();
    }


    private void switchDirection() {
        if (this.direction == 1) {
            this.direction = -1;
        } else {
            this.direction = 1;
        }

    }

    @EventTarget
    public void onHeadRender(EventHeadRender e){
         e.headYaw = (getRotations((Entity)target)[0]);
         e.headPitch = (getRotations((Entity)target)[1]);
    }

    private boolean canAttack(EntityLivingBase player) {
      if(player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
          if (player instanceof EntityPlayer && !Asyncware.instance.settingsManager.getSettingByName("Players").getValBoolean())
              return false;
          if (player instanceof EntityAnimal && !Asyncware.instance.settingsManager.getSettingByName("Animals").getValBoolean())
              return false;
          if (player instanceof EntityMob && !Asyncware.instance.settingsManager.getSettingByName("Monsters").getValBoolean())
              return false;
          if (player instanceof EntityVillager && !Asyncware.instance.settingsManager.getSettingByName("Villagers").getValBoolean())
              return false;
      }
      if(player.isOnSameTeam(mc.thePlayer) && Asyncware.instance.settingsManager.getSettingByName("Teams").getValBoolean())
          return false;
      if(player.isInvisible() && !Asyncware.instance.settingsManager.getSettingByName("Invisibles").getValBoolean())
          return false;
      if(!isInFOV(player, Asyncware.instance.settingsManager.getSettingByName("FOV").getValDouble()))
          return false;
      return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance() && player.ticksExisted > Asyncware.instance.settingsManager.getSettingByName("Existed").getValDouble();
    //   return player != mc.thePlayer ;
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }



    public float[] getRotations(Entity e){
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if(deltaX < 0 && deltaZ < 0){
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }else if(deltaX > 0 && deltaZ < 0){
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[] { yaw, pitch };
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        s = new TargetStrafe();
        Timer t = new Timer();
        if(t.hasTimeElapsed(20L, true)){
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        }
        this.setDisplayName("Kill Aura \u00A77" + "Smart");
        if(Asyncware.instance.settingsManager.getSettingByName("TPAura").getValBoolean()){
            this.setDisplayName("Kill Aura \u00A77" + "TP");
        }
    }

    @EventTarget
    public void onRenderUI(EventRenderUI e) {


        if (!(target == null) && Asyncware.instance.moduleManager.getModuleByName("KillAura").isToggled() && Asyncware.instance.moduleManager.getModuleByName("TargetHUD").isToggled()) {
            colorPrimary = Colors.Astolfo(200, 0.7f, 0.5f);
            colorSecondary = Colors.Astolfo(200, 1.0f, 0.5f);

            GL11.glPushMatrix();
            width = 140 - 32.5;
            GL11.glTranslated(GuiScreen.width / 2 - -120, GuiScreen.height / 2 + 20, GuiScreen.width / 2);

            Gui.drawRect(-22.5f, 0, 128 - 3.5f, 50, new Color(24, 24, 24, 181).getRGB());

            GL11.glTranslatef(-22.0f, -2.2f, 0);

            mc.fontRendererObj.drawString(target.getName(), 30, 8, -1, true);

            // \u2764
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glTranslatef(-15, -15, 0);
            mc.fontRendererObj.drawString(Math.round(target.getHealth()) + "", 30, 25, Colors.Astolfo(200, 1.0f, 0.5f), true);
            mc.fontRendererObj.drawString("\u2764", mc.fontRendererObj.getStringWidth(Math.round(target.getHealth()) + "") + 32, 25, Colors.Astolfo(200, 1.0f, 0.5f), true);

            GL11.glTranslatef(15, 15, 0);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            mc.fontRendererObj.drawString("", 30, 25, -1, true);

            GuiInventory.drawEntityOnScreen(15, 47, 20, 2, 2, target);

            animHealth += ((target.getHealth() - animHealth) / 32) * 0.7;
            if (animHealth < 0 || animHealth > target.getMaxHealth()) {
                animHealth = target.getHealth();
            } else {
                GL11.glTranslatef(30, 0, 0);
                Gui.drawRect(0, 40.5f, (int) (width), 48.5f, colorPrimary);
                Gui.drawRect(0f, 40.5f, (int) ((animHealth / target.getMaxHealth()) * width), 48.5f, colorSecondary);
            }
            GL11.glScalef(2f, 2f, 2f);
            //Asyncware.renderer1.drawString(target.getHealth() + "\u2764", 2, 2,Colors.Astolfo(100, 1.0f, 0.5f), true);
            GL11.glPopMatrix();
            //  Gui.drawRect(350.0D, 10.0D, 120.0D, 170.0D, new Color(9, 19, 34, 167).getRGB());

        }


    }



    }

     /*
    private EntityLivingBase target;
    private long current, last;
    private int delay = 8;
    private float yaw, pitch;
    private boolean others;

    public Timer timer = new Timer();

    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT);
    }

    @Override
    public void setup() {

        Asyncware.instance.settingsManager.rSetting(new Setting("Crack Size", this, 5, 0, 15, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("APS", this, 12, 1, 20, true));

        Asyncware.instance.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("AutoBlock", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Players", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Animals", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Teams", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("LockView", this, false));
    }



    public boolean blocking = false;


    @EventTarget
    public void onUpdate(EventUpdate e) {

        double APS = Asyncware.instance.settingsManager.getSettingByName("APS").getValDouble();

        int aps = (int) APS;
        List<EntityLivingBase> targets = (List<EntityLivingBase>) mc.theWorld.entityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

        targets = targets.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 && entity != mc.thePlayer).collect(Collectors.toList());
        targets.sort(Comparator.comparingDouble(entity ->((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));

        if(!targets.isEmpty()) {
            EntityLivingBase target = targets.get(0);


            if(this.timer.hasTimeElapsed(1000 / aps, true)) {


               block();

                //mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("xd"));
                //mc.rightClick();
                mc.thePlayer.swingItem();
                mc.playerController.attackEntity(mc.thePlayer, target);
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

            }

        }


    }

    private void block() {
        if (!mc.gameSettings.keyBindUseItem.isPressed() && !mc.thePlayer.isBlocking()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getCurrentEquippedItem(), 0.0f, 0.0f, 0.0f));
           mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getCurrentEquippedItem(), 0.0f, 0.0f, 0.0f));
            this.blocking = true;
        }
    }

    public float[] getRots(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 7.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ= e.posX + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                dist = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / dist));

        if(deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(-Math.atan(deltaZ / deltaX)));
        } else if(deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(-Math.atan(deltaZ / deltaX)));
        }

        return new float[] {yaw, pitch};
    }


     */
