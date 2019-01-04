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

import org.bremersee.tomtom.model.AbstractGeocodeRequest;
import org.bremersee.tomtom.model.AbstractReverseGeocodeRequest;

/**
 * General TomTom geocoding client interface.
 *
 * @param <S> the geocode response return type, e. g. {@literal GeocodeResponse} or {@literal
 *            Mono<GeocodeResponse>}
 * @param <R> the reverse search response type, e. g. {@literal ReverseGeocodeResponse} or {@literal
 *            Mono<ReverseGeocodeResponse>}
 * @author Christian Bremer
 */
public interface GeocodingClient<S, R> {

  /**
   * Makes a geocode search request.
   *
   * @param request the request
   * @return the response
   */
  S geocode(AbstractGeocodeRequest request);

  /**
   * Makes a reverse geocode search request.
   *
   * @param request the request
   * @return the response
   */
  R reverseGeocode(AbstractReverseGeocodeRequest request);

}
