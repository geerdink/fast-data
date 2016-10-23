package lambda.util

import org.scalatest.FunSpec
import org.slf4j.{Logger, LoggerFactory}

class LogTest extends FunSpec {
  describe("The logger") {
    it("should log to the console and an output file") {
      val log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
      log.info("This is a test INFO log.")
      log.error("This is a test ERROR log.")
    }
  }
}
