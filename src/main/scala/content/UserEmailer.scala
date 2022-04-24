package content

import content.ZLayersPlayground.User
import zio.{Has, Task, ZIO, ZLayer}

object UserEmailer {

  type UserEmailerEnv = Has[UserEmailer.Service]

  trait Service {
    def notify(user: User, message: String): Task[Unit]
    def somethingElse(user: User): Task[Unit]
  }

  val live: ZLayer[Any, Nothing, UserEmailerEnv] = ZLayer.succeed(new Service {
    override def notify(user: User, message: String): Task[Unit] = Task {
      println(s"[UserEmailer] Sending $message to ${user.email}")
    }
    override def somethingElse(user: User): Task[Unit] = Task {
      println(s"[UserEmailer] SomethingElse happend to user ${user.name}")
    }
  })

  def notify(user: User, message: String): ZIO[UserEmailerEnv, Throwable, Unit] =
    ZIO.accessM(_.get.notify(user, message))
  def somethingElse(user: User): ZIO[UserEmailerEnv, Throwable, Unit] =
    ZIO.accessM(_.get.somethingElse(user))
}