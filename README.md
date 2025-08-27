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
    implementation 'com.github.woheller69:FreeDroidWarn:V1.3'
}
```

### Usage

In onCreate of your app just add:

```
     FreeDroidWarn.showWarningOnUpgrade(this, BuildConfig.VERSION_CODE);

```


### License

This library is licensed under the GPLv3.


