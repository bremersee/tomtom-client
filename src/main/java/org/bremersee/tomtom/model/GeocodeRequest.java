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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * The geocode request.
 *
 * @author Christian Bremer
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GeocodeRequest extends AbstractGeocodeRequest {

  /**
   * Query string. Must be properly URL encoded.
   */
  private String query;

  /**
   * Comma separated string of country codes. This will limit the search to the specified
   * countries.
   */
  private List<Locale> countrySet;

  /**
   * Latitude and longitude where results should be biased NOTE: supplying a lat/lon without a
   * radius will return search results biased to that point.
   */
  private LatLonAware latLon;

  /**
   * If radius and position are set, the results will be constrained to the defined area. The radius
   * parameter is specified in meters.
   */
  private Integer radius;

  /**
   * Top left and bottom right position of the bounding box.
   */
  private BoundingBox boundingBox;

  /**
   * Instantiates a new geocode request.
   *
   * @param limit the limit
   * @param ofs the ofs
   * @param language the language
   * @param query the query
   * @param countrySet the country set
   * @param latLon the lat lon
   * @param radius the radius
   * @param boundingBox the bounding box
   */
  @Builder
  public GeocodeRequest(
      Integer limit,
      Integer ofs,
      Language language,
      String query,
      List<Locale> countrySet,
      LatLonAware latLon,
      Integer radius,
      BoundingBox boundingBox) {
    super(limit, ofs, language);
    this.query = query;
    this.countrySet = countrySet;
    this.latLon = latLon;
    this.radius = radius;
    this.boundingBox = boundingBox;
  }

  @Override
  public String getPath() {
    return "/geocode/" + urlEncode(query, true) + ".json";
  }

  @Override
  protected MultiValueMap<String, String> buildRequestParameters(boolean urlEncode) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    if (countrySet != null && !countrySet.isEmpty()) {
      final Set<String> countryCodes = new LinkedHashSet<>();
      for (final Locale country : countrySet) {
        if (StringUtils.hasText(country.getCountry())) {
          countryCodes.add(country.getCountry());
        }
      }
      if (countryCodes.isEmpty()) {
        map.set("countrySet", StringUtils.collectionToCommaDelimitedString(countryCodes));
      }
    }
    if (latLon != null && latLon.getLatitude() != null && latLon.getLongitude() != null) {
      map.set("lat", latLon.getLatitude().toPlainString());
      map.set("lon", latLon.getLongitude().toPlainString());
    }
    if (radius != null) {
      map.set("radius", radius.toString());
    }
    if (boundingBox != null && boundingBox.hasValues()) {
      map.set("topLeft", boundingBox.getTopLeftPoint().getLatitude().toPlainString()
          + "," + boundingBox.getTopLeftPoint().getLongitude().toPlainString());
      map.set("btmRight", boundingBox.getBtmRightPoint().getLatitude().toPlainString()
          + "," + boundingBox.getBtmRightPoint().getLongitude().toPlainString());
    }
    return map;
  }
}
