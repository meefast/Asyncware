package com.nquantum.module.render;

import cf.nquan.util.Timer;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.Event3D;
import com.nquantum.event.impl.EventUpdate;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class BreadCrumbs extends Module {

    Timer timer = new Timer();
    ArrayList<Vec3> positions = new ArrayList<Vec3>();


    public BreadCrumbs(){
        super("Bread Crumbs", 0, Category.RENDER);
    }


    @EventTarget
    public void onUpdate(EventUpdate nigga){
        if (!timer.hasTimeElapsed(100, true)) {
            return;
        }
        if (mc.thePlayer.moveForward == 0 && mc.thePlayer.moveStrafing == 0) {
            return;
        }
        positions.add(mc.thePlayer.getPositionVector());

    }
    @EventTarget
    public void onRender3D(Event3D niggers){
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2);
        GL11.glColor4f(0.3f, 1f, 1f, 0.7f);
        GL11.glBegin(3);
        for (Vec3 vec : positions) {
            GL11.glVertex3d(vec.xCoord, vec.yCoord + 0.3, vec.zCoord);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

    }

    public Vec3 getRenderPos(double x, double y, double z) {

        x = x - RenderManager.renderPosX;
        y = y - RenderManager.renderPosY;
        z = z - RenderManager.renderPosZ;

        return new Vec3(x, y, z);
    }

}

