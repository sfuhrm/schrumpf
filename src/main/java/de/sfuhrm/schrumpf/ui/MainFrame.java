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
package de.sfuhrm.schrumpf.ui;

import de.sfuhrm.schrumpf.business.FileCallable;
import de.sfuhrm.schrumpf.business.FormatBean;
import de.sfuhrm.schrumpf.business.NamingBean;
import de.sfuhrm.schrumpf.business.ResizeBean;
import de.sfuhrm.schrumpf.business.SkippedException;
import de.sfuhrm.schrumpf.prefs.BeanPrefsMapper;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stephan Fuhrmann <stephan@tynne.de>
 */
public class MainFrame extends javax.swing.JFrame {

    private final static Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        initFromPrefs();
        initDragAndDrop();
        initInternal();
        pack();
    }

    private Properties readPrefsMapping() {
        LOGGER.debug("reading properties");

        InputStream is = getClass().getResourceAsStream("MainFrame_prefs.properties");
        LOGGER.debug("input stream = {}", is);

        final Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException ex) {
            throw new Error(ex);
        }
        return props;
    }

    private void initFromPrefs() {
        try {
            LOGGER.debug("init from prefs");

            final Properties props = readPrefsMapping();
            BeanPrefsMapper.mapPrefsToBean(this, props);
        } catch (Exception ex) {
            LOGGER.error("Error while loading prefs map", ex);
            throw new Error(ex);
        }
    }

    public FormatPanel getFormatPanel1() {
        return formatPanel1;
    }

    public NamingPanel getNamingPanel1() {
        return namingPanel1;
    }

    public ResizePanel getResizePanel1() {
        return resizePanel1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        resizePanel1 = new de.sfuhrm.schrumpf.ui.ResizePanel();
        formatPanel1 = new de.sfuhrm.schrumpf.ui.FormatPanel();
        namingPanel1 = new de.sfuhrm.schrumpf.ui.NamingPanel();
        jTextFieldInfo = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItemResize = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemResetSettings = new javax.swing.JMenuItem();
        jMenuItemReloadSettings = new javax.swing.JMenuItem();
        jMenuItemSaveSettings = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemQuit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/sfuhrm/schrumpf/ui/MainFrame"); // NOI18N
        setTitle(bundle.getString("MainFrame.title")); // NOI18N
        setIconImage(getMyIconImage());
        getContentPane().setLayout(new java.awt.GridBagLayout());

        resizePanel1.setName("Resize"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(resizePanel1, gridBagConstraints);

        formatPanel1.setName("Format"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(formatPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(namingPanel1, gridBagConstraints);

        jTextFieldInfo.setColumns(60);
        jTextFieldInfo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTextFieldInfo, gridBagConstraints);

        jMenuFile.setText(bundle.getString("MainFrame.jMenuFile.text")); // NOI18N

        jMenuItemAbout.setMnemonic('A');
        jMenuItemAbout.setText(bundle.getString("MainFrame.jMenuItemAbout.text")); // NOI18N
        jMenuItemAbout.setToolTipText(bundle.getString("MainFrame.jMenuItemAbout.toolTipText")); // NOI18N
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemAbout);
        jMenuFile.add(jSeparator3);

        jMenuItemResize.setText(bundle.getString("MainFrame.jMenuItemResize.text")); // NOI18N
        jMenuItemResize.setToolTipText(bundle.getString("MainFrame.jMenuItemResize.toolTipText")); // NOI18N
        jMenuItemResize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemResizeActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemResize);
        jMenuFile.add(jSeparator1);

        jMenuItemResetSettings.setText(bundle.getString("MainFrame.jMenuItemResetSettings.text")); // NOI18N
        jMenuItemResetSettings.setToolTipText(bundle.getString("MainFrame.jMenuItemResetSettings.toolTipText")); // NOI18N
        jMenuItemResetSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemResetSettingsActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemResetSettings);

        jMenuItemReloadSettings.setText(bundle.getString("MainFrame.jMenuItemReloadSettings.text")); // NOI18N
        jMenuItemReloadSettings.setToolTipText(bundle.getString("MainFrame.jMenuItemReloadSettings.toolTipText")); // NOI18N
        jMenuItemReloadSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemReloadSettingsActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemReloadSettings);

        jMenuItemSaveSettings.setText(bundle.getString("MainFrame.jMenuItemSaveSettings.text")); // NOI18N
        jMenuItemSaveSettings.setToolTipText(bundle.getString("MainFrame.jMenuItemSaveSettings.toolTipText")); // NOI18N
        jMenuItemSaveSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveSettingsActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSaveSettings);
        jMenuFile.add(jSeparator2);

        jMenuItemQuit.setMnemonic('x');
        jMenuItemQuit.setText(bundle.getString("MainFrame.jMenuItemQuit.text")); // NOI18N
        jMenuItemQuit.setToolTipText(bundle.getString("MainFrame.jMenuItemQuit.toolTipText")); // NOI18N
        jMenuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemQuitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemQuit);

        jMenuBar1.add(jMenuFile);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemQuitActionPerformed

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        AboutDialog dialog = new AboutDialog(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

    private void jMenuItemSaveSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveSettingsActionPerformed
        BeanPrefsMapper.mapBeanToPrefs(this, readPrefsMapping());
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/sfuhrm/schrumpf/ui/MainFrame"); // NOI18N
        jTextFieldInfo.setText(bundle.getString("MainFrame.txt.prefs.saved")); // NOI18N
    }//GEN-LAST:event_jMenuItemSaveSettingsActionPerformed

    private void jMenuItemReloadSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemReloadSettingsActionPerformed
        BeanPrefsMapper.mapPrefsToBean(this, readPrefsMapping());
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/sfuhrm/schrumpf/ui/MainFrame"); // NOI18N
        jTextFieldInfo.setText(bundle.getString("MainFrame.txt.prefs.reloaded")); // NOI18N
    }//GEN-LAST:event_jMenuItemReloadSettingsActionPerformed

    private void jMenuItemResetSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResetSettingsActionPerformed
        BeanPrefsMapper.mapDefaultsToBean(this, readPrefsMapping());
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/sfuhrm/schrumpf/ui/MainFrame"); // NOI18N
        jTextFieldInfo.setText(bundle.getString("MainFrame.txt.prefs.reset")); // NOI18N
    }//GEN-LAST:event_jMenuItemResetSettingsActionPerformed

    
    private ImageFileFilter imageFileFilter = new ImageFileFilter();
    
    private void jMenuItemResizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResizeActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDragEnabled(true);
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(imageFileFilter);
        fileChooser.setFileFilter(imageFileFilter);
        
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            resizeFiles(Arrays.asList(fileChooser.getSelectedFiles()));
        }    }//GEN-LAST:event_jMenuItemResizeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.sfuhrm.schrumpf.ui.FormatPanel formatPanel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemQuit;
    private javax.swing.JMenuItem jMenuItemReloadSettings;
    private javax.swing.JMenuItem jMenuItemResetSettings;
    private javax.swing.JMenuItem jMenuItemResize;
    private javax.swing.JMenuItem jMenuItemSaveSettings;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextField jTextFieldInfo;
    private de.sfuhrm.schrumpf.ui.NamingPanel namingPanel1;
    private de.sfuhrm.schrumpf.ui.ResizePanel resizePanel1;
    // End of variables declaration//GEN-END:variables

    private DropTarget dropTarget;

    private DropTargetListener dropTargetListener = new DropTargetListener() {

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            LOGGER.info("Got drop event");
            setCursor(Cursor.getDefaultCursor());
            Transferable transferable = dtde.getTransferable();
            try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                final List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                LOGGER.debug("Dropped {} files: {}", files.size(), files);

                resizeFiles(files);

            } catch (UnsupportedFlavorException ex) {
                LOGGER.warn("No file flavor", ex);
            } catch (IOException ex) {
                LOGGER.warn("IOE", ex);
            }
        }
    };

    private void initDragAndDrop() {
        dropTarget = new DropTarget(this, dropTargetListener);
        setDropTarget(dropTarget);
    }

    private ExecutorService newExecutorService() {
        int procs = Runtime.getRuntime().availableProcessors();
        final ExecutorService executorService = Executors.newFixedThreadPool(procs);

        LOGGER.info("Created executor service {} with {} threads", executorService.getClass().getName(), procs);

        return executorService;
    }

    private void resizeFiles(final List<File> files) {
        final ResizeBean resizeBean = resizePanel1.toBean();
        final FormatBean formatBean = formatPanel1.toBean();
        final NamingBean namingBean = namingPanel1.toBean();

        Runnable r = new Runnable() {

            @Override
            public void run() {
                resizeFiles(files, resizeBean, formatBean, namingBean);
            }
        };

        Thread t = new Thread(r, "Resizer Thread");
        t.start();
    }    

    private void resizeFiles(List<File> files, ResizeBean resizeBean, FormatBean formatBean, NamingBean namingBean) {

        final ExecutorService executorService = newExecutorService();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/sfuhrm/schrumpf/ui/ProgressMonitor"); // NOI18N
        String message = bundle.getString("ProgressMonitor.message"); // NOI18N
        String noteInitial = bundle.getString("ProgressMonitor.note.initializing"); // NOI18N
        String noteEnd = bundle.getString("ProgressMonitor.note.done"); // NOI18N
        String noteFormat = bundle.getString("ProgressMonitor.note.format.image"); // NOI18N

        ProgressMonitor progressMonitor = new ProgressMonitor(MainFrame.this, message, noteInitial, 0, files.size());
        progressMonitor.setMillisToPopup(0);
        progressMonitor.setMillisToDecideToPopup(0);

        showInfo("Info.result.busy", files.size());

        int i = 0;
        int resized = 0;
        int errors = 0;
        int skipped = 0;
        boolean aborted = false;
        Map<FileCallable, Future<FileCallable>> map = new HashMap<>();
        List<FileCallable> submitted = new ArrayList<>();
        for (File f : files) {
            FileCallable callable = new FileCallable(f, resizeBean, formatBean, namingBean);
            Future<FileCallable> future = executorService.submit(callable);
            map.put(callable, future);
            submitted.add(callable);
        }

        for (FileCallable callable : submitted) {
            try {
                progressMonitor.setProgress(i);
                progressMonitor.setNote(String.format(noteFormat, i * 100 / files.size(), callable.getFile().getName()));

                map.get(callable).get();

                if (progressMonitor.isCanceled()) {
                    aborted = true;
                    for (Future<?> f : map.values()) {
                        f.cancel(true);
                    }
                    break;
                }

                resized++;
            } catch (ExecutionException ex) {
                Throwable cause = ex.getCause();

                if (cause instanceof SkippedException) {
                    skipped++;
                } else {
                    LOGGER.error("Error in " + callable.getFile().getAbsolutePath(), cause);
                    cause.printStackTrace(); // TODO
                    errors++;
                }
            } catch (Exception ex) {
                LOGGER.error("Error in " + callable.getFile().getAbsolutePath(), ex);
                ex.printStackTrace(); // TODO
                errors++;
            }
            i++;
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
        }

        if (aborted) {
            showInfo("Info.result.aborted");
        } else {
            if (errors == 0 && skipped == 0) {
                showInfo("Info.result.format", resized);
            } else {
                showInfo("Info.result-errors.format", resized, errors, skipped);
            }
        }

        progressMonitor.setProgress(i);
        progressMonitor.setNote(noteEnd);
        progressMonitor.close();
    }

    private void showInfo(String infoKey, Object... args) {
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/sfuhrm/schrumpf/ui/Info"); // NOI18N
        String format = bundle.getString(infoKey);
        final String info = String.format(format, args);
        LOGGER.info(info);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jTextFieldInfo.setText(info);
            }
        });
    }

    private void initInternal() {
        showInfo("Info.ready");
    }

    static Image getMyIconImage() {
        ImageIcon imageIcon = getMyIcon();
        return imageIcon != null ? imageIcon.getImage() : null;
    }

    static ImageIcon getMyIcon() {
        try {
            InputStream is = MainFrame.class.getResourceAsStream("Logo-Entwurf-80x60.png");
            if (is == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte buff[] = new byte[1024];
            int len;
            while ((len = is.read(buff)) > 0) {
                baos.write(buff, 0, len);
            }

            ImageIcon icon = new ImageIcon(baos.toByteArray());
            return icon;
        } catch (IOException ex) {
            LOGGER.warn("No icon loadable", ex);
            return null;
        }
    }
}