/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.viewmodel.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.spi.viewmodel.Models;
import org.netbeans.swing.outline.Outline;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author Enrico Scantamburlo <scantamburlo at streamsim.com>
 */
public class Paste {

    public static final String LINE_BREAK = "\n";
    public static final String CELL_BREAK = "\t";
    public static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    public static void pasteFromClipboard(Outline table, Models.CompoundModel model, Node.Property[] columns) {
        Transferable content = CLIPBOARD.getContents(table);
        if (content != null) {

            List<String> selectedColumnIds = getSelectedColumnIds(table, columns);
            List<Object> selectedNodes = getSelectedNodes(table);

            PasteType[] pasteTypes = model.getPasteTypes(content, selectedNodes, selectedColumnIds);
            if (pasteTypes != null && pasteTypes.length > 0) {
                try {
                    pasteTypes[0].paste();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }
    }

    private static List<Object> getSelectedNodes(Outline view) {
        List<Object> selectedNodes = new ArrayList<Object>();
        int[] selectedRows = view.getSelectedRows();
        if (selectedRows != null) {
            for (int row : selectedRows) {
                int r = view.convertRowIndexToModel(row);
                int f = view.convertColumnIndexToView(0);
                selectedNodes.add(view.getValueAt(r, f));
            }
        }
        return selectedNodes;
    }

    private static List<String> getSelectedColumnIds(Outline outline, Node.Property<?>[] properties) {

        List<String> selectedColIds = new ArrayList<String>();
        //final Outline outline = view.getOutline();
        int[] selectedCols = outline.getSelectedColumns();
        if (selectedCols != null) {
            for (int col : selectedCols) {
                int c = outline.convertColumnIndexToModel(col);
                Node.Property<?> prop = properties[c];
                selectedColIds.add(prop.getName());
            }
        }
        return selectedColIds;
    }
}

