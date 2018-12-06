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
import java.util.Collections;
import java.util.Locale;
import org.bremersee.tomtom.TomTomProperties;
import org.bremersee.tomtom.model.GeocodeRequest;
import org.bremersee.tomtom.model.GeocodeResponse;
import org.bremersee.tomtom.model.Language;
import org.bremersee.tomtom.model.LatLonAware;
import org.bremersee.tomtom.model.ReverseGeocodeRequest;
import org.bremersee.tomtom.model.ReverseGeocodeResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * The default geocoding client tests.
 *
 * @author Christian Bremer
 */
public class DefaultGeocodingClientTest {

  private static TomTomProperties properties;

  private static DefaultGeocodingClient geocodingClient;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Setup test.
   */
  @BeforeClass
  public static void setup() {
    properties = new TomTomProperties();
    properties.setKey(System.getProperty("tomTomKey"));

    geocodingClient = new DefaultGeocodingClient(properties, null);

    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
    objectMapper.registerModules(
        new Jdk8Module(),
        new JavaTimeModule(),
        new ParameterNamesModule());
  }

  /**
   * Test geocode.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGeocode() throws Exception {
    if (!StringUtils.hasText(properties.getKey())) {
      return;
    }

    final GeocodeRequest req = GeocodeRequest
        .builder()
        .countrySet(Collections.singletonList(Locale.GERMANY))
        .language(Language.fromLocale(Locale.GERMANY))
        .latLon(LatLonAware.builder()
            .latitude(52.3411)
            .longitude(10.18111)
            .build())
        .radius(8000)
        .limit(10)
        .query("Hauptstraße 56, Peine")
        .build();

    final GeocodeResponse res = geocodingClient.geocode(req);
    Assert.assertNotNull(res);

    System.out.println("### Geocoding response for query 'Hauptstraße 56, Peine':");
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));

    Assert.assertTrue(res.hasResults());
    Assert.assertTrue(
        res.getResults().stream().anyMatch(geocodeResult -> geocodeResult.getAddress() != null
            && "31228".equalsIgnoreCase(geocodeResult.getAddress().getPostalCode())
            && geocodeResult.getAddress().getStreetName() != null
            && geocodeResult.getAddress().getStreetName().contains("Hauptstraße")
            && "56".equalsIgnoreCase(geocodeResult.getAddress().getStreetNumber())));
  }

  /**
   * Test reverse geocode.
   *
   * @throws Exception the exception
   */
  @Test
  public void testReverseGeocode() throws Exception {
    if (!StringUtils.hasText(properties.getKey())) {
      return;
    }

    final ReverseGeocodeRequest req = ReverseGeocodeRequest
        .builder()
        .position(LatLonAware.builder()
            .latitude(52.35116)
            .longitude(10.18285)
            .build())
        .build();

    final ReverseGeocodeResponse res = geocodingClient.reverseGeocode(req);
    Assert.assertNotNull(res);

    System.out.println("### Reverse geocoding response for query '52.35116,10.18285':");
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));

    Assert.assertTrue(
        res.getAddresses().stream().anyMatch(geocodeResult -> geocodeResult.getAddress() != null
            && "31228".equalsIgnoreCase(geocodeResult.getAddress().getPostalCode())
            && geocodeResult.getAddress().getStreetName() != null
            && geocodeResult.getAddress().getStreetName().contains("Hauptstraße")
            && "56".equalsIgnoreCase(geocodeResult.getAddress().getStreetNumber())));
  }

}
