# Description:
#   Espresso Web Assertion

load("@build_bazel_rules_android//android:rules.bzl", "android_library")
load("@rules_java//java:defs.bzl", "java_library")

licenses(["notice"])

package(
    default_applicable_licenses = ["//espresso:license"],
    default_testonly = 1,
    default_visibility = ["//espresso/remote:__subpackages__"],
)

android_library(
    name = "remote_web_assertions",
    srcs = glob(
        ["*.java"],
    ),
    deps = [
        "//espresso/core/java/androidx/test/espresso/remote:exceptions",
        "//espresso/core/java/androidx/test/espresso/remote:interface",
        "//espresso/remote/java/androidx/test/espresso/remote:protoUtils",
        "//espresso/remote/java/androidx/test/espresso/remote:registry",
        "//espresso/remote/java/androidx/test/espresso/web/proto/assertion:web_assertions_proto_android_pb3",
        "//espresso/web/java/androidx/test/espresso/web/assertion",
        "//opensource/androidx:annotation",
        "//runner/android_junit_runner/java/androidx/test:runner",
        "@com_google_protobuf//:protobuf_javalite",
        "@maven//:org_jetbrains_kotlin_kotlin_stdlib",
    ],
)

java_library(
    name = "package-info",
    srcs = ["package-info.java"],
    deps = [
        "//opensource/androidx:annotation",
    ],
)
