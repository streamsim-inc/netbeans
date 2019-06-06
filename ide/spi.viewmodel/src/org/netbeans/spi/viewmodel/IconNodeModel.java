package org.netbeans.spi.viewmodel;

import java.awt.Image;

public interface IconNodeModel extends Model {

    /**
     * Find an icon for this node. Uses an {@link #setIconBase icon set}.
     *
     * @param type constants from {@link java.beans.BeanInfo}
     *
     * @return icon to use to represent the bean
     */
    public Image getIcon(Object node, int type) throws UnknownTypeException;

    /**
     * Finds an icon for this node when opened. This icon should represent the
     * node only when it is opened (when it can have children).
     *
     * @param type as in {@link #getIcon}
     * @return icon to use to represent the bean when opened
     */
    public Image getOpenedIcon(Object node, int type) throws UnknownTypeException;
}
