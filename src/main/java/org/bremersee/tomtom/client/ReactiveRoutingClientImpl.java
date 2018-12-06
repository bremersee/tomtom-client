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

import org.bremersee.tomtom.TomTomProperties;
import org.bremersee.tomtom.model.RoutingRequest;
import org.bremersee.tomtom.model.RoutingResponse;
import org.bremersee.web.ErrorDetectors;
import org.bremersee.web.reactive.function.client.WebClientErrorDecoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

/**
 * The reactive routing client implementation.
 *
 * @author Christian Bremer
 */
public class ReactiveRoutingClientImpl extends AbstractReactiveClient
    implements ReactiveRoutingClient {

  /**
   * Instantiates a new reactive routing client.
   *
   * @param properties the properties
   * @param webClientBuilder the web client builder
   * @param webClientErrorDecoder the web client error decoder
   */
  public ReactiveRoutingClientImpl(
      final TomTomProperties properties,
      final Builder webClientBuilder,
      final WebClientErrorDecoder<? extends Throwable> webClientErrorDecoder) {
    super(properties, webClientBuilder, webClientErrorDecoder);
  }

  @Override
  public Mono<RoutingResponse> calculateRoute(final RoutingRequest request) {
    final String baseUri = getProperties().getRoutingUri() + request.getPath();
    final MultiValueMap<String, String> params = request.buildParameters(true);
    params.set("key", getProperties().getKey());
    if (request.hasPostBody()) {
      return getWebClientBuilder()
          .baseUrl(baseUri)
          .build()
          .post()
          .uri(uriBuilder -> uriBuilder.queryParams(params).build())
          .header("User-Agent", getProperties().getUserAgent())
          .header("Content-Type", "application/json")
          .body(BodyInserters.fromObject(request.buildPostBody()))
          .retrieve()
          .onStatus(ErrorDetectors.DEFAULT, getWebClientErrorDecoder())
          .bodyToMono(RoutingResponse.class);
    } else {
      return getWebClientBuilder()
          .baseUrl(baseUri)
          .build()
          .get()
          .uri(uriBuilder -> uriBuilder.queryParams(params).build())
          .header("User-Agent", getProperties().getUserAgent())
          .retrieve()
          .onStatus(ErrorDetectors.DEFAULT, getWebClientErrorDecoder())
          .bodyToMono(RoutingResponse.class);
    }
  }
}
