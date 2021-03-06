/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.connectors.jdbc.internal;

import java.sql.JDBCType;

class ColumnData {
  private final String columnName;
  private final Object value;
  private final int dataType;

  ColumnData(String columnName, Object value, int dataType) {
    this.columnName = columnName;
    this.value = value;
    this.dataType = dataType;
  }

  String getColumnName() {
    return columnName;
  }

  Object getValue() {
    return value;
  }

  int getDataType() {
    return dataType;
  }

  @Override
  public String toString() {
    return "ColumnData [columnName=" + columnName + ", value=" + value + ", dataType="
        + JDBCType.valueOf(dataType) + "]";
  }
}
