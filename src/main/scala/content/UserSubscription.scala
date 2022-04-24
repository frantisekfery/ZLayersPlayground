package content

import content.UserDb.UserDbEnv
import content.UserEmailer.UserEmailerEnv
import content.ZLayersPlayground.User
import zio.{Has, Task, ZIO, ZLayer}

object UserSubscription {

  type UserSubscriptionEnv = Has[UserSubscription.Service]

  class Service(userDb: UserDb.Service, userEmailer: UserEmailer.Service) {
    def subscribe(user: User): Task[User] =
      for {
        _ <- userDb.create(user)
        _ <- userEmailer.notify(user, "Notify Message")
      } yield user
  }

  val live: ZLayer[UserDbEnv with UserEmailerEnv, Nothing, UserSubscriptionEnv] =
    ZLayer.fromServices[UserDb.Service, UserEmailer.Service, UserSubscription.Service] {
      (userDb, userEmailer) => new Service(userDb, userEmailer)
  }

  def subscribe(user: User): ZIO[UserSubscriptionEnv, Throwable, User] =
    ZIO.accessM(_.get.subscribe(user))
}
