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

import org.bremersee.tomtom.model.RoutingRequest;

/**
 * General TomTom routing client interface.
 *
 * <p>See
 * <a href="https://developer.tomtom.com/routing-api/routing-api-documentation-routing/calculate-route">
 * Routing API</a>
 *
 * @param <R> the routing response type, e. g. {@literal RoutingResponse} or {@literal
 *            Mono<RoutingResponse>}
 * @author Christian Bremer
 */
public interface RoutingClient<R> {

  /**
   * Calculate the route.
   *
   * @param request the request
   * @return the response
   */
  R calculateRoute(RoutingRequest request);

}
