# Description: Build rules for building androidx.test.truth from source

load("@build_bazel_rules_android//android:rules.bzl", "android_library")
load("//build_extensions:api_checks.bzl", "api_checks")
load("//build_extensions:dackka_test.bzl", "dackka_test")
load("//build_extensions/maven:axt_android_aar.bzl", "axt_android_aar")
load("//build_extensions/maven:maven_artifact.bzl", "maven_artifact")

# all users should reference the equivalent targets in //ext/truth
package(
    default_applicable_licenses = ["//:license"],
    default_testonly = 1,
    default_visibility = [
        "//:__pkg__",
        "//ext/truth:__subpackages__",
    ],
)

licenses(["notice"])

android_library(
    name = "truth",
    srcs = glob(
        ["**/*.java"],
    ),
    manifest = "AndroidManifest.xml",
    deps = [
        "//core",
        "//opensource/androidx:annotation",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
    ],
)

# Generate rules for the release artifacts.
axt_android_aar(
    name = "truth_aar",

    # omit banned check for guava, since its a dependency of upstream truth
    banned_maven_deps = ["com.google.dagger"],
    expected_class_prefixes = [
        "androidx.test.ext.truth",
    ],
    included_dep = ":truth",
)

maven_artifact(
    name = "truth_maven_artifact",
    last_updated = "20180321000000",
    target = ":truth_aar",
)

dackka_test(
    name = "truth_doc",
    runtime_dep = ":truth",
    src_jar = ":truth_aar-src.jar",
)

api_checks(
    name = "truth_api",
    runtime_dep = ":truth",
    src_jar = ":truth_aar-src.jar",
)
