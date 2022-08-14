# manifestprinter

This project showcases different ways of writing backwards-compatible Gradle plugins. It accompanies a talk held at [droidcon Berlin 2022](https://www.droidcon.com/2022/08/03/writing-backwards-compatible-gradle-plugins/), you can find the slides for this talk on [Speaker Deck](https://speakerdeck.com/simonschiller/writing-backwards-compatible-gradle-plugins-droidcon-berlin-2022).

### common

Shows how we can find out the version of a dependency (like the Android Gradle plugin) that has been resolved at runtime. 

### fixture

Shows how we can test our plugins against multiple Android Gradle plugin versions. 

### sample-groovy

Shows how a dynamically typed language like Groovy can be used to write code that is compatible with multiple Android Gradle plugin versions.

### sample-multiversion

Shows how we can set up a Gradle project to compile against multiple Android Gradle plugin versions, so we can write typesafe code that is compatible across multiple Android Gradle plugin versions.

### sample-reflection

Shows how reflection can be used to write code that is compatible with multiple Android Gradle plugin versions.

### sample-shadow

Shows how third-party dependencies can be repackaged and shipped with Gradle plugins to avoid clashes and version inconsistencies for users.

## License

```
Copyright 2022 Simon Schiller

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
