package org.atlantis.api.radio.persistence.ejabberd

import com.google.inject.{Inject, Singleton}
import com.twitter.finagle.http.Response
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.util.Future
import org.atlantis.api.radio.core.restapi.{BasicCredential, HttpRestApi}
import org.atlantis.api.radio.core.typesafe.ConfigModule

/**
  * Created by ktz on 17. 1. 22.
  */

@Singleton
class ChatInfoRepository @Inject()(config: ConfigModule, jacksonMapper: FinatraObjectMapper) {
  private val adminId: String = config.getString("ejabberd.credential.id").get
  private val adminPw: String = config.getString("ejabberd.credential.pw").get
  private val serverAddress: String = config.getString("ejabberd.address").get
  private val hostname: String = config.getString("ejabberd.hostname").getOrElse(serverAddress.split(":").head)

  private val ejabberdClient: HttpRestApi = new HttpRestApi(BasicCredential(serverAddress, adminId, adminPw))


  def getOnlineUsers: Future[List[SimpleUserInfo]] =
    ejabberdClient.httpPost("connected_users", "{}").map(parseJidList)

  def getAllUser(hostname: String = this.hostname): Future[List[SimpleUserInfo]] =
    ejabberdClient.httpPost("registered_users", s"""{"host": "$hostname"}""")
      .map(userListResponse => parseUserList(userListResponse, hostname))

  private def parseUserList(userListResponse: Response, hostname: String): List[SimpleUserInfo] =
    jacksonMapper.parse[List[String]](userListResponse.contentString).map(userId => SimpleUserInfo(userId, hostname, None))

  private def parseJidList(userListResponse: Response): List[SimpleUserInfo] =
    jacksonMapper.parse[List[String]](userListResponse.contentString).map(StringToSimpleUserInfo)

  private def StringToSimpleUserInfo(userInfo: String): SimpleUserInfo =
    SimpleUserInfo(userInfo.split("@").head, userInfo.split("@")(1).split("/").head, Some(userInfo.split("/")(1)))
}

case class SimpleUserInfo(userId: String, userHost: String, userResource: Option[String])