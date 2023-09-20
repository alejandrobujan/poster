# Poster

## Documentation

- [Backend Documentation](doc/backend.md)

## Starting 🚀

These instructions will allow you to get a copy of the project running on your local machine for development and testing purposes.

### Requirements 📋

Software and tools you will need to run this project:
* Java 17 (_tested version_)
* Node v18.12.0 (_tested version_)
* yarn 1.22.19 (_tested version_)
* Eclipse
* VS Code (_Reccomended for frontend developement_)

### Local deployment 🔧

There are two ways to work in your local environment:

1. Spring-Boot app in port 8080 and React app in port 3000 (_Recommended for development_)
2. Both apps running in port 8080

Let's see how to work with each option.

_Note: both frontend and backend are configured to start on path /projectname. Moreover frontend subpaths follow this pattern: /projectname/#/subpath (due to deployment requirements it is necessary to use HashRouter instead of BrowserRouter)_

**1. Access frontend in port 3030 (_Reccomended for development_)**

* Install your maven project
    ```
    mvn clean install
    ```
* Start backend sever
    ```
    mvn spring-boot:run
    ```
* Install your frontend app (not necessary if mvn install was executed correctly)
    ```
    yarn install
    ```
* Start react app
    ```
    yarn start
    ```

Now you can test your app in http://localhost:3000/poster

**2. Access frontend in port 8080**

* Install your maven project
    ```
    mvn clean install
    ```
* Start the application
    ```
    mvn spring-boot:run
    ```
Thanks to the provided plugin configuration you can access your React app directly on http://localhost:8080/poster

## Deployment 📦
Production deploy will be made via Jenkins to Kubernetes.

Steps to follow:
1. Build the project
    ```
    mvn clean install
    ```
2. Create docker image
    ```
    mvn k8s:build
    ```
3. Undeploy Kubernetes app (_only needed if app is already deployed_)
    ```
    mvn k8s:undeploy
    ```
4. Push image to GitLab registry
    ```
    mvn k8s:push
    ```
5. Deploy pushed image to Kubernetes
    ```
    # Generate deploy files
    mvn k8s:resource
    # Deploy to Kubernetes
    mvn k8s:deploy
    ```

## Built with 🛠️

* [Spring-Boot](https://spring.io/projects/spring-boot) - Backend
* [React](https://es.reactjs.org/) - User Interface
* [Maven](https://maven.apache.org/) & [yarn](https://yarnpkg.com/) - Project Management
* [Kubernetes](https://kubernetes.io/) & [Docker](https://www.docker.com/) - Deployment

## Authors ✒️

* **Jorge Gabín**
* **Alfonso Landín**
* **Javier Parapar**

---
IRLab ©2021
