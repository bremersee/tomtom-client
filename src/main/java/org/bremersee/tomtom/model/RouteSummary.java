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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.plain.model.UnknownAware;

/**
 * A summary of a route, or a route leg.
 *
 * @author Christian Bremer
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("WeakerAccess")
public class RouteSummary extends UnknownAware {

  private Integer lengthInMeters;

  private Integer travelTimeInSeconds;

  private Integer trafficDelayInSeconds;

  /**
   * The estimated departure time for the route or leg.
   */
  private OffsetDateTime departureTime;

  /**
   * The estimated arrival time for the route or leg.
   */
  private OffsetDateTime arrivalTime;

  private Integer noTrafficTravelTimeInSeconds;

  private Integer historicTrafficTravelTimeInSeconds;

  private Integer liveTrafficIncidentsTravelTimeInSeconds;

  private BigDecimal fuelConsumptionInLiters;

  private BigDecimal batteryConsumptionInkWh;

  private Integer deviationDistance;

  private Integer deviationTime;

  private LatitudeLongitude deviationPoint;

}
