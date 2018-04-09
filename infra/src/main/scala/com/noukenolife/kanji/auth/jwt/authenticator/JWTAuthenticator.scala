package com.noukenolife.kanji.auth.jwt.authenticator

import java.util.UUID

import cats.data.EitherT
import cats.implicits._
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.jwt.config.JWTAuthenticatorConfig
import com.noukenolife.kanji.auth.jwt.error.InvalidToken
import com.noukenolife.kanji.auth.repository.AccountRepository
import com.noukenolife.kanji.auth.value.{AccountId, Credential}
import com.noukenolife.kanji.support.{IOContext, InfraError}
import com.noukenolife.kanji.util.TryEither
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, Formats}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtJson4s}

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait JWTAuthenticatorLike {
  def secretKey: String
  def issuer: Option[String]
  def subject: Option[String]
  def audience: Option[Set[String]]
  def duration: Option[Duration]

  implicit def formats: Formats

  def init(credential: Credential, algorithm: JwtAlgorithm = JwtAlgorithm.HS256)
    (implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, InfraError, String]
  def retrieve(token: String)(implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, InfraError, Account]
}

case class JWTAuthenticator(
  secretKey: String,
  issuer: Option[String] = None,
  subject: Option[String] = None,
  audience: Option[Set[String]] = None,
  duration: Option[Duration] = None
)(implicit accountRepository: AccountRepository) extends JWTAuthenticatorLike {

  override implicit val formats = DefaultFormats

  override def init(credential: Credential, algorithm: JwtAlgorithm = JwtAlgorithm.HS256)
    (implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, InfraError, String] = {

    accountRepository.resolveByCredential(credential).map { acc =>
      val claim = JwtClaim(
        content = Serialization.write(acc.id),
        issuer = issuer,
        subject = subject,
        audience = audience,
        jwtId = Some(UUID.randomUUID.toString)
      ).startsNow.issuedNow
      duration.map(d => claim.expiresIn(d.toSeconds)).getOrElse(claim)
    }.map(claim => Jwt.encode(claim, secretKey, algorithm)).leftMap(_ => InvalidToken())
  }

  override def retrieve(token: String)(implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, InfraError, Account] = {
    val claim = TryEither.toEither(JwtJson4s.decode(token, secretKey, JwtAlgorithm.allHmac), InvalidToken())
    val accId = claim.flatMap { c =>
      TryEither.toEither(Try(parse(c.content).extract[AccountId]), InvalidToken())
    }
    EitherT(Future(accId)).flatMap { id =>
      accountRepository.resolve(id).leftMap(_ => InvalidToken())
    }
  }
}

object JWTAuthenticator {

  def apply(config: JWTAuthenticatorConfig)(implicit accountRepository: AccountRepository): JWTAuthenticator = {
    JWTAuthenticator(
      config.secretKey,
      config.issuer,
      config.subject,
      config.audience,
      config.duration
    )
  }

  def apply()(implicit accountRepository: AccountRepository): JWTAuthenticator = {
    val config = ConfigFactory.load().as[JWTAuthenticatorConfig]("jwt.authenticator")
    JWTAuthenticator(config)
  }
}
