/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
