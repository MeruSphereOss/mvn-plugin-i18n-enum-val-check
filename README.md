# Maven Plugin :: i18n-enum-val-check
This Plugin will Checks that whether the enum values/constants have corresponding entries in the messages.properties file or not.

## Problem Statement
Ensuring all enum values have corresponding i18n codes in the messages.properties files.

## Solution
Ensuring all enum values have corresponding i18n codes in the messages.properties files.

# How to use this Plugin in your project

To use this, Include the below in your project plugins section of pom.xml

```
			<plugin>
				<groupId>com.merusphere.devops</groupId>
				<artifactId>mvnplugin.i18n-enum-val-check</artifactId>
				<version>0.9.1</version>
				<executions>
					<execution>
						<id>compile</id>
						<goals>
							<goal>compile</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<pkg>com.your.project</pkg>
				</configuration>
			</plugin>
```

## Configuration Parameters
* pkg : Name of the Java Package contains all the enum Classes
		* Default value : No Default value
		* Mandatory : Mandatory
		* Description : Name of the Java Package contains all the enum Classes
		* Example : com.merusphere.dao.model

* msgPropertiesPath : Path of the messages.properties file
		* Default value : No Default value
		* Mandatory : Mandatory
		* Description : Name of the messages.properties file
		* Example : messages.properties


# How to develop this Plugin based on your requirements

Please follow the below steps to set up the Development Environment in your system
## Pre-requisities
+ Open JDK 11
+ Maven
+ pgp latest version
+ PGP Private and Public Keys. Get these keys from the github repository 
Development environment set up instructions can be found at [Github Repo](https://github.com/MeruSphereOss/mvn-plugin-dev-setup.git)

## Eclipse or STS
+ Project environment should be Open JDK 11

## Source code repository
Checkout the code from [i18n-enum-val-check](https://github.com/MeruSphereOss/mvn-plugin-i18n-enum-val-check)

## GPG/PGP Keys setup
+ Checkout the GPG/PGP Keys from the [Github Repo](https://github.com/MeruSphereOss/mvn-plugin-dev-setup.git) and import into the local system

## Branch & Release


## Release notes
Please refer to https://github.com/MeruSphereOss/mvn-plugin-i18n-enum-val-check/releases

## Report Bugs
Please report all the issues to Github issues https://github.com/MeruSphereOss/mvn-plugin-i18n-enum-val-check/issues

## Contribution
Please finish your development and create branch then Please crate a Github Issue at Gitub issues https://github.com/MeruSphereOss/mvn-plugin-i18n-enum-val-check/issues with the feature details & branch details. One of the Maintainer will co-ordinate with you.

