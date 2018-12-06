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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.plain.model.UnknownAware;

/**
 * The route section.
 *
 * <p>Sections contain additional information about parts of a route. Each {@code section}
 * contains at least the elements {@code startPointIndex}, {@code endPointIndex} {@code
 * sectionType}.
 *
 * <p>See
 * <a href="https://developer.tomtom.com/routing-api/routing-api-documentation-routing/calculate-route#responseSection">
 * Structure of route sections</a>
 *
 * @author Christian Bremer
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("WeakerAccess")
public class RouteSection extends UnknownAware {

  private Integer startPointIndex;

  private Integer endPointIndex;

  /**
   * Contains the response section type.
   */
  private String sectionType;

  /**
   * This attribute is either set to the value given to the request parameter travelMode, if this
   * travel mode is possible, or to other which indicates that the given mode of transport is not
   * possible in this section. This field can only be used within sections of type TRAVEL_MODE.
   */
  private String travelMode;

  public enum SectionType {
    UNKNOWN,
    CAR_TRAIN,
    COUNTRY,
    FERRY,
    MOTORWAY,
    PEDESTRIAN,
    TOLL_ROAD,
    TOLL_VIGNETTE,
    TRAFFIC,
    TRAVEL_MODE,
    TUNNEL;

    public static SectionType fromValue(String value) {
      for (SectionType sectionType : SectionType.values()) {
        if (sectionType.name().equalsIgnoreCase(value)) {
          return sectionType;
        }
      }
      return UNKNOWN;
    }
  }

}
