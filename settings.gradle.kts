rootProject.name = "soldier-letter"

## Project-wide Gradle settings.
#
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
#
# The Gradle daemon aims to improve the startup and execution time of Gradle.
# When set to true the Gradle daemon is to run the build.
org.gradle.daemon=true
#
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
# Default value: -Xmx10248m -XX:MaxPermSize=256m
    org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
#
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. More details, visit
# http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:decoupled_projects
org.gradle.parallel=true
#
# Enables new incubating mode that makes Gradle selective when configuring projects.
# Only relevant projects are configured which results in faster builds for large multi-projects.
# http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:configuration_on_demand
org.gradle.configureondemand=true
