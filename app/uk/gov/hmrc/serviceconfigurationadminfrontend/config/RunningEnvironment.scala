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

import javax.inject.Inject

class RunningEnvironment @Inject()(config: Configuration) {

  def get: String = config.get[String]("features.environment")

  def styling: String =
    config.get[String]("features.environment") match {
      case "Production" => "govuk-tag--red"
      case "Qa" => "govuk-tag--yellow"
      case "Staging" => "govuk-tag--yellow"
      case _ => "govuk-tag--blue"
    }

}
