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

📸 Screenshots

(Add screenshots in your repo, e.g. UI and PDF sample)

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

Screenshots or logs (if available)

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
