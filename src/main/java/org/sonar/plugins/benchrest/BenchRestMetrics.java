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

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public final class BenchRestMetrics implements Metrics {

	public static final String BENCHREST_DOMAIN = "Performance";

	public static final Metric<Double> min = new Metric.Builder(
			"minInterationTime", "Min Iteration Time", Metric.ValueType.FLOAT)
			.setDescription("Min Iteration Time")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	public static final Metric<Double> max = new Metric.Builder(
			"maxInterationTime", "Max Iteration Time", Metric.ValueType.FLOAT)
			.setDescription("Max Iteration Time")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	public static final Metric<Double> sum = new Metric.Builder(
			"sumInterationTime", "Sum Iteration Time", Metric.ValueType.FLOAT)
			.setDescription("Sum of all Iterations Time")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	public static final Metric<Double> variance = new Metric.Builder(
			"interationVariance", "Iteration Time Variance",
			Metric.ValueType.FLOAT)
			.setDescription("Variance of all Iterations")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	public static final Metric<Double> mean = new Metric.Builder(
			"meanIterationTime", "Mean Iteration Time", Metric.ValueType.FLOAT)
			.setDescription("Mean of all Iterations")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	public static final Metric<Double> stddev = new Metric.Builder(
			"iterationStdDev", "Iteration Std Dev", Metric.ValueType.FLOAT)
			.setDescription("Standard Deviation of all Iterations")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	public static final Metric<Double> count = new Metric.Builder(
			"iterationCount", "Iteration Count", Metric.ValueType.FLOAT)
			.setDescription("Count of all Iterations").setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	public static final Metric<Double> median = new Metric.Builder(
			"medianIterationTime", "Median Iteration Time",
			Metric.ValueType.FLOAT).setDescription("Median of all Iterations")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(BENCHREST_DOMAIN).create();

	// getMetrics() method is defined in the Metrics interface and is used by
	// Sonar to retrieve the list of new metrics
	public List<Metric> getMetrics() {
		return Arrays.<Metric> asList(min, max, sum, variance, mean, stddev,
				count, median);
	}
}
