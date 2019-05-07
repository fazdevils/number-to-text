# number-to-text

## 

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

## Build

	mvn clean package

## Run

	java -jar target/number-to-text.jar [number]
	
## GitFlow

Mostly follow [GitFlow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow "GitFlow") as implemented by [JGit Flow](https://bitbucket.org/atlassian/jgit-flow/wiki/goals.wiki)

## Create a release

- `mvn gitflow:release-start`
- verify the release
- `mvn gitflow:release-finish`	