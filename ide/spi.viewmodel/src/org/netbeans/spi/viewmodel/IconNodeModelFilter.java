package org.netbeans.spi.viewmodel;

import java.awt.Image;

public interface IconNodeModelFilter extends NodeModelFilter {

    /**
     * @param type constants from {@link java.beans.BeanInfo}
     *
     * @return filtered icon to use to represent the bean
     */
    public Image getIcon(IconNodeModel original, Object node, int type) throws UnknownTypeException;

    /**
     * @param type as in {@link #getIcon}
     * @return filtered icon to use to represent the bean when opened
     */
    public Image getOpenedIcon(IconNodeModel original, Object node, int type) throws UnknownTypeException;
}
