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

import org.sonar.plugins.benchrest.BenchRestMetrics;
import org.sonar.plugins.benchrest.BenchRestPlugin;
import org.sonar.plugins.benchrest.BenchRestPluginConst;
import org.sonar.plugins.benchrest.BenchRestStats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

import java.io.File;

public class BenchRestSensor implements Sensor {

	private static final Logger LOG = LoggerFactory
			.getLogger(BenchRestSensor.class);

	private Settings settings;
	private FileSystem fs;

	/**
	 * Use of IoC to get Settings and FileSystem
	 */
	public BenchRestSensor(Settings settings, FileSystem fs) {
		this.settings = settings;
		this.fs = fs;
	}

	public boolean shouldExecuteOnProject(Project project) {
		// This sensor is executed only when there are JavaScript files
		// return fs.hasFiles(fs.predicates().hasLanguage("javascript"));
		return true;
	}

	public void analyse(Project project, SensorContext sensorContext) {
		String statsFileLoc = settings
				.getString(BenchRestPluginConst.STATS_FILE_PROPERTY);

		LOG.info("Starting BenchRest Sensor");

		// Verify we have a stats file location.
		if (statsFileLoc == null || statsFileLoc.isEmpty()) {
			LOG.info("No Stats File configured.");
			return;
		}

		// Get the stats file path
		File statsFile = getIOFile(fs.baseDir(), statsFileLoc);

		if (!statsFile.isFile()) {
			LOG.warn(
					"No bench-rest information will be saved because stats file cannot be found. "
							+ "Provided bench-rest stats file path: {}",
					statsFileLoc);
			return;
		}

		LOG.info("Analysing {}", statsFile);

		try {
			String statsContents = FileUtils.readFileToString(statsFile);
			BenchRestStats stats = new BenchRestStats(statsContents);

			sensorContext.saveMeasure(BenchRestMetrics.min, stats.getMin());
			sensorContext.saveMeasure(BenchRestMetrics.max, stats.getMax());
			sensorContext.saveMeasure(BenchRestMetrics.sum, stats.getSum());
			sensorContext.saveMeasure(BenchRestMetrics.variance,
					stats.getVariance());
			sensorContext.saveMeasure(BenchRestMetrics.mean, stats.getMean());
			sensorContext.saveMeasure(BenchRestMetrics.stddev,
					stats.getStddev());
			sensorContext.saveMeasure(BenchRestMetrics.count, stats.getCount());
			sensorContext.saveMeasure(BenchRestMetrics.median,
					stats.getMedian());

		} catch (Exception e) {
			LOG.error("Bench-Rest cannot analyse project '" + project.getName()
					+ "'", e);
		}

	}

	/**
	 * Returns a java.io.File for the given path. If path is not absolute,
	 * returns a File with module base directory as parent path.
	 *
	 * This function was shamelessly stolen from sonar-javascript.
	 * https://github.com/SonarCommunity/sonar-javascript.git
	 */
	public static File getIOFile(File baseDir, String path) {
		File file = new File(path);
		if (!file.isAbsolute()) {
			file = new File(baseDir, path);
		}

		return file;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
