# Publicly visible alias to the AXT rules target

load("@rules_java//java:defs.bzl", "java_library")

package(
    default_applicable_licenses = ["//:license"],
    default_testonly = 1,
)

licenses(["notice"])

# This needs to be a java_library instead of an alias, so java_* based rules can reference it
java_library(
    name = "rules",
    visibility = ["//visibility:public"],
    exports = ["//runner/rules/java/androidx/test:rules"],
)
