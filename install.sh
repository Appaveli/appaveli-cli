#!/bin/bash

set -e

# Detect fat JAR
FAT_JAR=$(find target -name 'appaveli-cli-*-jar-with-dependencies.jar' | head -n 1)
INSTALL_DIR="$HOME/.local/lib/appaveli-cli"
SYMLINK="$HOME/.local/bin/appaveli-cli"
JAR_NAME=$(basename "$FAT_JAR")

if [ -z "$FAT_JAR" ]; then
  echo "❌ Fat JAR not found in target/. Run 'mvn clean package' first."
  exit 1
fi

echo "📦 Installing $JAR_NAME..."

# Create install location
mkdir -p "$INSTALL_DIR"
cp "$FAT_JAR" "$INSTALL_DIR/"

# Write launcher script
cat <<EOF > "$INSTALL_DIR/appaveli-cli.sh"
#!/bin/bash
java -jar "\$HOME/.local/lib/appaveli-cli/$JAR_NAME" "\$@"
EOF

chmod +x "$INSTALL_DIR/appaveli-cli.sh"

# Create symlink
mkdir -p "$HOME/.local/bin"
ln -sf "$INSTALL_DIR/appaveli-cli.sh" "$SYMLINK"
chmod +x "$SYMLINK"

# Ensure PATH includes ~/.local/bin
if [[ ":$PATH:" != *":$HOME/.local/bin:"* ]]; then
  SHELL_RC="$HOME/.zshrc"
  [ -f "$HOME/.bashrc" ] && SHELL_RC="$HOME/.bashrc"

  echo 'export PATH="$HOME/.local/bin:$PATH"' >> "$SHELL_RC"
  echo "✅ Added ~/.local/bin to PATH in $SHELL_RC"
  echo "⚠️  Run 'source $SHELL_RC' or restart your terminal to apply changes."
fi

echo "✅ Appaveli CLI installed!"
echo "👉 Try: appaveli-cli --help"