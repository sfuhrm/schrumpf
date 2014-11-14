/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;

/**
 *
 * @author fury
 */
public class FileCallable implements Callable<Object> {
    private final File file;
    private final ResizeBean resizeBean;

    public FileCallable(File file, ResizeBean resizeBean) {
        this.file = Objects.requireNonNull(file);
        this.resizeBean = resizeBean;
    }

    @Override
    public Object call() throws Exception {
        BufferedImage image = ImageIO.read(file);
        BufferedImage scaled = resizeBean.scaleImage(image);
        return null;
    }
}
