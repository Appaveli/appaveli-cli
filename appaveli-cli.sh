#!/bin/bash

# === Config ===
GROUP_ID="io.github.appaveli"
ARTIFACT_ID="appaveli-cli"
VERSION="1.8.0"

# Convert group ID to path
GROUP_PATH="${GROUP_ID//.//}"

# Construct full path to JAR
JAR_PATH="$HOME/.m2/repository/$GROUP_PATH/$ARTIFACT_ID/$VERSION/$ARTIFACT_ID-$VERSION.jar"

# Check if JAR exists
if [ ! -f "$JAR_PATH" ]; then
  echo "Appaveli CLI not found locally. Downloading from Maven..."
  mvn dependency:get -Dartifact=$GROUP_ID:$ARTIFACT_ID:$VERSION
  if [ $? -ne 0 ]; then
    echo "Failed to download Appaveli CLI."
    exit 1
  fi
fi

# Run the CLI with all passed arguments
java -jar "$JAR_PATH" "$@"
