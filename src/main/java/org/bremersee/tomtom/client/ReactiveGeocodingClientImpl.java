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
import org.bremersee.tomtom.model.AbstractGeocodeRequest;
import org.bremersee.tomtom.model.AbstractReverseGeocodeRequest;
import org.bremersee.tomtom.model.GeocodeResponse;
import org.bremersee.tomtom.model.ReverseGeocodeResponse;
import org.bremersee.web.ErrorDetectors;
import org.bremersee.web.reactive.function.client.WebClientErrorDecoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

/**
 * @author Christian Bremer
 */
public class ReactiveGeocodingClientImpl extends AbstractReactiveClient
    implements ReactiveGeocodingClient {

  public ReactiveGeocodingClientImpl(
      TomTomProperties properties,
      Builder webClientBuilder,
      WebClientErrorDecoder<? extends Throwable> webClientErrorDecoder) {

    super(properties, webClientBuilder, webClientErrorDecoder);
  }

  @Override
  public Mono<GeocodeResponse> geocode(AbstractGeocodeRequest request) {
    final String baseUri = getProperties().getGeocodeUri() + request.getPath();
    final MultiValueMap<String, String> params = request.buildParameters(true);
    return getWebClientBuilder()
        .baseUrl(baseUri)
        .build()
        .get()
        .uri(uriBuilder -> uriBuilder.queryParams(params).build())
        .header("User-Agent", getProperties().getUserAgent())
        .retrieve()
        .onStatus(ErrorDetectors.DEFAULT, getWebClientErrorDecoder())
        .bodyToMono(GeocodeResponse.class);
  }

  @Override
  public Mono<ReverseGeocodeResponse> reverseGeocode(AbstractReverseGeocodeRequest request) {
    final String baseUri = getProperties().getReverseGeocodeUri() + request.getPath();
    final MultiValueMap<String, String> params = request.buildParameters(true);
    return getWebClientBuilder()
        .baseUrl(baseUri)
        .build()
        .get()
        .uri(uriBuilder -> uriBuilder.queryParams(params).build())
        .header("User-Agent", getProperties().getUserAgent())
        .retrieve()
        .onStatus(ErrorDetectors.DEFAULT, getWebClientErrorDecoder())
        .bodyToMono(ReverseGeocodeResponse.class);
  }
}
