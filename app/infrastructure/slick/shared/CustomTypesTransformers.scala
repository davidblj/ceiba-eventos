package infrastructure.slick.shared

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object CustomTypesTransformers {

  def parse(insertionDate: DateTime): String = {
    val timeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss")
    timeFormat.print(insertionDate)
  }
}
