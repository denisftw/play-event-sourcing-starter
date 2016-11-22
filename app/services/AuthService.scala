package services

import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.TimeUnit

import dao.{SessionDao, UserDao}
import model.{User, UserSession}
import org.apache.commons.codec.binary.Base64
import play.api.mvc.{Cookie, RequestHeader}

import scala.concurrent.duration.Duration
import scala.util.{Success, Try}

/**
  * Created by denis on 2/11/16.
  */
class AuthService(sessionDao: SessionDao, userDao: UserDao) {

  val mda = MessageDigest.getInstance("SHA-512")
  val cookieHeader = "X-Auth-Token"

  def login(userCode: String, password: String): Try[Cookie] = {
    val userD = userDao.checkUser(userCode, password)
    userD.flatMap { user =>
      createCookie(user)
    }
  }

  def register(userCode: String, fullName: String, password: String): Try[Cookie] = {
    val userD = userDao.insertUser(userCode, fullName, password)
    userD.flatMap { user =>
      createCookie(user)
    }
  }

  def checkCookie(header: RequestHeader): Try[Option[User]] = {
    val maybeCookie = header.cookies.get(cookieHeader)
    val maybeUserD = maybeCookie match {
      case Some(cookie) =>
        val maybeUserSessionD = sessionDao.findSession(cookie.value)
        maybeUserSessionD.flatMap {
          case Some(userSession) => userDao.findById(userSession.userId)
          case None => Success(None)
        }
      case None => Success(None)
    }
    maybeUserD
  }

  def destroySession(header: RequestHeader): Try[Unit] = {
    val maybeCookie = header.cookies.get(cookieHeader)
    maybeCookie match {
      case Some(cookie) => sessionDao.deleteSession(cookie.value)
      case None => Success(Unit)
    }
  }

  private def createCookie(user: User): Try[Cookie] = {
    val randomPart = UUID.randomUUID().toString.toUpperCase
    val userPart = user.userId.toString.toUpperCase
    val key = s"$randomPart|$userPart"
    val token = Base64.encodeBase64String(mda.digest(key.getBytes))
    val duration = Duration.create(10, TimeUnit.HOURS)

    val userSession = UserSession.create(user, token, duration.toSeconds)
    val insertV = sessionDao.insertSession(userSession)
    insertV.map { insert =>
      Cookie(cookieHeader, token, maxAge = Some(duration.toSeconds.toInt), httpOnly = true)
    }
  }
}
