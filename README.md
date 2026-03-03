# FreeDroidWarn

## Table of contents
- [Overview](#overview)
- [Arguments against developer verification](#arguments-against-developer-verification)
- [Solutions](#solutions)
  - [Set up ADB on your device](#set-up-adb-on-your-device)
    - [Download ADB for PC (Windows)](#download-adb-for-pc-windows)
    - [Download app APK](#download-app-apk)
    - [Connect phone to USB and install app](#connect-phone-to-usb-and-install-app)
- [Use this library in your own Android project](#use-this-library-in-your-own-android-project)
    - [Implement the dialog](#implement-the-dialog)
    - [Implement the SnackBar](#implement-the-snackbar)
- [License](#license)

## Overview

From 2026/2027 onward, Google will [require developer verification](https://developer.android.com/developer-verification) for all Android apps on certified devices, including those installed outside of the Play Store.

This library shows an alert dialog with a deprecation warning informing the user that the developer is not going to provide verification. It uses this text:

> Google has announced that, starting in 2026/2027, all apps on certified Android devices will require the developer to submit personal identity details directly to Google.
> 
> Since the developers of this app do not agree to this requirement, this app will no longer work on certified Android devices after that time.

See [Keep Android Open](https://keepandroidopen.org/) for more information and support us by submitting a complaint to your national regulator.

## Arguments against developer verification

Requiring developers to submit personal identity details to Google in order for their apps to run on certified Android devices represents a serious attack on fundamental digital rights:

**Developer privacy** – Individual developers and small teams should not be forced to hand over government IDs or sensitive documents to a multinational corporation. Many developers value their privacy for legitimate personal, political, or security reasons.

**The right to use my own device** – As a user, I should be free to run the software of my choice on my phone. Blocking applications that do not meet Google’s new requirements is a restriction on device ownership and digital freedom.

**Free and open-source software ecosystems** – Many FOSS projects are developed by volunteers who will not (and often cannot) provide identity documents. This policy risks removing an enormous amount of valuable free software from certified Android devices.

**Developer safety** – In some countries, linking real-world identities to developers of privacy tools, political apps, or security software can put them in danger. This requirement could actively harm people.

**Adaptation and forking of open-source programs** – One of the most important freedoms of open-source software is the ability to fork and adapt programs to personal or local needs. Today, I can simply fork an app, add a translation, build it, and install it on my device. Under the new rules, any fork would require a new package ID — which in turn would force the developer to register with Google and provide personal identity details. This creates a bureaucratic and privacy-invasive barrier to the most basic use of open-source: improving, localizing, and customizing software.

## Solutions

Developer verification will be enforced on certified devices with Google Play Services installed, which is the majority of Android devices. There are options to bypass the restriction:

- Use a free, uncensored Android system like [/e/os](https://e.foundation/e-os/), [LineageOS](https://lineageos.org/), or [GrapheneOS](https://grapheneos.org/) that does not preinstall Google Play Services.
- "Degoogle" by removing Google Play Services. Depending on the manufacturer of your phone this may require [rooting your device](https://www.androidauthority.com/root-android-277350/).
- Install apps via ADB. Google has already confirmed that ADB will continue to work in the future. You can either use ADB from a PC as described below or use a wireless ADB based installer like [anyapk](https://github.com/sam1am/anyapk).

### Set up ADB on your device

- Enable Developer options on your phone: In Android settings, find and tap the Build Number option (usually at the bottom in "About phone") seven times until you see the message "You are now a developer!"
- Return to the main Settings screen to find Developer options at the bottom (or it may be in System)
- Scroll through the options to find and enable USB debugging. On some devices, you can use the hourglass at the top of the Settings app to search for "USB debugging".

#### Download ADB for PC (Windows)

Download these files into a folder:

- [AdbWinApi.dll](https://github.com/K3V1991/ADB-and-FastbootPlusPlus/blob/main/AdbWinApi.dll?raw=true)
- [AdbWinUsbApi.dll](https://github.com/K3V1991/ADB-and-FastbootPlusPlus/blob/main/AdbWinUsbApi.dll?raw=true)
- [adb.exe](https://github.com/K3V1991/ADB-and-FastbootPlusPlus/blob/main/adb.exe?raw=true)
- [APK_Installer.bat](https://github.com/woheller69/FreeDroidWarn/blob/master/APK_Installer.bat?raw=true)

#### Download app APK

You will also need the APK file to install to your phone, e.g. from [F-Droid](https://f-droid.org/). Save the APK to the same folder where you downloaded the above files.

#### Connect phone to USB and install app

- Connect your phone to the PC via a USB cable.
- You should see a notification on your phone to change USB mode. Set it to file transfer mode.
- Open the folder where you saved the above files and double click `APK_Installer.bat`.
- Select desired APK from list and install.
- If prompted, check confirmation box on phone and agree to USB debugging from this PC.

Your app will be installed 🚀

Optional (**recommended**): Switch off USB debugging in Developer options until you need it again.

## Use this library in your own Android project
Add the JitPack repository to your root `build.gradle` at the end of repositories:

(If you are not using Groovy, see instructions on [Jitpack](https://jitpack.io/#woheller69/FreeDroidWarn))

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add the library dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.woheller69:FreeDroidWarn:V1.+'
}
```

### Implement the dialog
In `onCreate` of your app just add:

```java
import org.woheller69.freeDroidWarn.FreeDroidWarn;
...
FreeDroidWarn.showWarningDialogOnUpgrade(this, BuildConfig.VERSION_CODE);
```

### Implement the SnackBar
In `onCreate` of your app just add:

```java
import org.woheller69.freeDroidWarn.FreeDroidWarn;
...
FreeDroidWarn.showWarningSnackBarOnUpgrade(this, view, BuildConfig.VERSION_CODE);
```

## License

This library is licensed under the Apache License, Version 2.0.
