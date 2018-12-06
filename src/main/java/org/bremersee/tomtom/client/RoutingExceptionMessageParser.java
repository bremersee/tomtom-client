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

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import org.bremersee.tomtom.model.RoutingResponse;
import org.springframework.util.StringUtils;

/**
 * Error parser that reads the error description from the routing response (see {@link
 * RoutingResponse#getError()}).
 *
 * @author Christian Bremer
 */
public class RoutingExceptionMessageParser implements ExceptionMessageParser {

  private static final String PATH = "$.error.description";

  private static final com.jayway.jsonpath.Configuration jsonPathConf
      = com.jayway.jsonpath.Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();

  @Override
  public String getExceptionMessage(String errorBody) {
    try {
      final DocumentContext ctx = JsonPath.parse(errorBody, jsonPathConf);
      final String message = ctx.read(PATH, String.class);
      if (StringUtils.hasText(message)) {
        return message;
      }
    } catch (Exception ignored) {
      // ignored
    }
    return errorBody;
  }
}
