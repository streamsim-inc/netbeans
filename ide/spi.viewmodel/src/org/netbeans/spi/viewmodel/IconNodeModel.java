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

import java.awt.Image;

/**
 *
 * @author matteo
 */
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