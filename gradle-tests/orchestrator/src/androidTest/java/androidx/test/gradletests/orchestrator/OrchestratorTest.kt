/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.test.gradletests.orchestrator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import java.io.File
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrchestratorTest {
  // create two tests, and verify storage is cleared between them by running the same actions in
  // each
  @Test
  fun storage1() {
    verifyStorageClearedThenAdd()
  }

  @Test
  fun storage2() {
    verifyStorageClearedThenAdd()
  }

  @Test
  fun `test with whitespace`() {

  }

  private fun verifyStorageClearedThenAdd() {
    val savedFile =
      File(ApplicationProvider.getApplicationContext<Context>().filesDir, "myfile.txt")
    assertThat(savedFile.exists()).isFalse()

    savedFile.writeText("hi")
  }
}
