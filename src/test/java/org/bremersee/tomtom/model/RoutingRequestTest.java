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

package org.bremersee.tomtom.model;

import org.bremersee.tomtom.model.RoutingRequest.AlternativeType;
import org.bremersee.tomtom.model.RoutingRequest.Avoid;
import org.bremersee.tomtom.model.RoutingRequest.Hilliness;
import org.bremersee.tomtom.model.RoutingRequest.InstructionsType;
import org.bremersee.tomtom.model.RoutingRequest.Report;
import org.bremersee.tomtom.model.RoutingRequest.RouteRepresentation;
import org.bremersee.tomtom.model.RoutingRequest.RouteType;
import org.bremersee.tomtom.model.RoutingRequest.TravelMode;
import org.bremersee.tomtom.model.RoutingRequest.TravelTime;
import org.bremersee.tomtom.model.RoutingRequest.VehicleLoadType;
import org.bremersee.tomtom.model.RoutingRequest.Windingness;
import org.junit.Assert;
import org.junit.Test;

/**
 * The routing request test.
 *
 * @author Christian Bremer
 */
public class RoutingRequestTest {

  /**
   * Test enums.
   */
  @Test
  public void testEnums() {
    Assert.assertEquals(
        AlternativeType.ANY_ROUTE,
        AlternativeType.fromValue(AlternativeType.ANY_ROUTE.getValue()));
    Assert.assertEquals(
        AlternativeType.BETTER_ROUTE,
        AlternativeType.fromValue(AlternativeType.BETTER_ROUTE.getValue()));
    Assert.assertNull(AlternativeType.fromValue("foo"));

    Assert.assertEquals(
        InstructionsType.CODED,
        InstructionsType.fromValue(InstructionsType.CODED.getValue()));
    Assert.assertEquals(
        InstructionsType.TAGGED,
        InstructionsType.fromValue(InstructionsType.TAGGED.getValue()));
    Assert.assertEquals(
        InstructionsType.TEXT,
        InstructionsType.fromValue(InstructionsType.TEXT.getValue()));
    Assert.assertNull(InstructionsType.fromValue("foo"));

    Assert.assertEquals(
        RouteRepresentation.NONE,
        RouteRepresentation.fromValue(RouteRepresentation.NONE.getValue()));
    Assert.assertEquals(
        RouteRepresentation.POLYLINE,
        RouteRepresentation.fromValue(RouteRepresentation.POLYLINE.getValue()));
    Assert.assertNull(RouteRepresentation.fromValue("foo"));

    Assert.assertEquals(
        TravelTime.NONE,
        TravelTime.fromValue(TravelTime.NONE.getValue()));
    Assert.assertEquals(
        TravelTime.ALL,
        TravelTime.fromValue(TravelTime.ALL.getValue()));
    Assert.assertNull(TravelTime.fromValue("foo"));

    Assert.assertEquals(
        Report.EFFECTIVE_SETTINGS,
        Report.fromValue(Report.EFFECTIVE_SETTINGS.getValue()));
    Assert.assertNull(Report.fromValue("foo"));

    Assert.assertEquals(
        RouteType.ECO,
        RouteType.fromValue(RouteType.ECO.getValue()));
    Assert.assertEquals(
        RouteType.FASTEST,
        RouteType.fromValue(RouteType.FASTEST.getValue()));
    Assert.assertEquals(
        RouteType.SHORTEST,
        RouteType.fromValue(RouteType.SHORTEST.getValue()));
    Assert.assertEquals(
        RouteType.THRILLING,
        RouteType.fromValue(RouteType.THRILLING.getValue()));
    Assert.assertNull(RouteType.fromValue("foo"));

    Assert.assertEquals(
        Avoid.ALREADY_USED_ROADS,
        Avoid.fromValue(Avoid.ALREADY_USED_ROADS.getValue()));
    Assert.assertEquals(
        Avoid.CARPOOLS,
        Avoid.fromValue(Avoid.CARPOOLS.getValue()));
    Assert.assertEquals(
        Avoid.FERRIES,
        Avoid.fromValue(Avoid.FERRIES.getValue()));
    Assert.assertEquals(
        Avoid.MOTORWAYS,
        Avoid.fromValue(Avoid.MOTORWAYS.getValue()));
    Assert.assertEquals(
        Avoid.TOLL_ROADS,
        Avoid.fromValue(Avoid.TOLL_ROADS.getValue()));
    Assert.assertEquals(
        Avoid.UNPAVED_ROADS,
        Avoid.fromValue(Avoid.UNPAVED_ROADS.getValue()));
    Assert.assertNull(Avoid.fromValue("foo"));

    Assert.assertEquals(
        TravelMode.BICYCLE,
        TravelMode.fromValue(TravelMode.BICYCLE.getValue()));
    Assert.assertEquals(
        TravelMode.BUS,
        TravelMode.fromValue(TravelMode.BUS.getValue()));
    Assert.assertEquals(
        TravelMode.CAR,
        TravelMode.fromValue(TravelMode.CAR.getValue()));
    Assert.assertEquals(
        TravelMode.MOTORCYCLE,
        TravelMode.fromValue(TravelMode.MOTORCYCLE.getValue()));
    Assert.assertEquals(
        TravelMode.PEDESTRIAN,
        TravelMode.fromValue(TravelMode.PEDESTRIAN.getValue()));
    Assert.assertEquals(
        TravelMode.TAXI,
        TravelMode.fromValue(TravelMode.TAXI.getValue()));
    Assert.assertEquals(
        TravelMode.TRUCK,
        TravelMode.fromValue(TravelMode.TRUCK.getValue()));
    Assert.assertEquals(
        TravelMode.VAN,
        TravelMode.fromValue(TravelMode.VAN.getValue()));
    Assert.assertNull(TravelMode.fromValue("foo"));

    Assert.assertEquals(
        Hilliness.LOW,
        Hilliness.fromValue(Hilliness.LOW.getValue()));
    Assert.assertEquals(
        Hilliness.NORMAL,
        Hilliness.fromValue(Hilliness.NORMAL.getValue()));
    Assert.assertEquals(
        Hilliness.HIGH,
        Hilliness.fromValue(Hilliness.HIGH.getValue()));
    Assert.assertNull(Hilliness.fromValue("foo"));

    Assert.assertEquals(
        Windingness.LOW,
        Windingness.fromValue(Windingness.LOW.getValue()));
    Assert.assertEquals(
        Windingness.NORMAL,
        Windingness.fromValue(Windingness.NORMAL.getValue()));
    Assert.assertEquals(
        Windingness.HIGH,
        Windingness.fromValue(Windingness.HIGH.getValue()));
    Assert.assertNull(Windingness.fromValue("foo"));

    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_1,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_1.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_2,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_2.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_3,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_3.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_4,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_4.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_5,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_5.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_6,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_6.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_7,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_7.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_8,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_8.getValue()));
    Assert.assertEquals(
        VehicleLoadType.US_HAZMAT_CLASS_9,
        VehicleLoadType.fromValue(VehicleLoadType.US_HAZMAT_CLASS_9.getValue()));
    Assert.assertEquals(
        VehicleLoadType.OTHER_HAZMAT_EXPLOSIVE,
        VehicleLoadType.fromValue(VehicleLoadType.OTHER_HAZMAT_EXPLOSIVE.getValue()));
    Assert.assertEquals(
        VehicleLoadType.OTHER_HAZMAT_GENERAL,
        VehicleLoadType.fromValue(VehicleLoadType.OTHER_HAZMAT_GENERAL.getValue()));
    Assert.assertEquals(
        VehicleLoadType.OTHER_HAZMAT_HARMFUL_TO_WATER,
        VehicleLoadType.fromValue(VehicleLoadType.OTHER_HAZMAT_HARMFUL_TO_WATER.getValue()));
    Assert.assertNull(VehicleLoadType.fromValue("foo"));

  }

}
