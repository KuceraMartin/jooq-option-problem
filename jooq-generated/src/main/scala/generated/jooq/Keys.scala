/*
 * This file is generated by jOOQ.
 */
package generated.jooq


import generated.jooq.tables.Employee
import generated.jooq.tables.records.EmployeeRecord

import org.jooq.TableField
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal


/**
 * A class modelling foreign key relationships and constraints of tables in
 * test.
 */
object Keys {

  // -------------------------------------------------------------------------
  // UNIQUE and PRIMARY KEY definitions
  // -------------------------------------------------------------------------

  val EMPLOYEE_PKEY: UniqueKey[EmployeeRecord] = Internal.createUniqueKey(Employee.EMPLOYEE, DSL.name("employee_pkey"), Array(Employee.EMPLOYEE.ID).asInstanceOf[Array[TableField[EmployeeRecord, _] ] ], true)
}
