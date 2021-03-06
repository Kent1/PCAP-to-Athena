/*
 * This file is part of PCAP to Athena.
 *
 * Copyright (c) 2019 DNS Belgium.
 *
 * PCAP to Athena is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PCAP to Athena is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PCAP to Athena.  If not, see <https://www.gnu.org/licenses/>.
 */

package be.dnsbelgium.data.pcap.utils;

import org.junit.Test;
import org.slf4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

public class FileSizeTest {

  private static final Logger logger = getLogger(FileSizeTest.class);

  @Test
  public void friendlySize() {
    assertFriendlySize("811 Kilobytes",   811  * 1024L);
    assertFriendlySize("51 Megabytes",    51   * 1024L * 1024L);
    assertFriendlySize("511 Megabytes",   511  * 1024L * 1024L);
    assertFriendlySize("5.418 Gigabytes", 5548L * 1024L * 1024L);
    assertFriendlySize("5.418 Terabytes", 5548L * 1024L * 1024L * 1024L);
    assertFriendlySize("5.418 Petabytes", 5548L * 1024L * 1024L * 1024L * 1024L);
    assertFriendlySize("8192.000 Petabytes", Long.MAX_VALUE);
  }

  private void assertFriendlySize(String expected, long bytes) {
    String friendly = FileSize.friendlySize(bytes);
    logger.info("{} bytes => {}", bytes, friendly);
    assertEquals("input: " + bytes, expected, friendly);
  }

  @Test
  public void testFriendlyThroughput() {
    String s = FileSize.friendlyThroughput(1000, 1000);
    logger.info("s = {}", s);
    assertFriendlyThroughput("8.0 Kbps", 1000, 1000);
    assertFriendlyThroughput("8.00 Mbps", 1000, 1);
    assertFriendlyThroughput("800.00 Mbps", 100000, 1);
    assertFriendlyThroughput("8.00 Gbps", 1000000, 1);
  }

  private void assertFriendlyThroughput(String expected, long bytes, long millis) {
    String friendly = FileSize.friendlyThroughput(bytes, millis);
    logger.info("{} bytes / {} millis => {}", bytes, millis, friendly);
    assertEquals(bytes + " bytes in " + millis, expected, friendly);
  }


}