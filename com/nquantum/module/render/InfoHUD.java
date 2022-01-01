package com.nquantum.module.render;

import cf.nquan.util.Colors;
import cf.nquan.util.Time;
import com.nquantum.Asyncware;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventRenderUI;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import nig.hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class InfoHUD extends Module {

    static Minecraft mc = Minecraft.getMinecraft();

    public InfoHUD(){
        super("InfoHUD", 0, Category.RENDER);
    }

    public void setup() {
         ArrayList<String> options = new ArrayList<>();
         options.add("Classic");
         options.add("CatSense");
         options.add("CatSense CSGO");
         options.add("Asyncware");

        Asyncware.instance.settingsManager.rSetting(new Setting("InfoHUD Mode", this, "Classic", options));

    }



    @EventTarget
    public void onRenderUI(EventRenderUI e){
        int y = 0;
        ScaledResolution sr = new ScaledResolution(mc);
        float bps = Math.round(mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.posY, mc.thePlayer.lastTickPosZ) * 200) / 10f;

        String mode = Asyncware.instance.settingsManager.getSettingByName("InfoHUD Mode").getValString();

        Gui yyy = new Gui();
        Asyncware.renderer.drawString("UID: ", sr.getScaledWidth()- 50, sr.getScaledHeight()- 13, -1, true);
        Asyncware.renderer.drawString("-0001 ", sr.getScaledWidth()- 31, sr.getScaledHeight()- 13, new Color(139, 139, 139, 255).getRGB(), true);

      //  GL11.glPushMatrix();
      //  GL11.glTranslatef(0.0f, 36.0f, 0);
      //  Gui.drawRect(100, 130, 1, 1, new Color(12, 12, 12, 255).getRGB());
      //  Gui.drawRect(100, 20, 1, 1, new Color(31, 31, 31, 255).getRGB());
      //  GuiUtil.drawScaledString("PlayerList", 25, 7, false, 1.0f);
//
      //  for(Object ee : mc.theWorld.loadedEntityList) {
      //      if(ee instanceof EntityPlayer) {
      //          EntityPlayer player = mc.thePlayer;
      //          if(!(ee == player) && !player.isInvisible()) {
//
      //              GuiUtil.drawScaledString(((EntityPlayer) ee).getName(), 5, 27 + y, false, 0.75f);
      //              GL11.glPushMatrix();
      //              GL11.glTranslatef(0, 35.0f + y, 0);
      //             // Gui.drawRect(100, 2, 1, 1, new Color(40, 40, 40, 255).getRGB());
      //              GL11.glPopMatrix();
//
      //              y += 8;
//
      //          }
      //      }
      //  }
      //  GL11.glPopMatrix();

        if(mode.equalsIgnoreCase("Classic")){

             Asyncware.rendererT.drawString("XYZ: " + Math.round(mc.thePlayer.posX) + ", " + Math.round(mc.thePlayer.posY) + ", " + Math.round(mc.thePlayer.posZ) + ", " , 2, sr.getScaledHeight() - 14 * 2, Colors.RGB(), true);


            Asyncware.renderer.drawString(Time.getTime(System.currentTimeMillis(), "HH:mm"), sr.getScaledHeight() - 13 * 2, 7 - 3, -1, true);
            Asyncware.renderer.drawString("BPS: " + bps, 46, sr.getScaledHeight() - 8 * 2, Colors.RGB(), true);
            Asyncware.renderer.drawString("FPS: " + mc.getDebugFps(), 2, sr.getScaledHeight() - 8 * 2,  Colors.RGB(), true);

        }
        if(mode.equalsIgnoreCase("CatSense")){
            Gui gui = new Gui();
            GL11.glPushMatrix();
            GL11.glTranslatef(5, 35, 0);


            Gui.drawRect(71, 61, 1, 1, Colors.RGB());
            //Gui.drawRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB());
            //RenderUtil.drawRoundedShadow(70, 60, 2, 2);
            gui.drawGradientRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB(), new Color(14, 14, 14, 255).getRGB());
            GL11.glTranslatef(0, -20, 0);
            Asyncware.renderer.drawString("Time: " + Time.getTime(System.currentTimeMillis(), "HH:mm"), 5, 25, -1, false);
            Asyncware.renderer.drawString("BPS: " + bps, 5, 35, -1, false);
            Asyncware.renderer.drawString("FPS: " + mc.getDebugFps(), 5, 45, -1, false);
            GL11.glPopMatrix();
        }

        if(mode.equalsIgnoreCase("CatSense CSGO")){
            Gui gui = new Gui();
            GL11.glPushMatrix();
            GL11.glTranslatef(5, 35, 0);


            Gui.drawRect(71, 61, 1, 1, Colors.RGB());
            //Gui.drawRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB());
            //RenderUtil.drawRoundedShadow(70, 60, 2, 2);
            gui.drawGradientRect(70, 60, 2, 2, new Color(24, 24, 24, 255).getRGB(), new Color(14, 14, 14, 255).getRGB());
            GL11.glTranslatef(0, -20, 0);
            Asyncware.verdana.drawString("Time: " + Time.getTime(System.currentTimeMillis(), "HH:mm"), 5, 25, -1, false);
            Asyncware.verdana.drawString("BPS: " + bps, 5, 35, -1, false);
            Asyncware.verdana.drawString("FPS: " + mc.getDebugFps(), 5, 45, -1, false);
            GL11.glPopMatrix();
        }



    }
}
