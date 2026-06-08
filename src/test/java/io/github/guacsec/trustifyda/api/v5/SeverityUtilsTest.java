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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SeverityUtilsTest {

  @Test
  void fromValueReturnsNullForNullInput() {
    assertNull(SeverityUtils.fromValue(null));
  }

  @Test
  void fromValueIsCaseInsensitive() {
    assertEquals(Severity.HIGH, SeverityUtils.fromValue("high"));
    assertEquals(Severity.CRITICAL, SeverityUtils.fromValue("CRITICAL"));
  }

  @Test
  void fromValueParsesUnknownSeverity() {
    assertEquals(Severity.UNKNOWN, SeverityUtils.fromValue("unknown"));
    assertEquals(Severity.UNKNOWN, SeverityUtils.fromValue("UNKNOWN"));
  }

  @Test
  void fromValueRejectsInvalidSeverity() {
    assertThrows(IllegalArgumentException.class, () -> SeverityUtils.fromValue("invalid"));
  }

  @Test
  void fromScoreReturnsUnknownForNullInput() {
    assertEquals(Severity.UNKNOWN, SeverityUtils.fromScore(null));
  }

  @Test
  void fromScoreMapsCvssRanges() {
    assertEquals(Severity.LOW, SeverityUtils.fromScore(0f));
    assertEquals(Severity.LOW, SeverityUtils.fromScore(3.9f));
    assertEquals(Severity.MEDIUM, SeverityUtils.fromScore(4f));
    assertEquals(Severity.MEDIUM, SeverityUtils.fromScore(6.9f));
    assertEquals(Severity.HIGH, SeverityUtils.fromScore(7f));
    assertEquals(Severity.HIGH, SeverityUtils.fromScore(8.9f));
    assertEquals(Severity.CRITICAL, SeverityUtils.fromScore(9f));
    assertEquals(Severity.CRITICAL, SeverityUtils.fromScore(10f));
  }
}
