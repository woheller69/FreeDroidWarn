# FreeDroidWarn

### Overview

This library shows an alert dialog with a deprecation warning informing that Google will require developer verification for Android apps outside the Play Store from 2026/2027 which the developer is not going to provide.


 ```
Google has announced that, starting in 2026/2027, all apps on certified Android devices
will require the developer to submit personal identity details directly to Google.
Since the developers of this app do not agree to this requirement, this app will no longer 
work on certified Android devices after that time.
```

https://www.androidauthority.com/android-developer-verification-requirements-3590911/

https://developer.android.com/developer-verification

Some arguments:

Requiring developers to submit personal identity details to Google in order for their apps to run on certified Android devices represents a serious attack on fundamental digital rights:

Developer privacy – Individual developers and small teams should not be forced to hand over government IDs or sensitive documents to a multinational corporation. Many developers value their privacy for legitimate personal, political, or security reasons.

The right to use my own device – As a user, I should be free to run the software of my choice on my phone. Blocking applications that do not meet Google’s new requirements is a restriction on device ownership and digital freedom.

Free and open-source software ecosystems – Many FOSS projects are developed by volunteers who will not (and often cannot) provide identity documents. This policy risks removing an enormous amount of valuable free software from certified Android devices.

Developer safety – In some countries, linking real-world identities to developers of privacy tools, political apps, or security software can put them in danger. This requirement could actively harm people.

Adaptation and forking of open-source programs – One of the most important freedoms of open-source software is the ability to fork and adapt programs to personal or local needs. Today, I can simply fork an app, add a translation, build it, and install it on my device. Under the new rules, any fork would require a new package ID — which in turn would force the developer to register with Google and provide personal identity details. This creates a bureaucratic and privacy-invasive barrier to the most basic use of open-source: improving, localizing, and customizing software.

### Installation

Add the JitPack repository to your root build.gradle at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add the library dependency to your build.gradle file.

```gradle
dependencies {
    implementation 'com.github.woheller69:FreeDroidWarn:V1.4'
}
```

### Usage

In onCreate of your app just add:

```
     FreeDroidWarn.showWarningOnUpgrade(this, BuildConfig.VERSION_CODE);

```


### License

This library is licensed under the Apache V2.0 license.


