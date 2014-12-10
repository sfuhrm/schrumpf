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
package de.tynne.schrumpf.ui;

import de.tynne.schrumpf.business.NamingBean;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

/**
 * File filter using ImageIO file suffixes.
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
class ImageFileFilter extends FileFilter {
    private final Set<String> readerFileSuffixes;

    ImageFileFilter() {
        readerFileSuffixes = new HashSet<>();
        String[] myReaderFileSuffixes = ImageIO.getReaderFileSuffixes();
        for (String myReaderFileSuffixe : myReaderFileSuffixes) {
            readerFileSuffixes.add(myReaderFileSuffixe.toLowerCase());
        }
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String name = f.getName();
        String fileSuffix = NamingBean.parseNameAndSuffix(name)[1];
        return readerFileSuffixes.contains(fileSuffix.toLowerCase());
    }

    @Override
    public String getDescription() {
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/tynne/schrumpf/ui/MainFrame"); // NOI18N
        return bundle.getString("MainFrame.fileFilter.images"); // NOI18N
    }
}
