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

package io.github.guacsec.trustifyda.api.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.guacsec.trustifyda.api.PackageRef;

class PackageURLDeserializerTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void deserializeFromString() throws JsonProcessingException {
    String purl = "pkg:maven/io.quarkus/quarkus-resteasy-multipart@2.13.8.Final-redhat-00005?repository_url=https%3A%2F%2Fmaven.repository.redhat.com%2Fga%2F&type=jar";
    PackageRef ref = mapper.readValue("\"" + purl + "\"", PackageRef.class);
    assertEquals(purl, ref.ref());
    assertEquals("jar", ref.purl().getQualifiers().get("type"));
  }

  @Test
  void deserializeFromStringRoundTrip() throws JsonProcessingException {
    String purl = "pkg:maven/org.apache/commons-lang3@3.12.0";
    PackageRef ref = mapper.readValue("\"" + purl + "\"", PackageRef.class);
    assertEquals(purl, ref.ref());
    assertEquals(mapper.writeValueAsString(ref), "\"" + purl + "\"");
  }

  @Test
  void deserializeInvalidStringThrows() {
    assertThrows(JsonProcessingException.class,
        () -> mapper.readValue("\"not-a-valid-purl\"", PackageRef.class));
  }

  @Test
  void deserializeRejectsNonStringJson() {
    assertThrows(JsonProcessingException.class,
        () -> mapper.readValue("{\"type\":\"maven\"}", PackageRef.class));
  }
}
