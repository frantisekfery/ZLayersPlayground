package content

import content.ZLayersPlayground.User
import zio.{Has, Task, ZIO, ZLayer}

object UserDb {

  type UserDbEnv = Has[UserDb.Service]

  trait Service {
    def create(user: User): Task[Unit]
    def update(userEmail: String, user: User): Task[Unit]
    def get(userEmail: String): Task[Unit]
    def delete(userEmail: String): Task[Unit]
  }

  val live: ZLayer[Any, Nothing, UserDbEnv] = ZLayer.succeed(new Service {
    override def create(user: User): Task[Unit] = Task {
      println(s"[UserDb] create user with name: ${user.name} and email: ${user.email}")
    }
    override def update(userEmail: String, user: User): Task[Unit] = Task {
      println(s"[UserDb] update user with email: ${userEmail}")
    }
    override def get(userEmail: String): Task[Unit] = Task {
      println(s"[UserDb] get user with email: ${userEmail}")
    }
    override def delete(userEmail: String): Task[Unit] = Task {
      println(s"[UserDb] delete user with email: ${userEmail}")
    }
  })

  def create(user: User): ZIO[UserDbEnv, Throwable, Unit] =
    ZIO.accessM(_.get.create(user))
  def update(userEmail: String, user: User): ZIO[UserDbEnv, Throwable, Unit] =
    ZIO.accessM(_.get.update(userEmail, user))
  def get(userEmail: String): ZIO[UserDbEnv, Throwable, Unit] =
    ZIO.accessM(_.get.get(userEmail))
  def delete(userEmail: String): ZIO[UserDbEnv, Throwable, Unit] =
    ZIO.accessM(_.get.delete(userEmail))
}