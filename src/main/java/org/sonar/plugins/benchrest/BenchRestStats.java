/*
 * SonarQube Bench Rest Plugin
 * Copyright (C) 2015
 * Seth Kress <kresss at gmail dot com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package org.sonar.plugins.benchrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BenchRestStats {

	private static final Logger LOG = LoggerFactory
			.getLogger(BenchRestStats.class);

	private double min = 0;
	private double max = 0;
	private double sum = 0;
	private double variance = 0;
	private double mean = 0;
	private double stddev = 0;
	private double count = 0;
	private double median = 0;

	public BenchRestStats(String json) {

		try {

			JSONParser parser = new JSONParser();
			Object rootObj = parser.parse(json);
			JSONObject rootJsonObj = (JSONObject) rootObj;

			JSONObject mainJsonObj = (JSONObject) rootJsonObj.get("main");
			JSONObject histogramJsonObj = (JSONObject) mainJsonObj
					.get("histogram");

			this.min = (double) histogramJsonObj.get("min");
			this.max = (double) histogramJsonObj.get("max");
			this.sum = (double) histogramJsonObj.get("sum");
			this.variance = (double) histogramJsonObj.get("variance");
			this.mean = (double) histogramJsonObj.get("mean");
			this.stddev = (double) histogramJsonObj.get("stddev");

			Long tmpCount = (Long) histogramJsonObj.get("count");

			this.count = (double) tmpCount.floatValue();
			this.median = (double) histogramJsonObj.get("median");

		} catch (Exception e) {
			LOG.error("Failed to parse Bench Rest JSON");
		}
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getSum() {
		return sum;
	}

	public double getVariance() {
		return variance;
	}

	public double getMean() {
		return mean;
	}

	public double getStddev() {
		return stddev;
	}

	public double getCount() {
		return count;
	}

	public double getMedian() {
		return median;
	}

}
