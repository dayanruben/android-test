# Description:
#   Espresso Web Actions

load("@build_bazel_rules_android//android:rules.bzl", "android_library")
load("@rules_java//java:defs.bzl", "java_library")

licenses(["notice"])

package(
    default_applicable_licenses = ["//espresso:license"],
    default_testonly = 1,
    default_visibility = ["//espresso/remote:__subpackages__"],
)

android_library(
    name = "remote_web_actions",
    srcs = glob(
        ["*.java"],
    ),
    deps = [
        "//espresso/core/java/androidx/test/espresso/remote:exceptions",
        "//espresso/core/java/androidx/test/espresso/remote:interface",
        "//espresso/core/java/androidx/test/espresso/remote:reflectionUtils",
        "//espresso/remote/java/androidx/test/espresso/remote:protoUtils",
        "//espresso/remote/java/androidx/test/espresso/remote:registry",
        "//espresso/remote/java/androidx/test/espresso/web/proto/action:web_actions_proto_android_pb3",
        "//espresso/web/java/androidx/test/espresso/web/action",
        "//espresso/web/java/androidx/test/espresso/web/model",
        "//opensource/androidx:annotation",
        "@com_google_protobuf//:protobuf_javalite",
    ],
)

java_library(
    name = "package-info",
    srcs = ["package-info.java"],
    deps = [
        "//opensource/androidx:annotation",
    ],
)
