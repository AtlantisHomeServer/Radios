package org.atlantis.api.radio.service.signaling

import com.twitter.inject.app.TestInjector
import com.twitter.inject.{Injector, IntegrationTest}
import com.twitter.util.Await
import org.atlantis.api.radio.core.jackson.CustomJacksonModule
import org.atlantis.api.radio.service.{LastActivity, SimpleUserInfo, ejabberdService}

/**
  * Created by ktz on 17. 1. 27.
  */
class ejabberdServiceTest extends IntegrationTest{
  override protected def injector: Injector = TestInjector(CustomJacksonModule)

  val ejabberdClient: ejabberdService = injector.instance[ejabberdService]

  "ejabberd test" should {
    "Get online user well" in {
      val onlineUser: List[SimpleUserInfo] = Await.result(ejabberdClient.getOnlineUsers)
      println(onlineUser)
      onlineUser.size should be >= 0
    }

    "Get user status well" in {
      val userStatus: SimpleUserInfo = Await.result(ejabberdClient.getUserStatus("admin"))
      println(userStatus)
    }
  }
}
