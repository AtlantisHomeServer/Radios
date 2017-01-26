package org.atlantis.api.radio.core.restapi

import com.twitter.finagle.Http
import com.twitter.finagle.http.{BasicAuth, Method, Request, Response}
import com.twitter.util.Future

/**
  * Created by ktz on 17. 1. 26.
  */

case class BasicCredential(serverAddr: String, clientId: String, clientPW: String)

class HttpRestApi(apiCredential: BasicCredential) {

  private val basicAuth = BasicAuth.client(apiCredential.clientId, apiCredential.clientPW)
  private val httpService = Http.client.newService(apiCredential.serverAddr)

  def httpGet(requestUri: String): Future[Response] = requestHTTP(Method.Get, requestUri, None)

  def httpPost(requestUri: String, body: String): Future[Response] = requestHTTP(Method.Post, requestUri, Some(body))

  private def requestHTTP(httpMethod: Method, requestUri: String, body: Option[String]): Future[Response] = {
    val request = Request(httpMethod, s"/api/$requestUri")
    request.host = apiCredential.serverAddr
    if(body.isDefined)
      request.setContentString(body.get)
    basicAuth.andThen(httpService)(request)
  }
}