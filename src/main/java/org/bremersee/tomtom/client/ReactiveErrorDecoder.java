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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.bremersee.exception.model.RestApiException;
import org.bremersee.tomtom.exception.ErrorCodeConstants;
import org.bremersee.web.reactive.function.client.AbstractWebClientErrorDecoder;
import org.bremersee.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * The reactive error decoder.
 *
 * @author Christian Bremer
 */
public class ReactiveErrorDecoder extends AbstractWebClientErrorDecoder<WebClientException> {

  private final ExceptionMessageParser parser;

  public ReactiveErrorDecoder() {
    this(null);
  }

  public ReactiveErrorDecoder(ExceptionMessageParser parser) {
    this.parser = parser != null ? parser : ExceptionMessageParser.defaultParser();
  }

  @Override
  public WebClientException buildException(
      final ClientResponse clientResponse,
      final String response) {

    final Map<String, ? extends Collection<String>> headers = Collections
        .unmodifiableMap(clientResponse.headers().asHttpHeaders());
    final RestApiException restApiException = new RestApiException();
    restApiException.setMessage(parser.getExceptionMessage(response));
    restApiException.setErrorCode(ErrorCodeConstants.GENERAL_REQUEST_ERROR);
    return new WebClientException(clientResponse.statusCode(), headers, restApiException);
  }
}
