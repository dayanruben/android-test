load("@build_bazel_rules_android//android:rules.bzl", "android_library")

package(
    default_applicable_licenses = ["//:license"],
    default_testonly = 1,
)

# Description:
#   JavaScript to Java bridging
package_group(
    name = "internal",
    packages = [
        "//espresso/web/...",
    ],
)

android_library(
    name = "bridge",
    srcs = glob(
        ["*java"],
    ),
    exports_manifest = 1,
    manifest = "AndroidManifest.xml",
    visibility = [":internal"],
    deps = [
        "//opensource/androidx:annotation",
        "//runner/monitor",
        "@maven//:androidx_concurrent_concurrent_futures",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven_listenablefuture//:com_google_guava_listenablefuture",
    ],
)
