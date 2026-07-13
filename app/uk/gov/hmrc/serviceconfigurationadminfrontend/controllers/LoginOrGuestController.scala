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

import com.google.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import uk.gov.hmrc.serviceconfigurationadminfrontend.views.html.LoginOrGuestView
import java.time.{Instant, LocalDateTime, ZoneId}

class LoginOrGuestController @Inject()(
                                        val controllerComponents: MessagesControllerComponents,
                                        view: LoginOrGuestView
                                      ) extends FrontendBaseController with I18nSupport {

  def onPageLoad: Action[AnyContent] = Action { implicit request =>
    Ok(view()).withNewSession
  }

  def guest: Action[AnyContent] = Action { implicit request =>
    (request.session.get("ts"), request.session.get("sessionId")) match {
      case (Some(ts), Some(id)) =>
        val dt: LocalDateTime = Instant
          .ofEpochMilli(ts.toLong)
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime
        
        Redirect(uk.gov.hmrc.serviceconfigurationadminfrontend.controllers.routes.IndexController.onPageLoad)
          .withSession(request.session + ("guestSessionId" -> s"$id/$dt"))
      case _ =>
        Redirect(uk.gov.hmrc.serviceconfigurationadminfrontend.controllers.routes.LoginOrGuestController.onPageLoad)
    }
  }

}
