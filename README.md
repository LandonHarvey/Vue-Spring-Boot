# Spring Boot + Vue.js

###Table of Contents
1. [Prerequisites](#Prerequisites)
2. [Project Structure](#Project-Structure)
3. [Backend](#Backend)
4. [Front End](#Front-End)
5. [Parent Pom](#Parent-Pom)
6. [Run Application](#Run-Application)
7. [Front End Development](#Front-End-Development)
7. [Authors](#Authors)
8. [Acknowledgments](#Acknowledgments)

This will explain the process of creating a project utilizing Vue.js with a Java Spring Boot Backend. We will also be 
using a MySQL backend for data storage. there will be no testing involved in the build process in this guide. I am still a novice in that category.
I will not be going over deployment to desired platforms. I personally use this repo to help with deployment * [cods](https://github.com/zgulde/cods) visit if you are wanting to deploy.


###Prerequisites

You want to make sure you have an updated version of Node when going through this process as well as Vue-Cli.
Vue-Cli requires Node version 8.9 or above ([Vue-Cli](https://cli.vuejs.org/guide/installation.html))


You can check your node version in your terminal

```
    node -v
```

Run the following commands based on your operating system.


**MacOSX**

```
    brew install node
    npm install -g @vue/cli
```


**Linux**
```
    brew install node
    npm install -g @vue/cli
```

**Windows**
```
    brew install node
    npm install -g @vue/cli
```

###Project Structure

```
spring-boot-vuejs
├─┬ backend     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```

###Backend

Using Intellij create a spring initializr select `Web`
If you are not using Intellij use * [springInitializr](https://start.spring.io/) and initialize a Spring Boot app with `Web`. Place the zip’s contents in the backend folder.

Create a `backend` folder and move the src folder into your newly created `backend` folder

Create and Customize pom to copy content from Frontend for serving it later. Feel free to just take the `<build>` portion if you will be using your own dependencies.
Notice mysql-connector for using a mysql database.

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <artifactId>backend</artifactId>
    <packaging>jar</packaging>

    <parent>
        <groupId>com.example</groupId>
        <artifactId>example</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <artifactId>maven-resources-plugin</artifactId>
                <executions>
                    <execution>
                        <id>copy Vue.js frontend content</id>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>src/main/resources/public</outputDirectory>
                            <overwrite>true</overwrite>
                            <resources>
                                <resource>
                                    <directory>${project.parent.basedir}/frontend/target/dist</directory>
                                    <includes>
                                        <include>static/</include>
                                        <include>index.html</include>
                                        <include>favicon.ico</include>
                                    </includes>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
###Front End

Creating our `frontend` project is done with the `--no-git`, because our parent already has a git repo created and vue CLI will create a git repo if we do not specify no git then errors will occur.

From root folder
```
 vue create frontend --no-git
```

Vue will prompt you to select features that will be applied. For this project the selections where 

1.Manually select features 

2.Babel, Router, Vuex

This is where I have decided not to include unit testing. 

Create a Frontend Pom.xml file and include the following.

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <artifactId>frontend</artifactId>

    <parent>
        <groupId>com.example</groupId>
        <artifactId>example</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <frontend-maven-plugin.version>1.6</frontend-maven-plugin.version>
    </properties>

<build>
    <plugins>
        <plugin>
            <groupId>com.github.eirslett</groupId>
            <artifactId>frontend-maven-plugin</artifactId>
            <version>${frontend-maven-plugin.version}</version>
            <executions>
                <!-- Install our node and npm version to run npm/node scripts-->
                <execution>
                    <id>install node and npm</id>
                    <goals>
                        <goal>install-node-and-npm</goal>
                    </goals>
                    <configuration>
                        <nodeVersion>v10.10.0</nodeVersion>
                    </configuration>
                </execution>
                <!-- Install all project dependencies -->
                <execution>
                    <id>npm install</id>
                    <goals>
                        <goal>npm</goal>
                    </goals>
                    <!-- optional: default phase is "generate-resources" -->
                    <phase>generate-resources</phase>
                    <!-- Optional configuration which provides for running any npm command -->
                    <configuration>
                        <arguments>install</arguments>
                    </configuration>
                </execution>
                <!-- Build and minify static files -->
                <execution>
                    <id>npm run build</id>
                    <goals>
                        <goal>npm</goal>
                    </goals>
                    <configuration>
                        <arguments>run build</arguments>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
</project>
```

Tell Webpack to output the dist/ to target/

Node projects usually create dist/ directories that contain all minified source code and we will be needing that in our /target. 
We will create `vue.config.js` file and configure the `outputDir` and `assetsDir`.

```
    module.exports = {
      outputDir: 'target/dist',
      assetsDir: 'static'
    }
```

###Parent Pom

Set up your parent pom to allow for a moduler `pom` structure.

This will allow for 3 `pom` files to be present and the parent to utilzing the `frontend` and `backend` pom.xml files.

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>example</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>pom</packaging>

    <name>example</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <main.basedir>${project.basedir}</main.basedir>
        <skipTests>true</skipTests>
    </properties>

    <modules>
        <module>frontend</module>
        <module>backend</module>
    </modules>

    <build>
        <plugins>
        </plugins>
    </build>

</project>
```

###Run Application 

Make sure to input values into your `application.properties` file in your backend `resources` folder if you are using MySql

Example: 
``` 
spring.datasource.url=jdbc:mysql://localhost/example_db_name?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=example_db_user
spring.datasource.password=example_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true    
```
            
Inside the Root directory, 

```
mvn clean install
```

Run the complete application

```
mvn --projects backend spring-boot:run
```        

Now go to your specified `localhost`


###Front End Development

If you are just wanting to see your front end effects without having to rerun the app every time use, 

```
npm run serve
```

REMEMBER ONLY FRONT END WILL BE UTILIZED YOU WILL NOT BE COMMUNICATING WITH YOUR DB OR BACKEND.
 

### Authors

* **Landon Harvey** 

### Acknowledgments

Where I first learned and for a more comprehensive guide check out `jonashackt` github
* [jonashackt](https://github.com/jonashackt/spring-boot-vuejs#in-search-of-a-new-web-frontend-framework-after-2-years-of-absence)
