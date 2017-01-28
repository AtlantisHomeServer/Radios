package org.atlantis.api.radio.service

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
class ejabberdService @Inject()(config: ConfigModule, jacksonMapper: FinatraObjectMapper) {
  private val adminId: String = config.getString("ejabberd.credential.id").get
  private val adminPw: String = config.getString("ejabberd.credential.pw").get
  private val serverAddress: String = config.getString("ejabberd.address").get
  private val hostname: String = config.getString("ejabberd.hostname").getOrElse(serverAddress.split(":").head)

  private val ejabberdClient: HttpRestApi = new HttpRestApi(BasicCredential(serverAddress, adminId, adminPw))


  def getOnlineUsers: Future[List[SimpleUserInfo]] =
    ejabberdClient.httpPost("connected_users", "{}").map(parseOnlineUser)

  def getUserStatus(userId: String): Future[SimpleUserInfo] =
    ejabberdClient.httpPost("get_last", s"""{"user": "$userId", "host": "$hostname"}""")
      .map{response =>
        SimpleUserInfo(userId, hostname, None, jacksonMapper.parse[LastActivity](response.contentString))
      }

  private def parseOnlineUser(userListResponse: Response): List[SimpleUserInfo] =
    jacksonMapper.parse[List[String]](userListResponse.contentString)
      .map{ userInfo =>
        SimpleUserInfo(userInfo.split("@").head, userInfo.split("@")(1).split("/").head, Some(userInfo.split("/")(1)), LastActivity.createOnline)
      }
}

case class SimpleUserInfo(userId: String, userHost: String, userResource: Option[String], lastActivity: LastActivity)
case class LastActivity(last_activity: String) {
  def isOnline: Boolean = last_activity == LastActivity.USER_ONLINE
}
object LastActivity {
  val USER_ONLINE = "Online"
  def createOnline: LastActivity = new LastActivity(USER_ONLINE)
}