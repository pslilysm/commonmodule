# commonmodule
A lightweight module used to assemble mvp and send network requests.
Check out app module to know how to use.

## usage

* step 1
```groovy
allprojects {
    repositories {
        // Add it in your root build.gradle at the end of repositories:
        maven { url 'https://jitpack.io' }
    }
}
```
* step 2
```groovy
dependencies {
    // add core lib to your dependencies
    implementation 'com.github.pslilysm.commonmodule:corelibrary:1.0.9'
    // add rx lib to your dependencies
    implementation 'com.github.pslilysm.commonmodule:rxlibrary:1.0.9'
}
```

