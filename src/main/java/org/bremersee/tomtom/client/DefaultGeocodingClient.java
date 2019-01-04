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
import java.net.URL;
import org.bremersee.tomtom.TomTomProperties;
import org.bremersee.tomtom.model.AbstractGeocodeRequest;
import org.bremersee.tomtom.model.AbstractReverseGeocodeRequest;
import org.bremersee.tomtom.model.GeocodeResponse;
import org.bremersee.tomtom.model.ReverseGeocodeResponse;
import org.springframework.util.MultiValueMap;

/**
 * The default geocoding client.
 *
 * @author Christian Bremer
 */
public class DefaultGeocodingClient extends AbstractDefaultClient
    implements TraditionalGeocodingClient {

  /**
   * Instantiates a new default geocoding client.
   *
   * @param properties   the properties
   * @param objectMapper the object mapper
   */
  @SuppressWarnings("WeakerAccess")
  public DefaultGeocodingClient(
      TomTomProperties properties,
      ObjectMapper objectMapper) {
    super(properties, objectMapper);
  }

  @Override
  public GeocodeResponse geocode(final AbstractGeocodeRequest request) {

    final String baseUri = getProperties().getGeocodeUri() + request.getPath();
    final MultiValueMap<String, String> params = request.buildParameters(true);
    final URL url = buildUrl(baseUri, params);
    return call(url, HttpMethod.GET, null, GeocodeResponse.class);
  }

  @Override
  public ReverseGeocodeResponse reverseGeocode(final AbstractReverseGeocodeRequest request) {

    final String baseUri = getProperties().getReverseGeocodeUri() + request.getPath();
    final MultiValueMap<String, String> params = request.buildParameters(true);
    final URL url = buildUrl(baseUri, params);
    return call(url, HttpMethod.GET, null, ReverseGeocodeResponse.class);
  }

}
