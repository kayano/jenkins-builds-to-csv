# jenkins-builds-to-csv

Jenkins keeps info about historical builds in build.xml files under
jobs directory
This tool extracts startTime, result, duration from all build.xml for
all jobs into single file jobs/builds.csv

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen


## Installation

Download from https://github.com/kayano/jenkins-builds-to-csv.git


## Build

    $ lein uberjar

## Usage

    $ java -jar jenkins-builds-to-csv-0.1.0-standalone.jar <path-to-jenkins-jobs-dir>


## License

Distributed under the Eclipse Public License, the same as Clojure
