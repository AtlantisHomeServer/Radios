package org.atlantis.api.radio.controller

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import org.atlantis.api.radio.RadioServer

/**
  * Created by ktz on 17. 1. 28.
  */
class SignalInfoControllerTest extends FeatureTest{
  override protected def server: EmbeddedHttpServer = new EmbeddedHttpServer(new RadioServer)

  "SignalInfoController" should {
    "get online user well" in {
      server.httpGet("/online", andExpect = Status.Ok)
    }

    "get user status well" in {
      server.httpGet("/user/admin", andExpect = Status.Ok)
    }
  }
}
