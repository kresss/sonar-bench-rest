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

import org.sonar.plugins.benchrest.BenchRestPluginConst;
import org.sonar.plugins.benchrest.BenchRestMetrics;
import org.sonar.plugins.benchrest.BenchRestSensor;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * This class is the entry point for all extensions
 */
@Properties({ @Property(key = BenchRestPluginConst.STATS_FILE_PROPERTY, name = "Stats File", description = "Path (absolute or relative) to a json file with the bench-rest stats object in it.") })
public final class BenchRestPlugin extends SonarPlugin {

	// This is where you're going to declare all your Sonar extensions
	public List getExtensions() {
		return Arrays.asList(
		// Definitions
				BenchRestMetrics.class,

				// Batch
				BenchRestSensor.class);

	}
}
