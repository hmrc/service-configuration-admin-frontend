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

package uk.gov.hmrc.serviceconfigurationadminfrontend.controllers

import play.api.Logging
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import uk.gov.hmrc.internalauth.client.FrontendAuthComponents
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import uk.gov.hmrc.serviceconfigurationadminfrontend.auth.Authentication
import uk.gov.hmrc.serviceconfigurationadminfrontend.config.ServiceDiscoveryConfig
import uk.gov.hmrc.serviceconfigurationadminfrontend.views.html.IndexView

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class IndexController @Inject()(
                                 serviceDiscoveryConfig: ServiceDiscoveryConfig,
                                 internalAuth: FrontendAuthComponents,
                                 val controllerComponents: MessagesControllerComponents,
                                 indexView: IndexView
                               )(implicit ec: ExecutionContext)
  extends FrontendBaseController 
  with I18nSupport 
  with Logging 
  with Authentication(internalAuth) {

  def onPageLoad: Action[AnyContent] =
    authorised(uk.gov.hmrc.serviceconfigurationadminfrontend.controllers.routes.IndexController.onPageLoad).async {
      request =>
        given Request[?] = request

        val autoDiscoveredServices: Seq[String] = serviceDiscoveryConfig.getAllAutoDiscoverServices.map(_.serviceName)
        val allowedResources = request.retrieval.map(_.resourceLocation.value).toSeq.sorted
        val myServices = autoDiscoveredServices.intersect(allowedResources)
        val otherServices = allowedResources.diff(autoDiscoveredServices)
        logger.debug(s"services from config: $autoDiscoveredServices")
        logger.debug(s"services allowed: $allowedResources")
        Future.successful(Ok(indexView(myServices, otherServices)))
    }
}
