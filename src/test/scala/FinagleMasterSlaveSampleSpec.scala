import org.specs2.mutable.Specification

import com.twitter.util._

object FinagleMasterSlaveSampleSpec extends Specification {

  "FinagleMasterSlaveSample" should {
  	"write and read data" in {
  		val key = "test"
  		val value = "sample"
  		val test = for {
  			_ <- FinagleMasterSlaveSampleClient.write(key, value)
  			valueRead <- FinagleMasterSlaveSampleClient.read(key)
  		} yield (valueRead === Some(value))
  		Await.result(test)
    }
  }

}
