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

import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.internalauth.client.FrontendAuthComponents
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import uk.gov.hmrc.serviceconfigurationadminfrontend.auth.Authentication
import uk.gov.hmrc.serviceconfigurationadminfrontend.views.html.LogoutView

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class LogoutController @Inject()(
                                  internalAuth: FrontendAuthComponents,
                                  val controllerComponents: MessagesControllerComponents,
                                  logoutView: LogoutView
                               ) (implicit ec: ExecutionContext)
  extends FrontendBaseController 
    with I18nSupport 
    with Authentication(internalAuth) {

  def loggingOut: Action[AnyContent] = authorised(routes.IndexController.onPageLoad).async {
    Future.successful(Redirect(uk.gov.hmrc.serviceconfigurationadminfrontend.controllers.routes.LogoutController.loggedOut).withNewSession)
  }

  def loggedOut: Action[AnyContent] = Action.async { implicit request =>
      Future.successful(Ok(logoutView()))
  }
}
