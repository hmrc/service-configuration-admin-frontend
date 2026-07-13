/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.serviceconfigurationadminfrontend.config

import play.api.Configuration
import uk.gov.hmrc.serviceconfigurationadminfrontend.models.ServiceModel

import javax.inject.Inject
import scala.jdk.CollectionConverters.*

class ServiceDiscoveryConfig @Inject()(configuration: Configuration) {

  val getAndSetAllAdminUrl = "/admin/featureFlags"
  val setOneAdminUrl = "/admin/featureFlags/"

  lazy val config = configuration.underlying

  private def baseUrl(serviceName: String): String = {
    val protocol = configuration.get[String](s"microservice.services.$serviceName.protocol")
    val host = configuration.get[String](s"microservice.services.$serviceName.host")
    val port = configuration.get[Int](s"microservice.services.$serviceName.port")
    s"$protocol://$host:$port"
  }

  def getService(serviceName: String): ServiceModel = {
    ServiceModel(serviceName, baseUrl(serviceName))
  }

  def getAllAutoDiscoverServices: Seq[ServiceModel] = {
    val serviceList: Seq[String] = config.getStringList("features.autodiscover").asScala.toSeq
    serviceList.map(getService)
  }

}
