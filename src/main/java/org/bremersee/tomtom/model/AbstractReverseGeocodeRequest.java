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
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * The abstract reverse geocode request.
 *
 * @author Christian Bremer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("WeakerAccess")
public abstract class AbstractReverseGeocodeRequest extends AbstractRequest {

  private String path;

  /**
   * This is specified as a comma separated string composed of lat., lon.
   */
  @Getter
  private LatLonAware position;

  /**
   * The directional heading in degrees, usually similar to the course along a road segment. Entered
   * in degrees, measured clockwise from north (so north is 0, east is 90, etc.)
   */
  @Getter
  private BigDecimal heading;

  /**
   * The maximum distance in meters from the specified position for the reverse geocoder to
   * consider.
   */
  @Getter
  private Integer radius;

  /**
   * Instantiates a new abstract reverse geocode request.
   *
   * @param path the path
   * @param position the position
   * @param heading the heading
   * @param radius the radius
   */
  protected AbstractReverseGeocodeRequest(
      @NotNull String path,
      LatLonAware position,
      BigDecimal heading,
      Integer radius) {

    this.path = path;
    this.position = position;
    this.heading = heading;
    this.radius = radius;
  }

  /**
   * Gets path with position and format, e. g.: /37.8328,-122.27669.json
   *
   * @return the path
   */
  public String getPath() {
    final String lat = position != null && position.getLatitude() != null
        ? position.getLatitude().toPlainString()
        : "0";
    final String lon = position != null && position.getLongitude() != null
        ? position.getLongitude().toPlainString()
        : "0";
    return path + lat + "," + lon + ".json";
  }

  /**
   * Build the request parameter map.
   *
   * @param urlEncode if {@code true}, the parameter values will be encoded, otherwise not.
   * @return the request parameter map
   */
  public final MultiValueMap<String, String> buildParameters(final boolean urlEncode) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    if (heading != null) {
      map.set("heading", heading.toPlainString());
    }
    if (radius != null) {
      map.set("radius", radius.toString());
    }
    map.putAll(buildRequestParameters(urlEncode));
    return map;
  }

  /**
   * Build request parameters of sub classes.
   *
   * @param urlEncode the url encode
   * @return the multi value map
   */
  protected abstract MultiValueMap<String, String> buildRequestParameters(boolean urlEncode);


}
