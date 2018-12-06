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

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.plain.model.UnknownAware;

/**
 * The reverse geocode response.
 *
 * @author Christian Bremer
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ReverseGeocodeResponse extends UnknownAware {

  private Summary summary;

  private List<AddressWithPositionString> addresses;

  /**
   * Has addresses?
   *
   * @return the boolean
   */
  public boolean hasAddresses() {
    return addresses != null && !addresses.isEmpty();
  }

}
