package com.nquantum.ui.clickgui;

import cf.nquan.util.Colors;
import cf.nquan.util.RenderUtil;
import cf.nquan.util.Strings;
import com.nquantum.Asyncware;
import com.nquantum.module.Category;
import com.nquantum.module.Module;
import com.nquantum.ui.clickgui.elem.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;


public class Frame {

    int x;
    int y;
    int width;
    int height;
    public static int count;

    Category category;
    Minecraft mc = Minecraft.getMinecraft();
    ArrayList<Button> moduleButtons;

    public Frame(Category category, int x, int y){
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 300;
        this.category = category;

        moduleButtons = new ArrayList<>();
        int offsetY = 15;
        for(Module module : Asyncware.instance.moduleManager.getModules(category)){
            moduleButtons.add(
                    new Button(module, x + width / 2, y + 15 + offsetY, this)
            );
            offsetY += 14;
        }



    }

    public void render(int MouseX, int MouseY){

       count = 1;
       GL11.glPushMatrix();
        GL11.glTranslatef(150, 0, 0);

        RenderUtil.drawRoundedRectWithShadow(x,y + 20, x + width, y + height, new Color(26, 26, 26, 104).getRGB(), new Color(26, 26, 26, 255).getRGB());
       //  Gui.drawRect(Asyncware.dort.getStringWidth(Strings.capitalizeOnlyFirstLetter(category.toString())) + 58, 10, 10, 30, Colors.RGB());
       Gui.drawRect(x - 1, y - 1, x + width + 1, 30 + 1, Colors.Astolfo(count * 100, 1.0f, 0.6f));


        Asyncware.renderer.drawString(Strings.capitalizeOnlyFirstLetter(category.toString()), x + 2, y + 4, new Color(255, 255, 255).getRGB(), true);
      //
        for(Button button : moduleButtons){
             button.draw(MouseX, MouseY);
        }

        GL11.glPopMatrix();



    }

    public void onClick(int x, int y, int button){
        for(Button button1 : moduleButtons){
            button1.onClick(x, y, button);
        }
    }

}
