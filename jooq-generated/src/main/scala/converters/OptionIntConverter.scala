package converters

import org.jooq.Converter

class OptionIntConverter extends Converter[Integer, Option[Int]] {

  override def from(databaseObject: Integer): Option[Int] =
    Option(databaseObject)

  override def to(userObject: Option[Int]): Integer =
    userObject.map(i => i: Integer).getOrElse(null)

  override def fromType(): Class[Integer] =
    classOf[Integer]

  override def toType(): Class[Option[Int]] =
    classOf[Option[Int]]

}
