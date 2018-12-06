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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * The cross street reverse geocode request.
 *
 * @author Christian Bremer
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CrossStreetReverseGeocodeRequest extends AbstractReverseGeocodeRequest {

  /**
   * Maximum number of cross-streets to return.
   */
  private Integer limit;

  /**
   * Language in which search results should be returned.
   */
  private Language language;

  /**
   * Instantiates a new cross street reverse geocode request.
   *
   * @param position the position
   * @param heading the heading
   * @param radius the radius
   * @param limit the limit
   * @param language the language
   */
  @Builder
  public CrossStreetReverseGeocodeRequest(
      LatLonAware position,
      BigDecimal heading,
      Integer radius,
      Integer limit,
      Language language) {

    super("/crossStreet/", position, heading, radius);
    this.limit = limit;
    this.language = language;
  }

  @Override
  protected MultiValueMap<String, String> buildRequestParameters(boolean urlEncode) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    if (limit != null) {
      map.set("limit", limit.toString());
    }
    if (language != null) {
      map.set("language", urlEncode(language.getValue(), urlEncode));
    }
    return map;
  }

}
