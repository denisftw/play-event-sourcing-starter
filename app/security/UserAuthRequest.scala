package security

import model.User
import play.api.Logger
import play.api.mvc._
import services.AuthService

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by denis on 10/16/16.
  */
case class UserAwareRequest[A](user: Option[User], request: Request[A]) extends WrappedRequest[A](request)

class UserAwareAction(userAuthService: AuthService) extends ActionBuilder[UserAwareRequest] {

  def invokeBlock[A](request: Request[A], block: (UserAwareRequest[A]) => Future[Result]): Future[Result] = {
    val maybeUserD = userAuthService.checkCookie(request)
    maybeUserD match {
      case Success(maybeUser) => block(UserAwareRequest(maybeUser, request))
      case Failure(exc) =>
        Logger.error("Exception occurred while invoking user-aware action", exc)
        Future.successful(Results.Redirect("/500"))
    }
  }
}
