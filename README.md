# number-to-text

## Overview

Sample project to convert integers to text.

Examples:

| Sample input | Sample output |
|--------------|---------------|
| 0            | Zero          |
| 13           | Thirteen      |
| 85           | Eighty five   |
| 5237         | Five thousand two hundred and thirty seven |

Assumptions:

* Only Integer values in the range of Integer.MIN_VALUE and Integer.MAX_VALUE are supported (no fractional or decimal numbers)
* The target output is English.  Internationalization and localization are not supported.

## Execution

### Build

	mvn clean package
	
Notes:

* Code conventions are verified during the build using [checkstyle](http://checkstyle.sourceforge.net/)
* A unit test coverage report is available after successful build at `target/site/jacoco/index.html`
	

### Run

	java -jar target/number-to-text.jar [number]
	
## Development Model

Mostly follow [GitFlow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow "GitFlow") as implemented by [gitflow-maven-plugin](https://github.com/aleksandr-m/gitflow-maven-plugin)

### Create a feature branch

- `mvn gitflow:feature-start`
- implement the feature
- `mvn gitflow:feature-finish`

### Create a release

- `mvn gitflow:release-start`
- verify the release
- `mvn gitflow:release-finish`	