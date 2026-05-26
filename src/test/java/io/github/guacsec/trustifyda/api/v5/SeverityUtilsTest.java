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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class SeverityUtilsTest {

  /** Verifies that fromValue("UNKNOWN") returns Severity.UNKNOWN. */
  @Test
  public void testFromValueUnknown() {
    assertEquals(Severity.UNKNOWN, SeverityUtils.fromValue("UNKNOWN"));
  }

  /** Verifies that fromValue handles case-insensitive input for UNKNOWN. */
  @Test
  public void testFromValueUnknownCaseInsensitive() {
    assertEquals(Severity.UNKNOWN, SeverityUtils.fromValue("unknown"));
  }

  /** Verifies that fromValue returns null for null input. */
  @Test
  public void testFromValueNull() {
    assertNull(SeverityUtils.fromValue(null));
  }

  /** Verifies that fromScore returns only CRITICAL/HIGH/MEDIUM/LOW for all numeric ranges. */
  @Test
  public void testFromScoreDoesNotReturnUnknown() {
    // Given representative scores across all CVSS ranges
    float[] scores = {0.0f, 1.0f, 3.9f, 4.0f, 6.9f, 7.0f, 8.9f, 9.0f, 10.0f};

    for (float score : scores) {
      // When calculating severity from a numeric score
      Severity result = SeverityUtils.fromScore(score);

      // Then UNKNOWN must never be returned
      assertNotEquals(Severity.UNKNOWN, result,
          "fromScore(" + score + ") should not return UNKNOWN");
    }
  }

  /** Verifies the correct severity boundaries for fromScore. */
  @Test
  public void testFromScoreBoundaries() {
    assertEquals(Severity.LOW, SeverityUtils.fromScore(0.0f));
    assertEquals(Severity.LOW, SeverityUtils.fromScore(3.9f));
    assertEquals(Severity.MEDIUM, SeverityUtils.fromScore(4.0f));
    assertEquals(Severity.MEDIUM, SeverityUtils.fromScore(6.9f));
    assertEquals(Severity.HIGH, SeverityUtils.fromScore(7.0f));
    assertEquals(Severity.HIGH, SeverityUtils.fromScore(8.9f));
    assertEquals(Severity.CRITICAL, SeverityUtils.fromScore(9.0f));
    assertEquals(Severity.CRITICAL, SeverityUtils.fromScore(10.0f));
  }
}
