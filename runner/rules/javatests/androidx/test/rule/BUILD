# Description: Tests for Rules.
load("//build_extensions:android_library_test.bzl", "axt_android_library_test")
load("//build_extensions:phone_devices.bzl", "apis", "devices")

package(
    default_applicable_licenses = ["//:license"],
    default_testonly = 1,
)

licenses(["notice"])

axt_android_library_test(
    name = "ActivityTestRuleTest",
    srcs = ["ActivityTestRuleTest.java"],
    manifest = "AndroidManifest_activitytestrule.xml",
    shard_count = 2,
    deps = [
        "//core",
        "//ext/junit",
        "//runner/android_junit_runner",
        "//runner/rules",
        "@maven//:junit_junit",
        "@maven//:org_hamcrest_hamcrest_core",
        "@maven//:org_mockito_mockito_core",
    ],
)

axt_android_library_test(
    name = "AndroidTimeoutUiTest",
    srcs = ["AndroidTimeoutUiTest.java"],
    # needs access to android.test classes
    manifest = "AndroidManifest_testlibrary.xml",
    deps = [
        "//core",
        "//ext/junit",
        "//runner/android_junit_runner",
        "//runner/rules",
        "@androidsdk//:legacy_test-35",
        "@maven//:junit_junit",
        "@maven//:org_mockito_mockito_core",
    ],
)

axt_android_library_test(
    name = "DisableOnAndroidDebugTest",
    srcs = ["DisableOnAndroidDebugTest.java"],
    deps = [
        "//core",
        "//ext/junit",
        "//runner/android_junit_runner",
        "//runner/rules",
        "@maven//:junit_junit",
    ],
)

axt_android_library_test(
    name = "GrantPermissionRuleTest",
    srcs = ["GrantPermissionRuleTest.java"],
    # runtime permissions only a thing from api 23+
    device_list = devices(apis(min_api = 23)),
    deps = [
        "//core",
        "//ext/junit",
        "//runner/rules",
        "@maven//:junit_junit",
    ],
)

axt_android_library_test(
    name = "PortForwardingRuleTest",
    srcs = ["PortForwardingRuleTest.java"],
    deps = [
        "//core",
        "//ext/junit",
        "//runner/android_junit_runner",
        "//runner/rules",
        "@maven//:junit_junit",
    ],
)

axt_android_library_test(
    name = "ServiceTestRuleTest",
    srcs = ["ServiceTestRuleTest.java"],
    manifest = "AndroidManifest_servicetestrule.xml",
    deps = [
        "//core",
        "//ext/junit",
        "//opensource/androidx:annotation",
        "//runner/android_junit_runner",
        "//runner/rules",
        "@maven//:junit_junit",
        "@maven//:org_hamcrest_hamcrest_core",
    ],
)

axt_android_library_test(
    name = "UiThreadTestRuleTest",
    srcs = ["UiThreadTestRuleTest.java"],
    # needs access to android.test classes
    manifest = "AndroidManifest_testlibrary.xml",
    deps = [
        "//core",
        "//ext/junit",
        "//runner/android_junit_runner",
        "//runner/rules",
        "@androidsdk//:legacy_test-35",
        "@maven//:junit_junit",
    ],
)
