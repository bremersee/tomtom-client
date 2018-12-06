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
import org.bremersee.web.reactive.function.client.DefaultWebClientErrorDecoder;
import org.bremersee.web.reactive.function.client.WebClientErrorDecoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

/**
 * The abstract reactive client.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractReactiveClient extends AbstractClient {

  private WebClient.Builder webClientBuilder;

  private WebClientErrorDecoder<? extends Throwable> webClientErrorDecoder;

  /**
   * Instantiates a new abstract reactive client.
   *
   * @param properties the properties
   * @param webClientBuilder the web client builder
   * @param webClientErrorDecoder the web client error decoder
   */
  protected AbstractReactiveClient(
      TomTomProperties properties,
      WebClient.Builder webClientBuilder,
      WebClientErrorDecoder<? extends Throwable> webClientErrorDecoder) {

    super(properties);
    this.webClientBuilder = webClientBuilder != null
        ? webClientBuilder.clone()
        : null;
    this.webClientErrorDecoder = webClientErrorDecoder;
  }

  /**
   * Gets web client builder.
   *
   * @return the web client builder
   */
  protected Builder getWebClientBuilder() {
    if (webClientBuilder == null) {
      this.webClientBuilder = WebClient
          .builder()
          .exchangeStrategies(
              ExchangeStrategies
                  .builder()
                  .codecs((clientCodecConfigurer -> clientCodecConfigurer
                      .defaultCodecs()
                      .jackson2JsonDecoder(new Jackson2JsonDecoder(getObjectMapper()))))
                  .codecs((clientCodecConfigurer -> clientCodecConfigurer
                      .defaultCodecs()
                      .jackson2JsonEncoder(new Jackson2JsonEncoder(getObjectMapper()))))
                  .build());
    }
    return webClientBuilder;
  }

  /**
   * Gets web client error decoder.
   *
   * @return the web client error decoder
   */
  protected WebClientErrorDecoder<? extends Throwable> getWebClientErrorDecoder() {
    if (webClientErrorDecoder == null) {
      webClientErrorDecoder = new DefaultWebClientErrorDecoder();
    }
    return webClientErrorDecoder;
  }

}
