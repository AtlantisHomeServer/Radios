package org.atlantis.api.radio

import com.github.xiaodongw.swagger.finatra.{SwaggerController, WebjarsController}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import org.atlantis.api.radio.controller.{SampleSwagger, SignalInfoController}

/**
  * Created by ktz on 17. 1. 22.
  */

object RadioServerMain extends RadioServer

class RadioServer extends HttpServer{
  override protected def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[WebjarsController]
      .add[SignalInfoController]
      .add(new SwaggerController(swagger = SampleSwagger))
  }
}
