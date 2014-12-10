/*
 * Copyright (C) 2014 Stephan Fuhrmann <stephan@tynne.de>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
