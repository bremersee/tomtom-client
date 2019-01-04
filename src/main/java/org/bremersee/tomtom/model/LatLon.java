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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bremersee.plain.model.UnknownAware;

/**
 * The lat lon.
 *
 * @author Christian Bremer
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LatLon extends UnknownAware implements LatLonAware {

  private BigDecimal lat;

  private BigDecimal lon;

  /**
   * Instantiates a new lat lon.
   *
   * @param latLonAware the lat lon aware
   */
  @SuppressWarnings("unused")
  public LatLon(LatLonAware latLonAware) {
    if (latLonAware != null) {
      this.lat = latLonAware.getLatitude();
      this.lon = latLonAware.getLongitude();
    }
  }

  @JsonIgnore
  @Override
  public BigDecimal getLatitude() {
    return lat;
  }

  @JsonIgnore
  @Override
  public BigDecimal getLongitude() {
    return lon;
  }

  @Override
  public boolean hasValues() {
    return lat != null && lon != null;
  }

}
