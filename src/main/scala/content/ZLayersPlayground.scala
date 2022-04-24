package content

import content.UserDb.UserDbEnv
import content.UserEmailer.UserEmailerEnv
import content.UserSubscription.UserSubscriptionEnv
import zio._

object ZLayersPlayground extends ZIOAppDefault {

  case class User(name: String, email: String)

  val user: User = User("Frantisek", "frantisek.glova@gmail.com")
  val message = "notify message"

  val userBackendLayer: ZLayer[Any, Nothing, UserDbEnv with UserEmailerEnv] =
    UserDb.live ++ UserEmailer.live

  val userSubscriptionLayer: ZLayer[Any, Nothing, UserSubscriptionEnv] =
    userBackendLayer >>> UserSubscription.live

  def process(): URIO[Any with Console, ExitCode] = {
    val process = for {
      _ <- UserEmailer.notify(user, message).provideLayer(userBackendLayer)
      _ <- UserEmailer.somethingElse(user).provideLayer(userBackendLayer)
    } yield ()
    process.exitCode
  }

  def subscribe(): URIO[zio.ZEnv, ExitCode] = {
    UserSubscription.subscribe(user).provideLayer(userSubscriptionLayer).exitCode
  }
}
