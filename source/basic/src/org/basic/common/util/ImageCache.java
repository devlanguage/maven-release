package org.basic.common.util;

import static org.basic.common.bean.CommonConstants.ICON_FILE_PATH;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Builds a default UGLY icon to be used when an icon cannot be loaded for some reason.
 * 
 * @author cfagan
 * 
 */
class DefaultMissingIcon implements Icon {

    private int iSize = 22;

    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, iSize, iSize);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, iSize - 1, iSize - 1);
        g2d.setColor(Color.RED);
        g2d.drawString("x", iSize / 2, iSize / 2);
    }

    public int getIconWidth() {

        return iSize;
    }

    public int getIconHeight() {

        return iSize;
    }
}

public class ImageCache {

    private final static ImageCache instance = new ImageCache();
    private static String DEFAULT_IMAGE_PATH;

    private ImageCache() {

        URL url = ImageCache.class.getResource(ICON_FILE_PATH);
        DEFAULT_IMAGE_PATH = url.getFile();

    }

    public final static ImageCache getInstance() {

        return instance;
    }

    private static HashMap<String, SoftReference<ImageIcon>> IMAGE_CACHE = new HashMap<String, SoftReference<ImageIcon>>();
    private static final DefaultMissingIcon UGLY_DEFAULT_ICON = new DefaultMissingIcon();

    /**
     * Scales the icon to the specified size
     * 
     * @param strPath
     *            String - path to original icon
     * @param newHeight
     *            int - new height for the icon
     * @param newWidth
     *            int - new width for the icon
     * @return Icon
     */
    public Icon getIcon(String iconFileName, int width, int height) {

        Icon icnResult = getIcon(iconFileName);
        if (icnResult instanceof ImageIcon) {
            BufferedImage biScaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = biScaledImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(((ImageIcon) icnResult).getImage(), 0, 0, width, height, null);
            g2.dispose();

            icnResult = new ImageIcon(biScaledImage);
        }
        return icnResult;
    }

    public Icon getIcon(String iconFileName) {

        iconFileName = DEFAULT_IMAGE_PATH + iconFileName;
        SoftReference<ImageIcon> srResult = IMAGE_CACHE.get(iconFileName);
        ImageIcon icnResult = null;
        try {
            if (srResult == null) {
                icnResult = loadIntoCache(iconFileName);
            } else {
                icnResult = srResult.get();
                if (icnResult == null) {
                    icnResult = loadIntoCache(iconFileName);
                }
            }
            return icnResult;
        } catch (Exception e) {
            e.printStackTrace();
            return UGLY_DEFAULT_ICON;
        }
    }

    /**
     * loads the specified ion into the cache
     * 
     * @param iconPath
     *            String
     * @return ImageIcon a string refrence to the added icon
     */
    private ImageIcon loadIntoCache(String iconPath) {

        ImageIcon icnResult = new ImageIcon(iconPath);
        IMAGE_CACHE.put(iconPath, new SoftReference<ImageIcon>(icnResult));
        return icnResult;
    }
}
