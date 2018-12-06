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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date time formatter.
 *
 * @author Christian Bremer
 */
abstract class DateTimeUtil {

  private static final SimpleDateFormat sdf = new SimpleDateFormat(
      "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);

  static {
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  private DateTimeUtil() {
  }

  /**
   * Formats the date into the ISO8601 format (yyyy-mm-ddThh:mm:ssZ).
   *
   * @param date the date
   * @return the formatted date
   */
  static String formatDateTime(final Date date) {
    if (date == null) {
      return "";
    }
    return sdf.format(date);
  }

}
