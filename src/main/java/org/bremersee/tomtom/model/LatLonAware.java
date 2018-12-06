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

/**
 * The interface lat lon aware.
 *
 * @author Christian Bremer
 */
public interface LatLonAware {

  /**
   * Gets latitude.
   *
   * @return the latitude
   */
  BigDecimal getLatitude();

  /**
   * Gets longitude.
   *
   * @return the longitude
   */
  BigDecimal getLongitude();

  /**
   * Is latitude and longitude not {@code null}?
   *
   * @return {@code true} if latitude and longitude is not {@code null}, otherwise {@code false}
   */
  boolean hasValues();

  /**
   * Builder builder.
   *
   * @return the builder
   */
  static Builder builder() {
    return new BuilderImpl();
  }

  /**
   * The interface Builder.
   */
  @SuppressWarnings("unused")
  interface Builder {

    /**
     * Latitude builder.
     *
     * @param latitude the latitude
     * @return the builder
     */
    Builder latitude(BigDecimal latitude);

    /**
     * Latitude builder.
     *
     * @param latitude the latitude
     * @return the builder
     */
    Builder latitude(double latitude);

    /**
     * Longitude builder.
     *
     * @param longitude the longitude
     * @return the builder
     */
    Builder longitude(BigDecimal longitude);

    /**
     * Longitude builder.
     *
     * @param longitude the longitude
     * @return the builder
     */
    Builder longitude(double longitude);

    /**
     * Build lat lon aware.
     *
     * @return the lat lon aware
     */
    LatLonAware build();
  }

  /**
   * The builder implementation.
   */
  class BuilderImpl implements Builder {

    private BigDecimal latitude;

    private BigDecimal longitude;

    @Override
    public Builder latitude(BigDecimal latitude) {
      this.latitude = latitude;
      return this;
    }

    @Override
    public Builder latitude(double latitude) {
      this.latitude = BigDecimal.valueOf(latitude);
      return this;
    }

    @Override
    public Builder longitude(BigDecimal longitude) {
      this.longitude = longitude;
      return this;
    }

    @Override
    public Builder longitude(double longitude) {
      this.longitude = BigDecimal.valueOf(longitude);
      return this;
    }

    @Override
    public LatLonAware build() {
      return new LatLon(latitude, longitude);
    }
  }

}
