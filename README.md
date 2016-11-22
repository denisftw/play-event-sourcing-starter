# Practical Event Sourcing with Scala

This is the activator template of sample event sourcing application that we will be building over the course of the [Practical Event Sourcing with Scala](https://leanpub.com/practical-event-sourcing-with-scala) ebook I'm currently writing.

## Description

The template is loosely based on the sample application built during the course of [Modern Web Development with Scala](https://leanpub.com/modern-web-development-with-scala). In particular:

* The dependency injection is based on MacWire
* A simple authentication mechanism is based on cookies and Play API
* A database connectivity module is based on ScalikeJDBC and uses PostgreSQL

The frontend sources are separated from the backend and reside in the `ui` directory. All necessary dependencies are already included in the `package.json`. These include:

* Webpack as the frontend build tool/module bundler
* Babel transpiler for using EcmaScript 6 (arrows, classes, `let`, `const`)
* React, React Router and Redux
* Axios as the frontend HTTP client

All styles are included so that the application looks decent enough from the very beginning. The complete SASS source of Bootstrap (together with Glyphicons) is included for easy use.

## Versions used

The code uses Play 2.5 and Scala 2.11.8 along with sbt 0.13.11.
