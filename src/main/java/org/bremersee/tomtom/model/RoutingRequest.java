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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bremersee.tomtom.model.RoutingRequest.PostBody.AvoidAreas;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * The routing request.
 *
 * @author Christian Bremer
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class RoutingRequest extends AbstractRequest {

  /**
   * Locations through which the calculated route must pass.
   */
  private List<LatLonAware> locations;

  private List<LatitudeLongitude> supportingPoints;

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
  private RouteRepresentation routeRepresentation;

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

  /**
   * Specifies which data should be reported for diagnosis purposes.
   */
  private Report report;

  /**
   * The date and time of departure from the origin point. Departure times apart from now must be
   * specified as a ISO8601 format (yyyy-mm-ddThh:mm:ssZ).
   */
  private Date departAt;

  /**
   * The date and time of arrival at the destination point. It must be specified as a ISO8601 format
   * (yyyy-mm-ddThh:mm:ssZ).
   */
  private Date arriveAt;

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
  private BigDecimal vehicleLength;

  /**
   * Width of the vehicle in meters.
   */
  private BigDecimal vehicleWidth;

  /**
   * Height of the vehicle in meters.
   */
  private BigDecimal vehicleHeight;

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

  /**
   * Instantiates a new routing request.
   *
   * @param locations            the locations
   * @param supportingPoints     the supporting points
   * @param maxAlternatives      the max alternatives
   * @param alternativeType      the alternative type
   * @param minDeviationDistance the min deviation distance
   * @param minDeviationTime     the min deviation time
   * @param instructionsType     the instructions type
   * @param language             the language
   * @param computeBestOrder     the compute best order
   * @param routeRepresentation  the route representation
   * @param computeTravelTimeFor the compute travel time for
   * @param vehicleHeading       the vehicle heading
   * @param sectionType          the section type
   * @param report               the report
   * @param departAt             the depart at
   * @param arriveAt             the arrive at
   * @param routeType            the route type
   * @param traffic              the traffic
   * @param avoid                the avoid
   * @param avoidAreas           the avoid areas
   * @param avoidVignette        the avoid vignette
   * @param allowVignette        the allow vignette
   * @param travelMode           the travel mode
   * @param hilliness            the hilliness
   * @param windingness          the windingness
   * @param vehicleMaxSpeed      the vehicle max speed
   * @param vehicleWeight        the vehicle weight
   * @param vehicleAxleWeight    the vehicle axle weight
   * @param vehicleLength        the vehicle length
   * @param vehicleWidth         the vehicle width
   * @param vehicleHeight        the vehicle height
   * @param vehicleCommercial    the vehicle commercial
   * @param vehicleLoadType      the vehicle load type
   */
  @Builder
  public RoutingRequest(
      List<LatLonAware> locations,
      List<LatitudeLongitude> supportingPoints,
      Integer maxAlternatives,
      AlternativeType alternativeType,
      Integer minDeviationDistance,
      Integer minDeviationTime,
      InstructionsType instructionsType,
      Locale language,
      Boolean computeBestOrder,
      RouteRepresentation routeRepresentation,
      TravelTime computeTravelTimeFor,
      Integer vehicleHeading,
      List<String> sectionType,
      Report report,
      Date departAt,
      Date arriveAt,
      RouteType routeType,
      Boolean traffic,
      List<Avoid> avoid,
      List<Rectangle> avoidAreas,
      List<Locale> avoidVignette,
      List<Locale> allowVignette,
      TravelMode travelMode,
      Hilliness hilliness,
      Windingness windingness,
      Integer vehicleMaxSpeed,
      Integer vehicleWeight,
      Integer vehicleAxleWeight,
      BigDecimal vehicleLength,
      BigDecimal vehicleWidth,
      BigDecimal vehicleHeight,
      Boolean vehicleCommercial,
      List<VehicleLoadType> vehicleLoadType) {

    this.locations = locations;
    this.supportingPoints = supportingPoints;
    this.maxAlternatives = maxAlternatives;
    this.alternativeType = alternativeType;
    this.minDeviationDistance = minDeviationDistance;
    this.minDeviationTime = minDeviationTime;
    this.instructionsType = instructionsType;
    this.language = language;
    this.computeBestOrder = computeBestOrder;
    this.routeRepresentation = routeRepresentation != null
        ? routeRepresentation
        : RouteRepresentation.POLYLINE;
    this.computeTravelTimeFor = computeTravelTimeFor;
    this.vehicleHeading = vehicleHeading;
    this.sectionType = sectionType;
    this.report = report;
    this.departAt = departAt;
    this.arriveAt = arriveAt;
    this.routeType = routeType;
    this.traffic = traffic;
    this.avoid = avoid;
    this.avoidAreas = avoidAreas;
    this.avoidVignette = avoidVignette;
    this.allowVignette = allowVignette;
    this.travelMode = travelMode;
    this.hilliness = hilliness;
    this.windingness = windingness;
    this.vehicleMaxSpeed = vehicleMaxSpeed;
    this.vehicleWeight = vehicleWeight;
    this.vehicleAxleWeight = vehicleAxleWeight;
    this.vehicleLength = vehicleLength;
    this.vehicleWidth = vehicleWidth;
    this.vehicleHeight = vehicleHeight;
    this.vehicleCommercial = vehicleCommercial;
    this.vehicleLoadType = vehicleLoadType;
  }

  /**
   * Gets path.
   *
   * @return the path
   */
  public String getPath() {
    return "/" + buildLocations() + "/json";
  }

  private String buildLocations() {
    final StringBuilder locationsBuilder = new StringBuilder();
    if (locations != null) {
      for (final LatLonAware latLon : locations) {
        if (latLon.hasValues()) {
          if (locationsBuilder.length() > 0) {
            locationsBuilder.append(':');
          }
          locationsBuilder
              .append(latLon.getLatitude().toPlainString())
              .append(',')
              .append(latLon.getLongitude().toPlainString());
        }
      }
    }
    return locationsBuilder.toString();
  }

  /**
   * Build url parameters.
   *
   * @param urlEncode the url encode
   * @return the url parameters
   */
  public MultiValueMap<String, String> buildParameters(final boolean urlEncode) {
    final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    if (maxAlternatives != null) {
      map.set("maxAlternatives", maxAlternatives.toString());
    }
    if (alternativeType != null) {
      map.set("alternativeType", urlEncode(alternativeType.getValue(), urlEncode));
    }
    if (minDeviationDistance != null) {
      map.set("minDeviationDistance", minDeviationDistance.toString());
    }
    if (minDeviationTime != null) {
      map.set("minDeviationTime", minDeviationTime.toString());
    }
    if (instructionsType != null) {
      map.set("instructionsType", urlEncode(instructionsType.getValue(), urlEncode));
    }
    if (language != null && StringUtils.hasText(language.getLanguage())) {
      map.set("language", urlEncode(language.getLanguage(), urlEncode));
    }
    if (computeBestOrder != null) {
      map.set("computeBestOrder", computeBestOrder.toString());
    }
    if (routeRepresentation != null) {
      map.set("routeRepresentation", urlEncode(routeRepresentation.getValue(), urlEncode));
    }
    if (computeTravelTimeFor != null) {
      map.set("computeTravelTimeFor", urlEncode(computeTravelTimeFor.getValue(), urlEncode));
    }
    if (vehicleHeading != null) {
      map.set("vehicleHeading", vehicleHeading.toString());
    }
    if (sectionType != null && !sectionType.isEmpty()) {
      map.set("sectionType", urlEncode(
          StringUtils.collectionToCommaDelimitedString(sectionType),
          urlEncode));
    }
    if (report != null) {
      map.set("report", urlEncode(report.getValue(), urlEncode));
    }
    if (departAt != null) {
      map.set("departAt", DateTimeUtil.formatDateTime(departAt));
    }
    if (arriveAt != null) {
      map.set("arriveAt", DateTimeUtil.formatDateTime(arriveAt));
    }
    if (routeType != null) {
      map.set("routeType", urlEncode(routeType.getValue(), urlEncode));
    }
    if (traffic != null) {
      map.set("traffic", traffic.toString());
    }
    if (avoid != null && !avoid.isEmpty()) {
      map.set("avoid", urlEncode(
          StringUtils.collectionToCommaDelimitedString(
              avoid.stream().map(Avoid::getValue).collect(Collectors.toList())),
          urlEncode));
    }
    if (travelMode != null) {
      map.set("travelMode", urlEncode(travelMode.getValue(), urlEncode));
    }
    if (hilliness != null) {
      map.set("hilliness", urlEncode(hilliness.getValue(), urlEncode));
    }
    if (windingness != null) {
      map.set("windingness", urlEncode(windingness.getValue(), urlEncode));
    }
    if (vehicleMaxSpeed != null) {
      map.set("vehicleMaxSpeed", vehicleMaxSpeed.toString());
    }
    if (vehicleWeight != null) {
      map.set("vehicleWeight", vehicleWeight.toString());
    }
    if (vehicleAxleWeight != null) {
      map.set("vehicleAxleWeight", vehicleAxleWeight.toString());
    }
    if (vehicleLength != null) {
      map.set("vehicleLength", vehicleLength.toPlainString());
    }
    if (vehicleWidth != null) {
      map.set("vehicleWidth", vehicleWidth.toPlainString());
    }
    if (vehicleHeight != null) {
      map.set("vehicleHeight", vehicleHeight.toPlainString());
    }
    if (vehicleCommercial != null) {
      map.set("vehicleCommercial", vehicleCommercial.toString());
    }
    if (vehicleLoadType != null && !vehicleLoadType.isEmpty()) {
      map.set("vehicleLoadType", urlEncode(
          StringUtils.collectionToCommaDelimitedString(
              vehicleLoadType.stream().map(VehicleLoadType::getValue).collect(Collectors.toList())),
          urlEncode));
    }
    return map;
  }

  /**
   * Has post body?
   *
   * @return the boolean
   */
  public boolean hasPostBody() {
    return (supportingPoints != null && !supportingPoints.isEmpty())
        || (avoidAreas != null && !avoidAreas.isEmpty())
        || !buildISO3Countries(avoidVignette).isEmpty()
        || !buildISO3Countries(allowVignette).isEmpty();
  }

  /**
   * Build post body.
   *
   * @return the post body
   */
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

  /**
   * The post body.
   */
  @JsonInclude(Include.NON_EMPTY)
  @Data
  @SuppressWarnings("WeakerAccess")
  public static class PostBody {

    private List<LatitudeLongitude> supportingPoints;

    private List<String> avoidVignette;

    private List<String> allowVignette;

    private AvoidAreas avoidAreas;

    /**
     * The avoid areas.
     */
    @JsonInclude(Include.NON_EMPTY)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvoidAreas {

      private List<Rectangle> rectangles;
    }
  }

  /**
   * The alternative type.
   */
  public enum AlternativeType {

    /**
     * Any route alternative type.
     */
    ANY_ROUTE("anyRoute"),

    /**
     * Better route alternative type.
     */
    BETTER_ROUTE("betterRoute");

    @Getter
    private String value;

    AlternativeType(String value) {
      this.value = value;
    }

    /**
     * From value alternative type.
     *
     * @param value the value
     * @return the alternative type
     */
    public static AlternativeType fromValue(String value) {
      for (AlternativeType alternativeType : AlternativeType.values()) {
        if (alternativeType.getValue().equalsIgnoreCase(value)) {
          return alternativeType;
        }
      }
      return null;
    }
  }

  /**
   * The instructions type.
   */
  public enum InstructionsType {

    /**
     * Coded instructions type.
     */
    CODED("coded"),

    /**
     * Text instructions type.
     */
    TEXT("text"),

    /**
     * Tagged instructions type.
     */
    TAGGED("tagged");

    @Getter
    private String value;

    InstructionsType(String value) {
      this.value = value;
    }

    /**
     * From value instructions type.
     *
     * @param value the value
     * @return the instructions type
     */
    public static InstructionsType fromValue(String value) {
      for (InstructionsType instructionsType : InstructionsType.values()) {
        if (instructionsType.getValue().equalsIgnoreCase(value)) {
          return instructionsType;
        }
      }
      return null;
    }
  }

  /**
   * The route representation.
   */
  public enum RouteRepresentation {

    /**
     * Polyline route representation.
     */
    POLYLINE("polyline"),

    /**
     * None route representation.
     */
    NONE("none");

    @Getter
    private String value;

    RouteRepresentation(String value) {
      this.value = value;
    }

    /**
     * From value route representation.
     *
     * @param value the value
     * @return the route representation
     */
    public static RouteRepresentation fromValue(String value) {
      for (RouteRepresentation routeRepresentation : RouteRepresentation.values()) {
        if (routeRepresentation.getValue().equalsIgnoreCase(value)) {
          return routeRepresentation;
        }
      }
      return null;
    }
  }

  /**
   * The travel time.
   */
  public enum TravelTime {

    /**
     * All travel time.
     */
    ALL("all"),

    /**
     * None travel time.
     */
    NONE("none");

    @Getter
    private String value;

    TravelTime(String value) {
      this.value = value;
    }

    /**
     * From value travel time.
     *
     * @param value the value
     * @return the travel time
     */
    public static TravelTime fromValue(String value) {
      for (TravelTime travelTime : TravelTime.values()) {
        if (travelTime.getValue().equalsIgnoreCase(value)) {
          return travelTime;
        }
      }
      return null;
    }
  }

  /**
   * The report.
   */
  public enum Report {

    /**
     * Effective settings report.
     */
    EFFECTIVE_SETTINGS("effectiveSettings");

    @Getter
    private String value;

    Report(String value) {
      this.value = value;
    }

    /**
     * From value report.
     *
     * @param value the value
     * @return the report
     */
    public static Report fromValue(String value) {
      for (Report report : Report.values()) {
        if (report.getValue().equalsIgnoreCase(value)) {
          return report;
        }
      }
      return null;
    }
  }

  /**
   * The route type.
   */
  public enum RouteType {

    /**
     * Fastest route type.
     */
    FASTEST("fastest"),

    /**
     * Shortest route type.
     */
    SHORTEST("shortest"),

    /**
     * Eco route type.
     */
    ECO("eco"),

    /**
     * Thrilling route type.
     */
    THRILLING("thrilling");

    @Getter
    private String value;

    RouteType(String value) {
      this.value = value;
    }

    /**
     * From value route type.
     *
     * @param value the value
     * @return the route type
     */
    public static RouteType fromValue(String value) {
      for (RouteType routeType : RouteType.values()) {
        if (routeType.getValue().equalsIgnoreCase(value)) {
          return routeType;
        }
      }
      return null;
    }
  }

  /**
   * The avoid.
   */
  public enum Avoid {

    /**
     * Toll roads avoid.
     */
    TOLL_ROADS("tollRoads"),

    /**
     * Motorways avoid.
     */
    MOTORWAYS("motorways"),

    /**
     * Ferries avoid.
     */
    FERRIES("ferries"),

    /**
     * Unpaved roads avoid.
     */
    UNPAVED_ROADS("unpavedRoads"),

    /**
     * Carpools avoid.
     */
    CARPOOLS("carpools"),

    /**
     * Already used roads avoid.
     */
    ALREADY_USED_ROADS("alreadyUsedRoads");

    @Getter
    private String value;

    Avoid(String value) {
      this.value = value;
    }

    /**
     * From value avoid.
     *
     * @param value the value
     * @return the avoid
     */
    public static Avoid fromValue(String value) {
      for (Avoid avoid : Avoid.values()) {
        if (avoid.getValue().equalsIgnoreCase(value)) {
          return avoid;
        }
      }
      return null;
    }
  }

  /**
   * The travel mode.
   */
  public enum TravelMode {

    /**
     * Car travel mode.
     */
    CAR("car"),

    /**
     * Truck travel mode.
     */
    TRUCK("truck"),

    /**
     * Taxi travel mode.
     */
    TAXI("taxi"),

    /**
     * Bus travel mode.
     */
    BUS("bus"),

    /**
     * Van travel mode.
     */
    VAN("van"),

    /**
     * Motorcycle travel mode.
     */
    MOTORCYCLE("motorcycle"),

    /**
     * Bicycle travel mode.
     */
    BICYCLE("bicycle"),

    /**
     * Pedestrian travel mode.
     */
    PEDESTRIAN("pedestrian");

    @Getter
    private String value;

    TravelMode(String value) {
      this.value = value;
    }

    /**
     * From value travel mode.
     *
     * @param value the value
     * @return the travel mode
     */
    public static TravelMode fromValue(String value) {
      for (TravelMode travelMode : TravelMode.values()) {
        if (travelMode.getValue().equalsIgnoreCase(value)) {
          return travelMode;
        }
      }
      return null;
    }
  }

  /**
   * The hilliness.
   */
  public enum Hilliness {

    /**
     * Low hilliness.
     */
    LOW("low"),

    /**
     * Normal hilliness.
     */
    NORMAL("normal"),

    /**
     * High hilliness.
     */
    HIGH("high");

    @Getter
    private String value;

    Hilliness(String value) {
      this.value = value;
    }

    /**
     * From value hilliness.
     *
     * @param value the value
     * @return the hilliness
     */
    public static Hilliness fromValue(String value) {
      for (Hilliness hilliness : Hilliness.values()) {
        if (hilliness.getValue().equalsIgnoreCase(value)) {
          return hilliness;
        }
      }
      return null;
    }
  }

  /**
   * The windingness.
   */
  public enum Windingness {

    /**
     * Low windingness.
     */
    LOW("low"),

    /**
     * Normal windingness.
     */
    NORMAL("normal"),

    /**
     * High windingness.
     */
    HIGH("high");

    @Getter
    private String value;

    Windingness(String value) {
      this.value = value;
    }

    /**
     * From value windingness.
     *
     * @param value the value
     * @return the windingness
     */
    public static Windingness fromValue(String value) {
      for (Windingness windingness : Windingness.values()) {
        if (windingness.getValue().equalsIgnoreCase(value)) {
          return windingness;
        }
      }
      return null;
    }
  }

  /**
   * The vehicle load type.
   */
  public enum VehicleLoadType {

    /**
     * Us hazmat class 1 vehicle load type.
     */
    US_HAZMAT_CLASS_1("USHazmatClass1", "Explosives (US)"),

    /**
     * The Us hazmat class 2.
     */
    US_HAZMAT_CLASS_2("USHazmatClass2", "Compressed gas"),

    /**
     * The Us hazmat class 3.
     */
    US_HAZMAT_CLASS_3("USHazmatClass3", "Flammable liquids"),

    /**
     * The Us hazmat class 4.
     */
    US_HAZMAT_CLASS_4("USHazmatClass4", "Flammable solids"),

    /**
     * Us hazmat class 5 vehicle load type.
     */
    US_HAZMAT_CLASS_5("USHazmatClass5", "Oxidizers"),

    /**
     * Us hazmat class 6 vehicle load type.
     */
    US_HAZMAT_CLASS_6("USHazmatClass6", "Poisons"),

    /**
     * Us hazmat class 7 vehicle load type.
     */
    US_HAZMAT_CLASS_7("USHazmatClass7", "Radioactive"),

    /**
     * Us hazmat class 8 vehicle load type.
     */
    US_HAZMAT_CLASS_8("USHazmatClass8", "Corrosives"),

    /**
     * Us hazmat class 9 vehicle load type.
     */
    US_HAZMAT_CLASS_9("USHazmatClass9", "Miscellaneous"),

    /**
     * Other hazmat explosive vehicle load type.
     */
    OTHER_HAZMAT_EXPLOSIVE("otherHazmatExplosive", "Explosives"),

    /**
     * Other hazmat general vehicle load type.
     */
    OTHER_HAZMAT_GENERAL("otherHazmatGeneral", "Miscellaneous"),

    /**
     * The Other hazmat harmful to water.
     */
    OTHER_HAZMAT_HARMFUL_TO_WATER("otherHazmatHarmfulToWater", "Harmful to water");

    @Getter
    private String value;

    @Getter
    private String description;

    VehicleLoadType(String value, String description) {
      this.value = value;
      this.description = description;
    }

    /**
     * From value vehicle load type.
     *
     * @param value the value
     * @return the vehicle load type
     */
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
