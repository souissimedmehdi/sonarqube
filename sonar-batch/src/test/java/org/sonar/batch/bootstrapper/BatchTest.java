/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.batch.bootstrapper;

import org.junit.Test;
import org.sonar.api.batch.bootstrap.ProjectReactor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class BatchTest {
  @Test
  public void testBuilder() {
    Batch batch = newBatch();
    assertNotNull(batch);
  }

  private Batch newBatch() {
    return Batch.builder()
      .setEnvironment(new EnvironmentInformation("Gradle", "1.0"))
      .setProjectReactor(new ProjectReactor(org.sonar.api.batch.bootstrap.ProjectDefinition.create()))
      .addComponent("fake")
      .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailIfNoEnvironment() {
    Batch.builder()
      .setProjectReactor(new ProjectReactor(org.sonar.api.batch.bootstrap.ProjectDefinition.create()))
      .addComponent("fake")
      .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailIfNoProjectReactor() {
    Batch.builder()
      .setEnvironment(new EnvironmentInformation("Gradle", "1.0"))
      .addComponent("fake")
      .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailIfNullComponents() {
    Batch.builder()
      .setProjectReactor(new ProjectReactor(org.sonar.api.batch.bootstrap.ProjectDefinition.create()))
      .setEnvironment(new EnvironmentInformation("Gradle", "1.0"))
      .setComponents(null)
      .build();
  }

  @Test
  public void shouldDisableLoggingConfiguration() {
    Batch batch = newBatch();
    batch.disableLoggingConfiguration();
    assertNull(batch.getLoggingConfiguration());
  }

  @Test
  public void loggingConfigurationShouldBeEnabledByDefault() {
    assertNotNull(newBatch().getLoggingConfiguration());
  }
}
