/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.tomtom.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.bremersee.tomtom.TomTomProperties;

/**
 * The common test setup.
 *
 * @author Christian Bremer
 */
abstract class Setup {

  /**
   * The properties.
   */
  static TomTomProperties properties;

  /**
   * The object mapper.
   */
  static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Setup test.
   */
  static void setup() {
    properties = new TomTomProperties();
    properties.setKey(System.getProperty("tomTomKey"));
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
    objectMapper.registerModules(
        new Jdk8Module(),
        new JavaTimeModule(),
        new ParameterNamesModule());
  }

}
