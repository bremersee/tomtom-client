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

package org.bremersee.tomtom.model;

import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * The abstract geocode request.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("WeakerAccess")
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractGeocodeRequest extends AbstractRequest {

  /**
   * Maximum number of search results that will be returned.
   */
  private Integer limit;

  /**
   * Starting offset of the returned results within the full result set.
   */
  private Integer ofs;

  /**
   * Language in which search results should be returned.
   */
  private Language language;

  /**
   * Instantiates a new abstract geocode request.
   *
   * @param limit the limit
   * @param ofs the ofs
   * @param language the language
   */
  protected AbstractGeocodeRequest(
      Integer limit,
      Integer ofs,
      Language language) {

    this.limit = limit;
    this.ofs = ofs;
    this.language = language;
  }

  /**
   * Gets path.
   *
   * @return the path
   */
  @NotNull
  public abstract String getPath();

  /**
   * Build the request parameter map.
   *
   * @param urlEncode if {@code true}, the parameter values will be encoded, otherwise not.
   * @return the request parameter map
   */
  public final MultiValueMap<String, String> buildParameters(final boolean urlEncode) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    if (limit != null) {
      map.set("limit", limit.toString());
    }
    if (ofs != null) {
      map.set("ofs", ofs.toString());
    }
    if (language != null) {
      map.set("language", urlEncode(language.getValue(), urlEncode));
    }
    map.putAll(buildRequestParameters(urlEncode));
    return map;
  }

  /**
   * Build request parameters of sub classes.
   *
   * @param urlEncode the url encode
   * @return the multi value map
   */
  protected abstract MultiValueMap<String, String> buildRequestParameters(boolean urlEncode);


}
