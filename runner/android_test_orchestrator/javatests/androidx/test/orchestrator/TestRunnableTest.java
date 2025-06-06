/*
 * Copyright (C) 2017 The Android Open Source Project
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
package androidx.test.orchestrator;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.google.common.truth.Truth.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import android.content.Context;
import android.os.Bundle;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.google.common.base.Joiner;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Unit tests for {@link TestRunnable}. */
@RunWith(AndroidJUnit4.class)
public class TestRunnableTest {

  private Bundle arguments;
  private OutputStream outputStream;
  private Context context;

  /**
   * A {@link TestRunnable} which does not actually call on the shell command executor but rather
   * saves the params for inspection.
   */
  private static class FakeTestRunnable extends TestRunnable {
    public List<String> params;

    FakeTestRunnable(
        Context context,
        String secret,
        Bundle arguments,
        OutputStream outputStream,
        RunFinishedListener listener,
        String test,
        boolean collectTests) {
      super(context, secret, arguments, outputStream, listener, test, collectTests);
    }

    @Override
    InputStream runShellCommand(List<String> params) {
      this.params = params;
      InputStream stream =
          new InputStream() {
            int mByte = 'a';

            @Override
            public int read() throws IOException {
              // A very basic input stream which outputs a-g then terminates
              if (mByte <= 'g') {
                return mByte++;
              } else {
                return -1;
              }
            }
          };
      return stream;
    }
  }

  private static class FakeListener implements TestRunnable.RunFinishedListener {
    public boolean finishedCalled = false;

    @Override
    public void runFinished() {
      finishedCalled = true;
    }
  }

  @Before
  public void setUp() {
    arguments = new Bundle();
    arguments.putString("targetInstrumentation", "targetInstrumentation/targetRunner");
    arguments.putString("class", "com.google.android.example.MyClass");
    arguments.putString("arg1", "val1");

    outputStream = new ByteArrayOutputStream();
    context = getApplicationContext();
  }

  @Test
  public void testRun_callsListener() {
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertThat(listener.finishedCalled, is(true));
  }

  @Test
  public void testRun_returnsOutputStream() {
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertThat(outputStream.toString(), is("abcdefg"));
  }

  @Test
  public void testRun_buildsParams_givenClassNameAndMethod() {
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(
            null,
            "secret",
            arguments,
            outputStream,
            listener,
            "com.google.android.example.MyClass#methodName",
            false);
    runnable.run();
    assertContainsRunnerArgs(
        runnable.params, "-e arg1 val1", "-e class com.google.android.example.MyClass#methodName");
  }

  @Test
  public void testRun_removesPackage_givenClassNameAndMethod() {
    FakeListener listener = new FakeListener();
    arguments.putString("package", "package.to.be.deleted");
    FakeTestRunnable runnable =
        new FakeTestRunnable(
            null,
            "secret",
            arguments,
            outputStream,
            listener,
            "com.google.android.example.MyClass#methodName",
            false);
    runnable.run();
    assertContainsRunnerArgs(
        runnable.params, "-e arg1 val1", "-e class com.google.android.example.MyClass#methodName");
  }

  @Test
  public void testRun_buildsParams_givenNullClassNameAndMethodForTestCollection() {
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertContainsRunnerArgs(
        runnable.params,
        "-e listTestsForOrchestrator true",
        "-e arg1 val1",
        "-e class com.google.android.example.MyClass");
  }

  @Test
  public void testRun_buildsParams_givenInstrumentationParamsAreHandledCorrectlyWhenTrue() {
    arguments.putString(
        "orchestratorInstrumentationArgs", "--no-hidden-api-checks, --no-isolated-storage");
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertContainsRunnerArgs(runnable.params, "-no-hidden-api-checks --no-isolated-storage");
  }

  @Test
  public void testRun_buildsParams_givenInstrumentationParamsAreHandledCorrectlyWithSpace() {
    arguments.putString(
        "orchestratorInstrumentationArgs",
        "--no-hidden-api-checks, --no-isolated-storage, --abi x86_64");
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertContainsRunnerArgs(
        runnable.params, "-no-hidden-api-checks --no-isolated-storage --abi x86_64");
  }

  @Test
  public void testRun_buildsParams_givenInstrumentationParamsAreHandledCorrectlySingleParam() {
    arguments.putString("orchestratorInstrumentationArgs", "--no-hidden-api-checks");
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertContainsRunnerArgs(runnable.params, "--no-hidden-api-checks");
  }

  @Test
  public void testRun_buildsParams_givenInstrumentationParamsAreSpaceDelimited() {
    arguments.putString("orchestratorInstrumentationArgs", "--abi x86_64");
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertThat(runnable.params).containsAtLeast("--abi", "x86_64").inOrder();
  }

  @Test
  public void testRun_buildsParams_allowTestNamesWithSpace() {
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(
            context,
            "secret",
            arguments,
            outputStream,
            listener,
            "com.google.android.example.MyClass#methodNameWith Space",
            true);
    runnable.run();
    assertContainsRunnerArgs(
        runnable.params, "-e class com.google.android.example.MyClass#methodNameWith Space");
  }

  @Test
  public void testRun_buildsParams_givenInstrumentationParamsAreHandledCorrectlyMultipleParam() {
    arguments.putString("orchestratorInstrumentationArgs", "--A,--B,--C,--key value");
    FakeListener listener = new FakeListener();
    FakeTestRunnable runnable =
        new FakeTestRunnable(context, "secret", arguments, outputStream, listener, null, true);
    runnable.run();
    assertContainsRunnerArgs(runnable.params, "--A --B --C --key value");
  }

  private static void assertContainsRunnerArgs(List<String> params, String... containsArgs) {
    String cmdArgs = Joiner.on(" ").join(params);
    assertThat(cmdArgs, startsWith("instrument -w -r"));
    for (String arg : containsArgs) {
      assertThat(cmdArgs, containsString(arg));
    }
    assertThat(cmdArgs, endsWith("targetInstrumentation/targetRunner"));
  }
}
