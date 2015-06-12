import com.twitter.finagle.Redis
import com.twitter.finagle.redis.util._
import com.twitter.finagle.redis.util._
import com.twitter.util._

object FinagleMasterSlaveSampleClient {

  val redisHosts = List("localhost:6379", "localhost:6380")

  val redisClients = redisHosts map (Redis.newRichClient(_))

  def read(key: String) = {
    val k = StringToChannelBuffer(key)
    val fannedOutGetFutures = redisClients map (_.get(k))
    firstSuccessOf(fannedOutGetFutures) map { 
      case Some(v) => Some(CBToString(v))
      case _ => None
    }
  }

  def write(key: String, value: String) = {
    val k = StringToChannelBuffer(key)
    val v = StringToChannelBuffer(value)
    val fannedOutSetFutures = redisClients map (_.set(k,v))
    firstSuccessOf(fannedOutSetFutures)
  }

  def firstSuccessOf[A](fs: List[Future[A]]): Future[A] = {
    def loop(selection: Future[(Try[A], Seq[Future[A]])]): Future[A] = selection flatMap {
      case (Return(result), _) => Future.value(result)
      case (Throw(error), Nil) => Future.exception(error)
      case (_, fs) => loop(Future.select(fs))
    }
    loop(Future.select(fs))
  }

}
