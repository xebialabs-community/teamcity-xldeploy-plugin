# teamcity-xldeploy-plugin

### CI status ###

[![Build Status][teamcity-xldeploy-plugin-travis-image] ][teamcity-xldeploy-plugin-travis-url]
[![Build Status][teamcity-xldeploy-plugin-codacy-image] ][teamcity-xldeploy-plugin-codacy-url]
[![Build Status][teamcity-xldeploy-plugin-code-climate-image] ][teamcity-xldeploy-plugin-code-climate-url]
[![License: MIT][teamcity-xldeploy-plugin-license-image] ][teamcity-xldeploy-plugin-license-url]
[![Github All Releases][teamcity-xldeploy-plugin-downloads-image] ]()

[teamcity-xldeploy-plugin-travis-image]: https://travis-ci.org/xebialabs-community/teamcity-xldeploy-plugin.svg?branch=master
[teamcity-xldeploy-plugin-travis-url]: https://travis-ci.org/xebialabs-community/teamcity-xldeploy-plugin
[teamcity-xldeploy-plugin-codacy-image]: https://api.codacy.com/project/badge/Grade/b78313b1eb1b4b058dc4512b4d48c26f
[teamcity-xldeploy-plugin-codacy-url]: https://www.codacy.com/app/rvanstone/teamcity-xldeploy-plugin
[teamcity-xldeploy-plugin-code-climate-image]: https://codeclimate.com/github/xebialabs-community/teamcity-xldeploy-plugin/badges/gpa.svg
[teamcity-xldeploy-plugin-code-climate-url]: https://codeclimate.com/github/xebialabs-community/teamcity-xldeploy-plugin
[teamcity-xldeploy-plugin-license-image]: https://img.shields.io/badge/License-MIT-yellow.svg
[teamcity-xldeploy-plugin-license-url]: https://opensource.org/licenses/MIT
[teamcity-xldeploy-plugin-downloads-image]: https://img.shields.io/github/downloads/xebialabs-community/teamcity-xldeploy-plugin/total.svg

#### Installation ####

1. Generate zip file using mvn package from root directory
2. Copy zip file to TeamCity Data Directory (e.g. ~/.BuildServer/plugins)
3. Restart TeamCity server
4. Configure build steps

#### Publish step ####
![screenshot of publish step](images/teamcity-xldeploy-plugin-1.png)

#### Build step ####

![screenshot of deploy step](images/teamcity-xldeploy-plugin-2.png)

#### Build parameters ####

![screenshot of deploy step](images/teamcity-xldeploy-plugin-3.png)

Tested on TeamCity 10.0.3.
