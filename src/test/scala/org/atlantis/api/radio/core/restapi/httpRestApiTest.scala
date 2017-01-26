package org.atlantis.api.radio.core.restapi

import com.twitter.inject.app.TestInjector
import com.twitter.inject.{Injector, IntegrationTest}
import com.twitter.util.Await
import org.atlantis.api.radio.core.jackson.CustomJacksonModule
import org.atlantis.api.radio.persistence.ejabberd.{ChatInfoRepository, SimpleUserInfo}

/**
  * Created by ktz on 17. 1. 27.
  */
class httpRestApiTest extends IntegrationTest{
  override protected def injector: Injector = TestInjector(CustomJacksonModule)

  val ejabberdClient: ChatInfoRepository = injector.instance[ChatInfoRepository]

  "ejabberd test" should {
    "Get online user well" in {
      val onlineUser: List[SimpleUserInfo] = Await.result(ejabberdClient.getOnlineUsers)
      println(onlineUser)
      onlineUser.size should be >= 0
    }

    "Get all user well" in {
      val allUser: List[SimpleUserInfo] = Await.result(ejabberdClient.getAllUser())
      println(allUser)
      allUser.size should be > 0
    }
  }
}
