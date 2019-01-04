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
import org.bremersee.tomtom.model.RoutingRequest;
import org.bremersee.tomtom.model.RoutingResponse;
import org.springframework.util.MultiValueMap;

/**
 * The default routing client.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("WeakerAccess")
public class DefaultRoutingClient extends AbstractDefaultClient
    implements TraditionalRoutingClient {

  /**
   * Instantiates a new default routing client.
   *
   * @param properties   the properties
   * @param objectMapper the object mapper (can be {@code null})
   */
  public DefaultRoutingClient(
      final TomTomProperties properties,
      final ObjectMapper objectMapper) {
    super(properties, objectMapper);
    setExceptionMessageParser(new RoutingExceptionMessageParser());
  }

  @Override
  public RoutingResponse calculateRoute(final RoutingRequest request) {
    final String baseUri = getProperties().getRoutingUri() + request.getPath();
    final MultiValueMap<String, String> params = request.buildParameters(true);
    final URL url = buildUrl(baseUri, params);
    if (request.hasPostBody()) {
      return call(url, HttpMethod.POST, request.buildPostBody(), RoutingResponse.class);
    }
    return call(url, HttpMethod.GET, null, RoutingResponse.class);
  }

}
