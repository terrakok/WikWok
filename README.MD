# WikWok

https://github.com/user-attachments/assets/a131ab28-fc53-4e3b-81d8-8e1276ae5b36

> Wikipedia meets TikTok: Knowledge that keeps you scrolling

## 📱 About WikWok

WikWok is a beautiful and functional app that transforms your Wikipedia reading experience into an engaging, TikTok-style article feed. Learn something new with every scroll!

## ✨ Features

- **Beautiful Interface**: Clean, modern design focused on readability
- **TikTok-Style Feed**: Scroll through Wikipedia articles in an intuitive, engaging format
- **Cross-Platform**: Available on iOS, Android, Macos, Windows, Linux and web platforms
- **Completely Free**: No subscriptions, no ads, just knowledge
- **Open Source**: Community-driven development welcomes contributions

## ☕ Support the Developer

<p align="center">
  <a href="https://www.buymeacoffee.com/terrakok">
    <img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=terrakok&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff" alt="Buy Me A Coffee" height="50">
  </a>
</p>

If you enjoy WikWok and want to support its development, consider buying me a coffee. Your support helps keep this project free and open-source!

## 🚀 Getting Started

### Installation

Download WikWok from your preferred app store:

- App Store: IN PROGRESS
- [Google Play](https://play.google.com/store/apps/details?id=com.github.terrakok.wikwok.androidApp)
- [Desktop (Macos/Linux/Windows)](https://github.com/terrakok/WikWok/releases)
- [Web App](https://terrakok.github.io/WikWok/)

Or build it yourself from source!

## 🛠️ Development

### Prerequisites
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 17 or higher on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`  
Run the desktop **hot reload** application: `./gradlew :composeApp:jvmRunHot`

### iOS
To run the application on iPhone device/simulator:
 - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio

### Wasm Browser (Alpha)
Run the browser application: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue`

## 🧑‍💻 Idea
Inspiration and idea were seen in the web-application [wikitok](https://wikitok.vercel.app/)
