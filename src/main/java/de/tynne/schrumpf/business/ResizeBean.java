/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tynne.schrumpf.business;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The logic for scaling images.
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class ResizeBean {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ResizeBean.class);
    
    private boolean change;
    private boolean keepAspect;
    private Integer preferredWidth;
    private Integer preferredHeight;

    public void setChange(boolean selected) {
        this.change = selected;
    }

    public void setKeepAspect(boolean selected) {
        this.keepAspect = selected;
    }

    public void setPreferredWidth(Integer integer) {
        this.preferredWidth = integer;
    }

    public void setPreferredHeight(Integer integer) {
        this.preferredHeight = integer;
    }
    
    public BufferedImage scaleImage(BufferedImage in) {
        BufferedImage result = in;
        LOGGER.debug("scaling: {}", change);
        if (change) {
            double sx;
            double sy;
            LOGGER.debug("original size: {}x{}", in.getWidth(), in.getHeight());
            sx = (double)preferredWidth / (double)in.getWidth();
            sy = (double)preferredHeight / (double)in.getHeight();
            
            if (keepAspect) {
                double min = Math.min(sx, sy);
                sx = min;
                sy = min;
            }            
            
            LOGGER.debug("sx = {}, sy = {}", sx, sy);
            int w = (int) Math.round(in.getWidth() * sx);
            int h = (int) Math.round(in.getHeight() * sy);
            
            AffineTransform scaleTransform = AffineTransform.getScaleInstance(sx, sy);
            AffineTransformOp bilinearScaleOp = 
                    new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

            LOGGER.debug("scaling to {}x{}", w, h);
            result = bilinearScaleOp.filter(
                    in,
                    new BufferedImage(w, h, in.getType()));
            LOGGER.debug("scaled");            
        }
        return result;
    }
}
