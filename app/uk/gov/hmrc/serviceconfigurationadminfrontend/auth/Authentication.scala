/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.serviceconfigurationadminfrontend.auth

import com.google.inject.Inject
import play.api.mvc.{AnyContent, Call}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.internalauth.client.*

import scala.concurrent.{ExecutionContext, Future}

trait Authentication @Inject()(internalAuth: FrontendAuthComponents)(implicit ec: ExecutionContext) {

  def authorisedForService(service: String, url: Call): AuthenticatedActionBuilder[Retrieval.Username, AnyContent] =
    internalAuth.authorizedAction(
      continueUrl = url,
      predicate = PredicateBuilder.forService(service).asAdmin,
      retrieval = Retrieval.username
    )

  def authorised(url: Call): AuthenticatedActionBuilder[Set[Resource], AnyContent] =
    internalAuth.authenticatedAction(
        continueUrl = url,
        retrieval = Retrieval.locations(Some(ResourceType("ddcn-live-admin-frontend")))
    )

  def retrievals(implicit hc: HeaderCarrier): Future[Option[Set[Resource] ~ Retrieval.Username]] = {
    val loc = Retrieval.locations(Some(ResourceType("ddcn-live-admin-frontend")))
    val name = Retrieval.username
    val composed: Retrieval[Set[Resource] ~ Retrieval.Username] = loc ~ name

    internalAuth.verify(
      retrieval = composed
    )
  }
}
