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

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.packageurl.PackageURL;

import io.github.guacsec.trustifyda.api.PackageRef;

public class PackageURLSerializerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerialize() throws JsonProcessingException {
        var expected = "pkg:maven/io.quarkus/quarkus-resteasy-multipart@2.13.8.Final-redhat-00005?repository_url=https%3A%2F%2Fmaven.repository.redhat.com%2Fga%2F&type=jar";
        var pkgRef = new PackageRef(expected);
        assertEquals("jar", pkgRef.purl().getQualifiers().get("type"));
        assertEquals("https://maven.repository.redhat.com/ga/", pkgRef.purl().getQualifiers().get("repository_url"));
        assertEquals("\"" + expected + "\"", mapper.writeValueAsString(pkgRef));
        
        var rpmPkg = "pkg:rpm/opensuse/curl@7.56.1-1.1.?arch=i386&distro=opensuse-tumbleweed";
        pkgRef = new PackageRef(rpmPkg);
        assertEquals("opensuse-tumbleweed", pkgRef.purl().getQualifiers().get("distro"));
        assertEquals("i386", pkgRef.purl().getQualifiers().get("arch"));
        assertEquals("\"" + rpmPkg + "\"", mapper.writeValueAsString(pkgRef));
    }

    @Test
    void serializesPackageUrlDirectly() throws Exception {
        var mapperWithSerializer = new ObjectMapper()
            .registerModule(new SimpleModule().addSerializer(
                PackageURL.class, new PackageURLSerializer()));
        var purl = new PackageURL("pkg:maven/org.example/artifact@1.0.0");
        assertEquals("\"pkg:maven/org.example/artifact@1.0.0\"",
            mapperWithSerializer.writeValueAsString(purl));
    }
}
