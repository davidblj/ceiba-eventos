package infrastructure.slick.shared

import java.sql.Timestamp

import org.joda.time.DateTime
import org.joda.time.DateTimeZone.UTC
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.MySQLProfile.api._


object CustomColumnTypes {

  implicit val jodaDateTimeType: JdbcType[DateTime] with BaseTypedType[DateTime]
    = MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime, UTC)
    )
}
