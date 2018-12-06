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

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * The structured geocode request.
 *
 * @author Christian Bremer
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StructuredGeocodeRequest extends AbstractGeocodeRequest {

  /**
   * 2 or 3 letter country code (e.g.: FR, ES). Required!
   */
  private String countryCode;

  /**
   * The street number for the structured address.
   */
  private String streetNumber;

  /**
   * The street name for the structured address.
   */
  private String streetName;

  /**
   * The cross street name for the structured address.
   */
  private String crossStreet;

  /**
   * The municipality (city/town) for the structured address.
   */
  private String municipality;

  /**
   * The municipality subdivision (sub/super city) for the structured address.
   */
  private String municipalitySubdivision;

  /**
   * The named area for the structured address.
   */
  private String countryTertiarySubdivision;

  /**
   * The county for the structured address.
   */
  private String countrySecondarySubdivision;

  /**
   * The state or province for the structured address.
   */
  private String countrySubdivision;

  /**
   * The zip code or postal code for the structured address.
   */
  private String postalCode;

  /**
   * Instantiates a new Structured geocode request.
   *
   * @param limit the limit
   * @param ofs the ofs
   * @param language the language
   * @param countryCode the country code
   * @param streetNumber the street number
   * @param streetName the street name
   * @param crossStreet the cross street
   * @param municipality the municipality
   * @param municipalitySubdivision the municipality subdivision
   * @param countryTertiarySubdivision the country tertiary subdivision
   * @param countrySecondarySubdivision the country secondary subdivision
   * @param countrySubdivision the country subdivision
   * @param postalCode the postal code
   */
  @Builder
  public StructuredGeocodeRequest(
      Integer limit,
      Integer ofs,
      Language language,
      String countryCode,
      String streetNumber,
      String streetName,
      String crossStreet,
      String municipality,
      String municipalitySubdivision,
      String countryTertiarySubdivision,
      String countrySecondarySubdivision,
      String countrySubdivision,
      String postalCode) {
    super(limit, ofs, language);
    this.countryCode = countryCode;
    this.streetNumber = streetNumber;
    this.streetName = streetName;
    this.crossStreet = crossStreet;
    this.municipality = municipality;
    this.municipalitySubdivision = municipalitySubdivision;
    this.countryTertiarySubdivision = countryTertiarySubdivision;
    this.countrySecondarySubdivision = countrySecondarySubdivision;
    this.countrySubdivision = countrySubdivision;
    this.postalCode = postalCode;
  }

  @Override
  public String getPath() {
    return "/structuredGeocode.json";
  }

  @Override
  protected MultiValueMap<String, String> buildRequestParameters(boolean urlEncode) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    if (StringUtils.hasText(countryCode)) {
      map.set("countryCode", urlEncode(countryCode, urlEncode));
    }
    if (StringUtils.hasText(streetNumber)) {
      map.set("streetNumber", urlEncode(streetNumber, urlEncode));
    }
    if (StringUtils.hasText(streetName)) {
      map.set("streetName", urlEncode(streetName, urlEncode));
    }
    if (StringUtils.hasText(crossStreet)) {
      map.set("crossStreet", urlEncode(crossStreet, urlEncode));
    }
    if (StringUtils.hasText(municipality)) {
      map.set("municipality", urlEncode(municipality, urlEncode));
    }
    if (StringUtils.hasText(municipalitySubdivision)) {
      map.set("municipalitySubdivision", urlEncode(municipalitySubdivision, urlEncode));
    }
    if (StringUtils.hasText(countryTertiarySubdivision)) {
      map.set("countryTertiarySubdivision", urlEncode(countryTertiarySubdivision, urlEncode));
    }
    if (StringUtils.hasText(countrySecondarySubdivision)) {
      map.set("countrySecondarySubdivision", urlEncode(countrySecondarySubdivision, urlEncode));
    }
    if (StringUtils.hasText(countrySubdivision)) {
      map.set("countrySubdivision", urlEncode(countrySubdivision, urlEncode));
    }
    if (StringUtils.hasText(postalCode)) {
      map.set("postalCode", urlEncode(postalCode, urlEncode));
    }
    return map;
  }

}
