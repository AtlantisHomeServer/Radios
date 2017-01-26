package org.atlantis.api.radio.core.typesafe

import com.google.inject.Singleton
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by ktz on 17. 1. 26.
  */

@Singleton
class ConfigModule {
  private val config: Config = ConfigFactory.load

  def getString(path: String): Option[String] =
    getIfExist(path)(() => config.getString(path))

  def getInt(path: String): Option[Int] =
    getIfExist(path)(() => config.getInt(path))

  private def getIfExist[A](path: String)(getFunction : () => A): Option[A] =
    if(config.hasPath(path)) Some(getFunction())
    else None
}
