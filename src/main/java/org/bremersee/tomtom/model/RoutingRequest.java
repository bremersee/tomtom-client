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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bremersee.tomtom.model.RoutingRequest.PostBody.AvoidAreas;
import org.springframework.util.StringUtils;

/**
 * @author Christian Bremer
 */
public class RoutingRequest {

  /**
   * Locations through which the calculated route must pass.
   */
  private List<LatLonAware> locations;

  private List<LatitudeLongitude> supportingPoints;

  //private String contentType = "json";

  /**
   * Number of alternative routes to be calculated.
   */
  private Integer maxAlternatives;

  /**
   * Determines whether the alternative routes to be calculated should be better with respect to the
   * planning criteria provided than the reference route.
   */
  private AlternativeType alternativeType;

  /**
   * All alternative routes will follow the reference route for the specified minimum number of
   * meters starting from the origin point.
   */
  private Integer minDeviationDistance;

  /**
   * All alternative routes will follow the reference route for the specified minimum number of
   * seconds starting from the origin point.
   */
  private Integer minDeviationTime;

  /**
   * If specified, guidance instructions will be returned (if available).
   */
  private InstructionsType instructionsType;

  /**
   * The language parameter determines the language of the guidance messages.
   */
  private Locale language;

  /**
   * Re-order the route waypoints to reduce the route length.
   */
  private Boolean computeBestOrder;

  /**
   * Specifies the representation of the set of routes provided as a response.
   */
  private RouteRepresentation routeRepresentation = RouteRepresentation.POLYLINE;

  /**
   * Specifies whether to return additional travel times using different types of traffic
   * information (none, historic, live) as well as the default best-estimate travel time.
   */
  private TravelTime computeTravelTimeFor;

  /**
   * The directional heading of the vehicle in degrees. Entered in degrees, measured clockwise from
   * north (so north is 0, east is 90, etc.).
   */
  private Integer vehicleHeading;

  /**
   * Specifies which section types are explicitly reported in the route response. Can be specified
   * multiple times.
   * <ul>
   * <li>carTrain, ferry, tunnel or motorway
   * <li>pedestrian
   * <li>tollRoad
   * <li>tollVignette
   * <li>country
   * <li>travelMode
   * <li>traffic
   */
  private List<String> sectionType;

  //private String callback;

  /**
   * Specifies which data should be reported for diagnosis purposes.
   */
  private Report report;

  /**
   * The date and time of departure from the origin point. Departure times apart from now must be
   * specified as a dateTime.
   */
  private OffsetDateTime departAt;

  /**
   * The date and time of arrival at the destination point. It must be specified as a dateTime.
   */
  private OffsetDateTime arriveAt;

  /**
   * The type of route requested.
   */
  private RouteType routeType;

  /**
   * Determines whether current traffic is used in route calculations. Note that information on
   * historic road speeds is always used.
   */
  private Boolean traffic;

  /**
   * Specifies whether the routing engine should try to avoid specific types of road segment when
   * calculating the route. Can be specified multiple times.
   */
  private List<Avoid> avoid;

  /**
   * A list of shapes to avoid for planning routes. Supported shapes include {@code rectangles}. Can
   * contain one of each supported shapes element.
   */
  private List<Rectangle> avoidAreas; // POST

  /**
   * List of 3-character ISO 3166-1 alpha-3 country codes of countries in which all toll roads with
   * vignettes are to be avoided. Toll roads with vignettes in countries not in the list are
   * unaffected. It is an error to specify both avoidVignette and allowVignette.
   */
  private List<Locale> avoidVignette; // POST

  /**
   * List of 3-character ISO 3166-1 alpha-3 country codes of countries in which toll roads with
   * vignettes are allowed. Specifying allowVignette with some countries X is equivalent to
   * specifying avoidVignette with all countries but X. Specifying allowVignette with an empty list
   * is the same as avoiding all toll roads with vignettes. It is an error to specify both
   * avoidVignette and allowVignette.
   */
  private List<Locale> allowVignette; // POST

  /**
   * The mode of travel for the requested route.
   */
  private TravelMode travelMode;

  /**
   * Degree of hilliness for calculating a thrilling route.
   */
  private Hilliness hilliness;

  /**
   * Amount that a thrilling route should wind.
   */
  private Windingness windingness;

  /**
   * Maximum speed of the vehicle in km/hour.
   */
  private Integer vehicleMaxSpeed;

  /**
   * Weight of the vehicle in kilograms.
   */
  private Integer vehicleWeight;

  /**
   * Weight per axle of the vehicle in kg.
   */
  private Integer vehicleAxleWeight;

  /**
   * Length of the vehicle in meters.
   */
  private Float vehicleLength;

  /**
   * Width of the vehicle in meters.
   */
  private Float vehicleWidth;

  /**
   * Height of the vehicle in meters.
   */
  private Float vehicleHeight;

  /**
   * Indicates that the vehicle is used for commercial purposes. This means it may not be allowed on
   * certain roads.
   */
  private Boolean vehicleCommercial;

  /**
   * Indicates what kinds of hazardous materials the vehicle is carrying (if any). This means it may
   * not be allowed on certain roads. This parameter is currently only considered for
   * travelMode=truck.
   */
  private List<VehicleLoadType> vehicleLoadType;

  public boolean hasPostBody() {
    return (supportingPoints != null && !supportingPoints.isEmpty())
        || (avoidAreas != null && !avoidAreas.isEmpty())
        || !buildISO3Countries(avoidVignette).isEmpty()
        || !buildISO3Countries(allowVignette).isEmpty();
  }

  public PostBody buildPostBody() {
    final PostBody postBody = new PostBody();
    postBody.setSupportingPoints(supportingPoints);
    postBody.setAvoidVignette(buildISO3Countries(avoidVignette));
    postBody.setAllowVignette(buildISO3Countries(allowVignette));
    if (avoidAreas != null && !avoidAreas.isEmpty()) {
      postBody.setAvoidAreas(new AvoidAreas(avoidAreas));
    }
    return postBody;
  }

  private List<String> buildISO3Countries(final List<Locale> locales) {
    final List<String> countries = new ArrayList<>();
    if (locales != null && !locales.isEmpty()) {
      for (final Locale locale : locales) {
        if (locale != null && StringUtils.hasText(locale.getISO3Country())) {
          countries.add(locale.getISO3Country());
        }
      }
    }
    return countries;
  }

  @JsonInclude(Include.NON_EMPTY)
  @Data
  public static class PostBody {

    private List<LatitudeLongitude> supportingPoints;

    private List<String> avoidVignette;

    private List<String> allowVignette;

    private AvoidAreas avoidAreas;

    @JsonInclude(Include.NON_EMPTY)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvoidAreas {

      private List<Rectangle> rectangles;
    }
  }

  public enum AlternativeType {
    ANY_ROUTE("anyRoute"), BETTER_ROUTE("betterRoute");

    @Getter
    private String value;

    AlternativeType(String value) {
      this.value = value;
    }

    public static AlternativeType fromValue(String value) {
      for (AlternativeType alternativeType : AlternativeType.values()) {
        if (alternativeType.getValue().equalsIgnoreCase(value)) {
          return alternativeType;
        }
      }
      return null;
    }
  }

  public enum InstructionsType {
    CODED("coded"), TEXT("text"), TAGGED("tagged");

    @Getter
    private String value;

    InstructionsType(String value) {
      this.value = value;
    }

    public static InstructionsType fromValue(String value) {
      for (InstructionsType instructionsType : InstructionsType.values()) {
        if (instructionsType.getValue().equalsIgnoreCase(value)) {
          return instructionsType;
        }
      }
      return null;
    }
  }

  public enum RouteRepresentation {
    POLYLINE("polyline"), NONE("none");

    @Getter
    private String value;

    RouteRepresentation(String value) {
      this.value = value;
    }

    public static RouteRepresentation fromValue(String value) {
      for (RouteRepresentation routeRepresentation : RouteRepresentation.values()) {
        if (routeRepresentation.getValue().equalsIgnoreCase(value)) {
          return routeRepresentation;
        }
      }
      return null;
    }
  }

  public enum TravelTime {
    ALL("all"), NONE("none");

    @Getter
    private String value;

    TravelTime(String value) {
      this.value = value;
    }

    public static TravelTime fromValue(String value) {
      for (TravelTime travelTime : TravelTime.values()) {
        if (travelTime.getValue().equalsIgnoreCase(value)) {
          return travelTime;
        }
      }
      return null;
    }
  }

  public enum Report {
    EFFECTIVE_SETTINGS("effectiveSettings");

    @Getter
    private String value;

    Report(String value) {
      this.value = value;
    }

    public static Report fromValue(String value) {
      for (Report report : Report.values()) {
        if (report.getValue().equalsIgnoreCase(value)) {
          return report;
        }
      }
      return null;
    }
  }

  public enum RouteType {
    FASTEST("fastest"), SHORTEST("shortest"), ECO("eco"), THRILLING("thrilling");

    @Getter
    private String value;

    RouteType(String value) {
      this.value = value;
    }

    public static RouteType fromValue(String value) {
      for (RouteType routeType : RouteType.values()) {
        if (routeType.getValue().equalsIgnoreCase(value)) {
          return routeType;
        }
      }
      return null;
    }
  }

  public enum Avoid {

    TOLL_ROADS("tollRoads"),

    MOTORWAYS("motorways"),

    FERRIES("ferries"),

    UNPAVED_ROADS("unpavedRoads"),

    CARPOOLS("carpools"),

    ALREADY_USED_ROADS("alreadyUsedRoads");

    @Getter
    private String value;

    Avoid(String value) {
      this.value = value;
    }

    public static Avoid fromValue(String value) {
      for (Avoid avoid : Avoid.values()) {
        if (avoid.getValue().equalsIgnoreCase(value)) {
          return avoid;
        }
      }
      return null;
    }
  }

  public enum TravelMode {

    CAR("car"),

    TRUCK("truck"),

    TAXI("taxi"),

    BUS("bus"),

    VAN("van"),

    MOTORCYCLE("alreadyUsedRoads"),

    BICYCLE("bicycle"),

    PEDESTRIAN("pedestrian");

    @Getter
    private String value;

    TravelMode(String value) {
      this.value = value;
    }

    public static TravelMode fromValue(String value) {
      for (TravelMode travelMode : TravelMode.values()) {
        if (travelMode.getValue().equalsIgnoreCase(value)) {
          return travelMode;
        }
      }
      return null;
    }
  }

  public enum Hilliness {

    LOW("low"),

    NORMAL("normal"),

    HIGH("high");

    @Getter
    private String value;

    Hilliness(String value) {
      this.value = value;
    }

    public static Hilliness fromValue(String value) {
      for (Hilliness hilliness : Hilliness.values()) {
        if (hilliness.getValue().equalsIgnoreCase(value)) {
          return hilliness;
        }
      }
      return null;
    }
  }

  public enum Windingness {

    LOW("low"),

    NORMAL("normal"),

    HIGH("high");

    @Getter
    private String value;

    Windingness(String value) {
      this.value = value;
    }

    public static Windingness fromValue(String value) {
      for (Windingness windingness : Windingness.values()) {
        if (windingness.getValue().equalsIgnoreCase(value)) {
          return windingness;
        }
      }
      return null;
    }
  }

  public enum VehicleLoadType {

    US_HAZMAT_CLASS_1("USHazmatClass1", "Explosives (US)"),

    US_HAZMAT_CLASS_2("USHazmatClass2", "Compressed gas"),

    US_HAZMAT_CLASS_3("USHazmatClass3", "Flammable liquids"),

    US_HAZMAT_CLASS_4("USHazmatClass4", "Flammable solids"),

    US_HAZMAT_CLASS_5("USHazmatClass5", "Oxidizers"),

    US_HAZMAT_CLASS_6("USHazmatClass6", "Poisons"),

    US_HAZMAT_CLASS_7("USHazmatClass7", "Radioactive"),

    US_HAZMAT_CLASS_8("USHazmatClass8", "Corrosives"),

    US_HAZMAT_CLASS_9("USHazmatClass9", "Miscellaneous"),

    OTHER_HAZMAT_EXPLOSIVE("otherHazmatExplosive", "Explosives"),

    OTHER_HAZMAT_GENERAL("otherHazmatGeneral", "Miscellaneous"),

    OTHER_HAZMAT_HARMFUL_TO_WATER("otherHazmatHarmfulToWater", "Harmful to water");

    @Getter
    private String value;

    @Getter
    private String description;

    VehicleLoadType(String value, String description) {
      this.value = value;
      this.description = description;
    }

    public static VehicleLoadType fromValue(String value) {
      for (VehicleLoadType vehicleLoadType : VehicleLoadType.values()) {
        if (vehicleLoadType.getValue().equalsIgnoreCase(value)) {
          return vehicleLoadType;
        }
      }
      return null;
    }
  }

}
