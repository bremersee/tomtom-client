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
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * The reverse geocode request.
 *
 * @author Christian Bremer
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReverseGeocodeRequest extends AbstractReverseGeocodeRequest {

  /**
   * To enable return of the posted speed limit (where available).
   */
  private Boolean returnSpeedLimit;

  /**
   * If a number is sent in along with the request, the response may include the side of the street
   * (Left/Right) and an offset position for that number.
   */
  private String number;

  /**
   * Enables return of the road use array for reverse geocodes at street level.
   */
  private Boolean returnRoadUse;

  /**
   * Restricts reverse geocodes to a certain type of road use. The road use array for reverse
   * geocodes can be one or more of: ["LimitedAccess", "Arterial", "Terminal", "Ramp", "Rotary",
   * "LocalStreet"].
   */
  private List<RoadUse> roadUse;

  /**
   * Instantiates a new reverse geocode request.
   *
   * @param position         the position
   * @param heading          the heading
   * @param radius           the radius
   * @param returnSpeedLimit the return speed limit
   * @param number           the number
   * @param returnRoadUse    the return road use
   * @param roadUse          the road use
   */
  @Builder
  public ReverseGeocodeRequest(
      LatLonAware position,
      BigDecimal heading,
      Integer radius,
      Boolean returnSpeedLimit,
      String number,
      Boolean returnRoadUse,
      List<RoadUse> roadUse) {

    super("/", position, heading, radius);
    this.returnSpeedLimit = returnSpeedLimit;
    this.number = number;
    this.returnRoadUse = returnRoadUse;
    this.roadUse = roadUse;
  }

  @Override
  protected MultiValueMap<String, String> buildRequestParameters(boolean urlEncode) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    if (returnSpeedLimit != null) {
      map.set("returnSpeedLimit", returnSpeedLimit.toString());
    }
    if (StringUtils.hasText(number)) {
      map.set("number", urlEncode(number, urlEncode));
    }
    if (returnRoadUse != null) {
      map.set("returnRoadUse", returnRoadUse.toString());
    }
    if (roadUse != null && !roadUse.isEmpty()) {
      map.set("roadUse", urlEncode(StringUtils.collectionToCommaDelimitedString(
          roadUse.stream().map(RoadUse::getValue).collect(Collectors.toList())), urlEncode));
    }
    return map;
  }

  /**
   * The enum road use.
   */
  @SuppressWarnings("unused")
  public enum RoadUse {

    /**
     * Limit access road use.
     */
    LIMIT_ACCESS("LimitedAccess"),

    /**
     * Arterial road use.
     */
    ARTERIAL("Arterial"),

    /**
     * Terminal road use.
     */
    TERMINAL("Terminal"),

    /**
     * Ramp road use.
     */
    RAMP("Ramp"),

    /**
     * Rotary road use.
     */
    ROTARY("Rotary"),

    /**
     * Local street road use.
     */
    LOCAL_STREET("LocalStreet");

    @Getter
    private String value;

    RoadUse(String value) {
      this.value = value;
    }

    /**
     * Find road use from value.
     *
     * @param value the value
     * @return the road use (can be {@code null})
     */
    public static RoadUse fromValue(String value) {
      for (RoadUse roadUse : RoadUse.values()) {
        if (roadUse.getValue().equalsIgnoreCase(value)) {
          return roadUse;
        }
      }
      return null;
    }
  }
}
