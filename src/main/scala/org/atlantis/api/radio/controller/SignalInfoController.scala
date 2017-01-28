package org.atlantis.api.radio.controller

import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.json.FinatraObjectMapper
import io.swagger.models.Swagger
import org.atlantis.api.radio.service.{LastActivity, SimpleUserInfo, ejabberdService}
import com.twitter.finatra.http.Controller


/**
  * Created by ktz on 17. 1. 28.
  */
object SampleSwagger extends Swagger
class SignalInfoController @Inject()(signalService: ejabberdService, mapper: FinatraObjectMapper) extends Controller with SwaggerSupport{
  protected implicit val swagger: Swagger = SampleSwagger
  val HTTP_OK: Int = 200
  val HTTP_NOT_FOUND: Int = 400

  getWithDoc("/online"){o =>
    o.summary("get all online user")
      .tag("Get Online User")
      .produces("application/json")
      .responseWith[List[SimpleUserInfo]](HTTP_OK, "get onlineUser", example = Some(List(SimpleUserInfo("admin", "localhost", Some("temp"), LastActivity.createOnline))))
      .responseWith[Unit](HTTP_NOT_FOUND, "Some Error occur")
  }{request: Request =>
    signalService.getOnlineUsers.map(response.ok.json)
  }

  getWithDoc("/user/:id"){ o =>
    o.summary("Check if user online")
      .tag("Get User Status").produces("application/json")
      .routeParam[String]("userId", "User ID")
      .responseWith[SimpleUserInfo](HTTP_OK, "get user status", example = Some(SimpleUserInfo("admin", "localhost", Some("temp"), LastActivity.createOnline)))
      .responseWith[Unit](HTTP_NOT_FOUND, "Some Error occur")
  }{ request: Request =>
    val userId: String = request.getParam("id")
    signalService.getUserStatus(userId).map(response.ok.json)
  }
}
