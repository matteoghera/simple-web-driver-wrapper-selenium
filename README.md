# simple-web-driver-wrapper-selenium
This is a simple web-driver wrapper, written in Java, that uses Selenium. To test it, I use a very simple webpage which can be found at this <a href="https://web.ics.purdue.edu/~gchopra/class/public/pages/webdesign/05_simple.html"> link </a>.

## Docker
I used a Docker Standalone image of Selenium for Chrome. Before running the JUnit tests, please run the following command:

```powershell
docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-chrome:4.0.0-20211013
```

## Maven
I provide a pre-set Maven configuration that starts the Docker container with Selenium, runs all tests and then stops the running container. To use Maven configuration, run the following command inside the pom.xml file folder:

```powershell
mvn clean verify
```