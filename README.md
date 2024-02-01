# ![Poster](doc/img/logo.png)

Poster is a Single Page Application (SPA) developed with React on the frontend and a backend implemented in Spring Boot. This platform enables users to create unique profiles with login credentials, password, name, and avatar. Users can generate posts that are displayed on the platform's feed, including titles, short descriptions, URLs, categories, prices, and images. Additionally, search, filtering, and sorting functionalities for posts have been implemented. Users also have the ability to create two types of posts: offers and coupons, with an additional field for associated coupon codes.

## Objectives 🎯

* __Develop an Interactive Application__: Create an application that allows users to seamlessly create profiles and posts. The user-friendly interface should enhance the overall user experience.
* __Implement Post Management Functionalities__: Integrate features such as search, filtering, and sorting to provide users with efficient ways to manage and explore posts on the platform.
* __Enable Offer and Coupon Creation__: Empower users to create and manage both offers and coupons. Include a dedicated field for coupon codes, enhancing the versatility of the platform.
* __Facilitate User Ratings__: Implement a rating system for offers and coupons, enabling users to provide feedback and share their experiences. This enhances the credibility of the platform.
* __Provide Interaction Tools__: Incorporate tools such as comments on posts and notifications to foster interaction and engagement among users.

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

## Other Documentation

* [PDF Report](doc/report.pdf)
* [Model Documentation](doc/model.md)
* [REST Documentation](doc/rest.md)
* [Spring-Boot](https://spring.io/projects/spring-boot) - Backend
* [React](https://es.reactjs.org/) - User Interface
* [Maven](https://maven.apache.org/) & [yarn](https://yarnpkg.com/) - Project Management
* [Kubernetes](https://kubernetes.io/) & [Docker](https://www.docker.com/) - Deployment

## Authors ✒️

* **Sergio Goyanes Legazpi**
* **Patricia Mato Miragaya**
* **Martín Regueiro Golán**
* **Daniel Jueguen Pérez**
* **Alejandro Buján Pampín**
* **Alfredo Javier Freire Bouzas**

©2023
