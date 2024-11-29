import org.jooq.impl.DSL
import java.sql.DriverManager
import org.jooq.SQLDialect
import scala.jdk.CollectionConverters.IterableHasAsScala
import generated.jooq.tables.Employee.EMPLOYEE

object Main extends App {

  val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/localdb?currentSchema=test", "localuser", "localpass")
  val db = DSL.using(connection, SQLDialect.POSTGRES)

  val res = db.select(EMPLOYEE).from(EMPLOYEE).fetch().map(_.component1).asScala
  for (row <- res) {
    println(s"name: ${row.getName}\tage: ${row.getAge}")
  }

}
