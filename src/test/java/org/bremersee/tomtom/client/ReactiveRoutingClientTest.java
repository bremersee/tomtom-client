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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import org.bremersee.tomtom.model.LatLonAware;
import org.bremersee.tomtom.model.RoutingRequest;
import org.bremersee.tomtom.model.RoutingRequest.Hilliness;
import org.bremersee.tomtom.model.RoutingRequest.InstructionsType;
import org.bremersee.tomtom.model.RoutingRequest.Report;
import org.bremersee.tomtom.model.RoutingRequest.RouteRepresentation;
import org.bremersee.tomtom.model.RoutingRequest.RouteType;
import org.bremersee.tomtom.model.RoutingRequest.TravelMode;
import org.bremersee.tomtom.model.RoutingRequest.TravelTime;
import org.bremersee.tomtom.model.RoutingRequest.Windingness;
import org.bremersee.tomtom.model.RoutingResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * Test of the reactive routing client.
 *
 * @author Christian Bremer
 */
public class ReactiveRoutingClientTest extends Setup {

  private static ReactiveRoutingClient routingClient;

  /**
   * Init test.
   */
  @BeforeClass
  public static void init() {
    setup();
    routingClient = new ReactiveRoutingClientImpl(properties, null, null);
    ((ReactiveRoutingClientImpl) routingClient).setObjectMapper(objectMapper);
  }

  @Test
  public void testLongRouting() throws Exception {
    if (!StringUtils.hasText(properties.getKey())) {
      return;
    }

    LatLonAware p0 = LatLonAware
        .builder()
        .latitude(52.35116)
        .longitude(10.18285)
        .build();
    LatLonAware p1 = LatLonAware
        .builder()
        .latitude(52.22654)
        .longitude(10.5569)
        .build();
    LatLonAware p2 = LatLonAware
        .builder()
        .latitude(51.906)
        .longitude(10.4266)
        .build();

    RoutingRequest req = RoutingRequest
        .builder()
        .locations(Arrays.asList(p0, p1, p2))
        .instructionsType(InstructionsType.TEXT)
        .routeRepresentation(RouteRepresentation.POLYLINE)
        .travelMode(TravelMode.MOTORCYCLE)
        .routeType(RouteType.THRILLING)
        .hilliness(Hilliness.NORMAL)
        .windingness(Windingness.HIGH)
        .departAt(new Date(System.currentTimeMillis() + 1000L * 60L * 60L))
        .build();

    Mono<RoutingResponse> mono = routingClient.calculateRoute(req);
    Assert.assertNotNull(mono);
    RoutingResponse res = mono.block();
    Assert.assertNotNull(res);

    System.out.println("### Routing response:");
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
  }

  @Test
  public void testShortRouting() throws Exception {
    if (!StringUtils.hasText(properties.getKey())) {
      return;
    }

    LatLonAware p0 = LatLonAware
        .builder()
        .latitude(52.35116)
        .longitude(10.18285)
        .build();
    LatLonAware p1 = LatLonAware
        .builder()
        .latitude(52.34814)
        .longitude(10.18638)
        .build();

    RoutingRequest req = RoutingRequest
        .builder()
        .locations(Arrays.asList(p0, p1))
        .instructionsType(InstructionsType.TAGGED)
        .routeRepresentation(RouteRepresentation.POLYLINE)
        .travelMode(TravelMode.PEDESTRIAN)
        .routeType(RouteType.FASTEST)
        .departAt(new Date(System.currentTimeMillis() + 1000L * 60L * 60L))
        .build();

    Mono<RoutingResponse> mono = routingClient.calculateRoute(req);
    Assert.assertNotNull(mono);
    RoutingResponse res = mono.block();
    Assert.assertNotNull(res);

    System.out.println("### Routing response:");
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
  }

  @Test
  public void testShortRoutingWithPost() throws Exception {
    if (!StringUtils.hasText(properties.getKey())) {
      return;
    }

    LatLonAware p0 = LatLonAware
        .builder()
        .latitude(52.35116)
        .longitude(10.18285)
        .build();
    LatLonAware p1 = LatLonAware
        .builder()
        .latitude(52.34814)
        .longitude(10.18638)
        .build();

    RoutingRequest req = RoutingRequest
        .builder()
        .locations(Arrays.asList(p0, p1))
        .language(Locale.GERMAN)
        //.alternativeType(AlternativeType.ANY_ROUTE)
        // cannot specify alternativeType without route to reconstruct -> supportingPoints?
        .instructionsType(InstructionsType.TAGGED)
        .routeRepresentation(RouteRepresentation.POLYLINE)
        .travelMode(TravelMode.TRUCK)
        .routeType(RouteType.FASTEST)
        .computeBestOrder(Boolean.FALSE)
        // cannot optimize waypoints and calculate alternative routes at same time!
        .computeTravelTimeFor(TravelTime.ALL)
        //.maxAlternatives(3)
        .report(Report.EFFECTIVE_SETTINGS)
        .traffic(Boolean.TRUE)
        //.avoidVignette(Arrays.asList(Locale.FRANCE, Locale.ITALY)) // you can't set both
        .allowVignette(Collections.singletonList(Locale.GERMANY))
        .departAt(new Date(System.currentTimeMillis() + 1000L * 60L * 60L))
        .build();

    Mono<RoutingResponse> mono = routingClient.calculateRoute(req);
    Assert.assertNotNull(mono);
    RoutingResponse res = mono.block();
    Assert.assertNotNull(res);

    System.out.println("### Routing response:");
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
  }

}
