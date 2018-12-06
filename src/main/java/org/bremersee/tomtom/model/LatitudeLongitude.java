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

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bremersee.plain.model.UnknownAware;

/**
 * The latitude longitude.
 *
 * @author Christian Bremer
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LatitudeLongitude extends UnknownAware implements LatLonAware {

  private BigDecimal latitude;

  private BigDecimal longitude;

  /**
   * Instantiates a new latitude longitude.
   *
   * @param latLonAware the lat lon aware
   */
  public LatitudeLongitude(LatLonAware latLonAware) {
    if (latLonAware != null) {
      this.latitude = latLonAware.getLatitude();
      this.longitude = latLonAware.getLongitude();
    }
  }

  @Override
  public boolean hasValues() {
    return latitude != null && longitude != null;
  }

}
