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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bremersee.exception.ServiceException;
import org.bremersee.tomtom.TomTomProperties;
import org.bremersee.tomtom.exception.ErrorCodeConstants;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 * The abstract default client.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractDefaultClient extends AbstractClient {

  @Getter(AccessLevel.PROTECTED)
  @Setter(AccessLevel.PROTECTED)
  private ExceptionMessageParser exceptionMessageParser = ExceptionMessageParser.defaultParser();

  /**
   * Instantiates a new abstract default client.
   *
   * @param properties the properties
   * @param objectMapper the object mapper
   */
  protected AbstractDefaultClient(
      TomTomProperties properties,
      ObjectMapper objectMapper) {

    super(properties);
    super.setObjectMapper(objectMapper);
  }

  /**
   * Call the url.
   *
   * @param <T> the type of the response
   * @param url the url
   * @param httpMethod the http method
   * @param requestBody the request body
   * @param responseClass the response class
   * @return the response
   */
  protected <T> T call(
      final URL url,
      final HttpMethod httpMethod,
      final Object requestBody,
      final Class<T> responseClass) {

    HttpURLConnection con = null;
    try {
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod(String.valueOf(httpMethod));
      con.setDoInput(true);
      con.setRequestProperty("Accept", "*/*");
      if (!HttpMethod.GET.equals(httpMethod)) {
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
      }
      if (StringUtils.hasText(getProperties().getUserAgent())) {
        con.setRequestProperty("User-Agent", getProperties().getUserAgent());
      }
      con.connect();
      if (!HttpMethod.GET.equals(httpMethod)) {
        try (OutputStream out = con.getOutputStream()) {
          if (requestBody != null) {
            getObjectMapper().writeValue(out, requestBody);
          }
        } catch (IOException e) {
          throw new ServiceException(
              500, "Writing request body failed.", ErrorCodeConstants.GENERAL_REQUEST_ERROR, e);
        }
      }
      int statusCode = con.getResponseCode();
      if (statusCode >= 400) {
        try (InputStream errorStream = con.getErrorStream()) {
          final String body = new String(
              FileCopyUtils.copyToByteArray(errorStream), StandardCharsets.UTF_8);
          final String message = exceptionMessageParser.getExceptionMessage(body);
          throw new ServiceException(statusCode, message, ErrorCodeConstants.GENERAL_REQUEST_ERROR);

        } catch (IOException e) {
          throw new ServiceException(
              statusCode,
              "Reading error stream failed.",
              ErrorCodeConstants.GENERAL_REQUEST_ERROR,
              e);
        }
      }
      try (InputStream inputStream = con.getInputStream()) {
        return getObjectMapper().readValue(inputStream, responseClass);

      } catch (IOException e) {
        throw new ServiceException(
            500, "Reading input stream failed.", ErrorCodeConstants.GENERAL_REQUEST_ERROR, e);
      }
    } catch (IOException e) {
      throw new ServiceException(
          500, "Connecting to " + url + " failed.", ErrorCodeConstants.GENERAL_REQUEST_ERROR, e);
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
  }

  /**
   * The supported http methods.
   */
  protected enum HttpMethod {

    /**
     * Get http method.
     */
    GET,

    /**
     * Post http method.
     */
    POST;
  }

}
