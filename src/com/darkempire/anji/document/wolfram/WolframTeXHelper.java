package com.darkempire.anji.document.wolfram;

import com.darkempire.anji.annotation.AnjiExperimental;
import com.darkempire.anji.document.tex.TeXProjectManager;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by siredvin on 08.10.14.
 */
@AnjiExperimental
public class WolframTeXHelper {
    private MathCanvas mathCanvas;
    private TeXProjectManager manager;
    private KernelLink ml;

    public WolframTeXHelper(TeXProjectManager manager, KernelLink ml) {
        this.manager = manager;
        this.ml = ml;
        mathCanvas = new MathCanvas(ml);
        mathCanvas.setBackground(Color.WHITE);
        mathCanvas.setImageType(MathCanvas.GRAPHICS);// определяем тип графики
        mathCanvas.setUsesFE(true);
    }

    public void getImage(String exec, String name) throws IOException {
        mathCanvas.setMathCommand(exec);// отправляем команду ядру
        Image tImage = mathCanvas.getImage();
        BufferedImage image = new BufferedImage(tImage.getWidth(mathCanvas), tImage.getHeight(mathCanvas), BufferedImage.TYPE_INT_RGB);
        image.createGraphics().drawImage(tImage, 0, 0, mathCanvas);
        ImageIO.write(image, "png", manager.createResourceFile(name + ".png"));
    }
}
