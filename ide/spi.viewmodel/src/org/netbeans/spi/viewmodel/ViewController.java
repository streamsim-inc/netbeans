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

package org.netbeans.spi.viewmodel;

import org.netbeans.modules.viewmodel.OutlineTableAccessor;
import javax.swing.JComponent;
import javax.swing.table.TableColumn;
import org.netbeans.modules.viewmodel.OutlineTable;
import org.netbeans.swing.etable.ETableColumnModel;
import org.netbeans.swing.outline.Outline;

public final class ViewController {

    private final OutlineTable outlineTable;

    public ViewController(JComponent c) {
        this.outlineTable = (OutlineTable) c;
    }

    /**
     * @param origIndex the original index in the column list
     * @param hidden
     */
    public void setColumnHidden(int origIndex, boolean hidden) {
        TableColumn[] columns = OutlineTableAccessor.getInstance().tableColumns(outlineTable);
        if (columns == null || columns.length < origIndex) {
            return;
        }

        Outline outline = OutlineTableAccessor.getInstance().treeTable(outlineTable).getOutline();
        ETableColumnModel columnModel = (ETableColumnModel) outline.getColumnModel();
        columnModel.setColumnHidden(columns[origIndex], hidden);
    }

    public void clearSortedColumns() {
        Outline outline = OutlineTableAccessor.getInstance().treeTable(outlineTable).getOutline();
        ETableColumnModel columnModel = (ETableColumnModel) outline.getColumnModel();
        columnModel.clearSortedColumns();
        //XXX: EMI: the below only forces a refresh of the table UI as no column has any sorting anymore
        setColumnSorted(0, true, 0);
    }

    /**
     * @param origIndex the original index in the column list
     * @param ascending
     * @param rank 0 - unsorted, 1 - top priority, 2 - next priority, etc.
     */
    public void setColumnSorted(int origIndex, boolean ascending, int rank) {
        TableColumn[] columns = OutlineTableAccessor.getInstance().tableColumns(outlineTable);
        if (columns == null || columns.length < origIndex) {
            return;
        }

        Outline outline = OutlineTableAccessor.getInstance().treeTable(outlineTable).getOutline();
        ETableColumnModel columnModel = (ETableColumnModel) outline.getColumnModel();
        TableColumn origColumn = columns[origIndex];
        if (columnModel.isColumnHidden(origColumn)) {
            //XXX: EMI: Cannot sort a hidden column
            return;
        }

        int viewIndex = columnModel.getColumnIndex(origColumn.getIdentifier());
        int modelIndex = outline.convertColumnIndexToModel(viewIndex);
        outline.setColumnSorted(modelIndex, ascending, rank);
    }
}