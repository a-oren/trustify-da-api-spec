/*
 * Copyright 2023-2025 Trustify Dependency Analytics Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.guacsec.trustifyda.api.v5;

public class SeverityUtils {

  public static Severity fromValue(String value) {
    if (value == null) {
      return null;
    }
    return Severity.valueOf(value.toUpperCase());
  }

  // From: https://nvd.nist.gov/vuln-metrics/cvss
  public static Severity fromScore(Float score) {
    if (score == null) {
      return Severity.UNKNOWN;
    }
    if (score < 4) {
      return Severity.LOW;
    }
    if (score < 7) {
      return Severity.MEDIUM;
    }
    if (score < 9) {
      return Severity.HIGH;
    }
    return Severity.CRITICAL;
  }
}
