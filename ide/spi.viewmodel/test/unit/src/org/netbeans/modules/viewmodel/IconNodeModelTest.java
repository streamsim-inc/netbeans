package org.netbeans.modules.viewmodel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.util.Arrays;
import java.util.List;
import org.netbeans.junit.NbTestCase;
import org.netbeans.spi.viewmodel.IconNodeModel;
import org.netbeans.spi.viewmodel.ModelListener;
import org.netbeans.spi.viewmodel.Models;
import org.netbeans.spi.viewmodel.TreeModel;
import org.netbeans.spi.viewmodel.UnknownTypeException;

public class IconNodeModelTest extends NbTestCase {

    public IconNodeModelTest(String s) {
        super(s);
    }

    public void testIcon() throws UnknownTypeException {
        TreeModel tree = new TreeModelImpl();
        IconNodeModel icons = new IconNodeModelImpl(Arrays.asList("b", "c"));
        Models.CompoundModel compoundModel = Models.createCompoundModel(Arrays.asList(tree, icons));

        assertNotNull(compoundModel.getIcon("a", BeanInfo.ICON_COLOR_16x16));
        try {
            compoundModel.getIcon("z", BeanInfo.ICON_COLOR_16x16);
            fail("Expected UnknownTypeException for z object");
        } catch (UnknownTypeException ute) {
            //ok, expected
        }
    }

    public void testMultipleIconNodeModels() throws UnknownTypeException {
        TreeModel tree = new TreeModelImpl();
        IconNodeModel icons = new IconNodeModelImpl(Arrays.asList("b", "c"));
        IconNodeModel icons2 = new IconNodeModelImpl(Arrays.asList("a", "d"));
        Models.CompoundModel compoundModel = Models.createCompoundModel(Arrays.asList(tree, icons, icons2));

        assertNotNull(compoundModel.getIcon("b", BeanInfo.ICON_COLOR_16x16));
    }

    //a simple tree with 4 children a, b, c, d.
    private static class TreeModelImpl implements TreeModel {

        final Object[] children;

        public TreeModelImpl() {
            children = new String[]{"a", "b", "c", "d"};
        }

        @Override
        public Object getRoot() {
            return ROOT;
        }

        @Override
        public Object[] getChildren(Object parent, int from, int to) throws UnknownTypeException {
            return Arrays.copyOfRange(children, from, to);
        }

        @Override
        public boolean isLeaf(Object node) throws UnknownTypeException {
            return node != ROOT;
        }

        @Override
        public int getChildrenCount(Object node) throws UnknownTypeException {
            if (node == ROOT) {
                return children.length;
            } else {
                return 0;
            }
        }

        @Override
        public void addModelListener(ModelListener l) {
            //ignore
        }

        @Override
        public void removeModelListener(ModelListener l) {
            //ignore
        }
    }

    //'a' and 'd' get an icon
    private class IconNodeModelImpl implements IconNodeModel {

        private final List<String> noIconList;

        public IconNodeModelImpl(List<String> noIconList) {
            this.noIconList = noIconList;
        }

        @Override
        public Image getIcon(Object node, int type) throws UnknownTypeException {
            if (node == TreeModel.ROOT) {
                return null;
            } else {
                if (!(node instanceof String)) {
                    throw new UnknownTypeException(node);
                }
                String s = (String) node;
                if (s.length() != 1 || !('a' <= s.charAt(0) && s.charAt(0) <= 'd')) {
                    throw new UnknownTypeException(node);
                }
                if (noIconList.contains(s)) {
                    throw new UnknownTypeException(node);
                }

                BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                Graphics graphics = img.getGraphics();
                graphics.setColor(Color.yellow);
                graphics.fillRect(0, 0, 16, 16);
                graphics.dispose();
                return img;
            }
        }

        @Override
        public Image getOpenedIcon(Object node, int type) throws UnknownTypeException {
            return getIcon(node, type);
        }
    }

}
