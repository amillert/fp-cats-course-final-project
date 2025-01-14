package fpfinal.fakes

import fpfinal.app.Console
import fpfinal.common.IO

trait FakeConsole extends Console {
  var linesToRead: List[String]
  var linesWritten: Vector[String] = Vector.empty

  override val console: Service = new Service {
    override def readLine(msg: String): IO[String] =
      IO {
        val hd = linesToRead.head
        linesToRead = linesToRead.tail
        hd
      }

    override def printLine(line: String): IO[Unit] =
      IO {
        linesWritten = linesWritten :+ line
      }
  }

}
