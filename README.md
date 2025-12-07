📦 Ricoom Batch Barcode PDF Generator

A simple and powerful JavaFX + ZXing + iText application that lets you create A4-sized printable barcode grids for product labeling.
Built for speed, accuracy, and ease of use — ideal for POS systems, inventory management, retail shops, and warehouse operations.

This tool takes your product list (name, barcode, quantity) and automatically generates a clean, grid-aligned PDF containing all barcodes, ready for printing on sticker paper.

⭐ Features

Beautiful A4 PDF Output

3-column grid layout

Auto-pagination after 30 barcode cells

Centered product names

Scaled crisp barcode images (Code128)

JavaFX Desktop UI

Add products interactively

Auto-generate random 12-digit barcodes

Adjustable quantities (1–1000)

Simple table for viewing/editing batch

Robust Barcode Generation

Uses ZXing (Code128)

High-resolution PNG rendering

Compatible with common POS scanners

Lightweight & Offline-ready

No server needed

Everything runs locally



🚀 Getting Started
Prerequisites

Java 17+

Maven or Gradle

JavaFX SDK (if not using modular runtime)

Clone the project
git clone https://github.com/ricoom/barcode-pdf-generator.git
cd barcode-pdf-generator

Run

If using Maven:

mvn clean install
mvn javafx:run
Build the project and copy dependencies
mvn clean package


This will:

Compile the project

Copy all runtime dependencies to target/libs

 Create a custom runtime image with jlink
jlink ^
  --module-path "%JAVA_HOME%\jmods;C:\javafx-jmods-21.0.7;target/libs" ^
  --add-modules java.base,java.desktop,java.logging,java.sql,java.xml.crypto,jdk.unsupported,javafx.controls,javafx.fxml,javafx.graphics,javafx.base ^
  --output runtime ^
  --strip-debug ^
  --compress=2 ^
  --no-header-files ^
  --no-man-pages


This creates a minimal runtime containing only the necessary modules for your application.

Package the app as a Windows .exe using jpackage
jpackage ^
  --type exe ^
  --name barcode-pdf-generator ^
  --input target ^
  --main-jar barcode-pdf-generator-0.0.1-SNAPSHOT.jar ^
  --main-class com.ricoom.barcode.BarcodePdfGridGenerator ^
  --runtime-image runtime ^
  --dest dist ^
  --icon app-icon.ico ^
  --win-menu ^
  --win-shortcut ^
  --vendor "Eric Mwaniki Munene" ^
  --description "BarcodePdfGridGenerator Application." ^
  --app-version "1.0.0" ^
  --copyright "© 2025 Eric Mwaniki Munene" ^
  --license-file license.txt

Or run the main class manually:

java --module-path /path/to/javafx --add-modules javafx.controls,javafx.fxml -jar target/app.jar

📄 PDF Layout Example

Each page contains a 3×10 grid (30 barcodes):

+-----------+-----------+-----------+
| Barcode 1 | Barcode 2 | Barcode 3 |
+-----------+-----------+-----------+
| Barcode 4 |    ...    | Barcode 6 |
+-----------+-----------+-----------+
|   ... (auto paginated if > 30)    |
+-----------+-----------+-----------+

🧩 Project Structure
src/main/java/com/ricoom/barcode/
│
├── BarcodePdfGridGenerator.java   # JavaFX main UI app
├── model/
│    └── Product.java              # Product model with JavaFX properties
└── ...

🛠️ Built With

JavaFX – Desktop UI

ZXing – Barcode generation

iText PDF – High-quality PDF rendering

🤝 Contributing

Contributions are welcome and highly appreciated!

Ways you can contribute:

🔧 Feature Ideas

CSV import/export

Edit existing rows

Custom label sizes / margin configuration

Support for QR codes

Dark mode

Settings persistence

🐞 Report Bugs

Open an issue with:

Steps to reproduce

Expected vs actual behavior


📦 Pull Requests

Fork the repo

Create a feature branch

Write clean, commented code

Submit the PR with a short description

We follow simple rules:

Keep it readable

Keep it documented

Keep it tested

📜 License

MIT License — free to use, modify, and distribute.

## 📜 License
This project is licensed under the MIT License.  
See the [LICENSE](LICENSE) file for details.


👨‍💻 Author

Eric (Ricoom software Developer)
If this project helps you, please ⭐ the repo!
