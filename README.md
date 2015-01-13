# java-web-planet

**java-web-planet** strives to be a simple library for web MVC development. 

It is abbreviated "**jawn**", which is a homophone of "yawn", as this project is supposed to be so simple to use
that you can do it almost do it your sleep.

## Personal motivation
I was tired of the limits and troublesome HTML tags of standard JSPs,
and not satisfied with existing frameworks, so I set down in the endeavour of 
combining technologies to get close to a full stack web development 
platform with all the features I thought a web framework should have:

* **Fewer extensive XML files for configuration**. Preferring to get as much into the code as possible.
* **Keep close to Java standards**. The framework is useless if it is just another language you have to learn,
and is not something you can use in an existing environment/container.
* **Commonly needed functionality in one place**. I often have the need for image manipulation, JSON marshalling, AJAX, and many other things.
* **Extremely close to full stack web development**. The development has to be seamless, and should possible to 
deploy the service either as a self-contained service or as a part of a container.


## Introduction
jawn utilises the typical MVC structure, where all logic is handled in the controllers, controllers
create models, and models are sent to the view.





## Documentation
* [Getting started](docs/getting_started)
* [Structure of a jawn project](docs/structure_of_jawn_project)
* [Controllers](docs/controllers)
* [Views](docs/views)
* [Image manipulation](docs/imagemanipulation)
* [Environments](docs/environments)
* [Application wide context](docs/appcontext)

## Get it

### Maven
```xml
<dependency>
  <groupId>net.javapla.jawn</groupId>
  <artifactId>jawn</artifactId>
  <version>0.1.1</version>
</dependency>
```

### Gradle
```groovy
compile 'net.javapla.jawn:jawn:0.1.1'
```



## Acknowledgement
This started out as a fork of [ActiveWeb](https://github.com/javalite/activeweb),
but it has moved pretty far away from the original way of thinking of ActiveWeb
when it comes to handling URLs, writing controllers and the inner workings.
Also the notion of setting a controller to be RESTful has been removed.

When this is said, however, much of the usage is the same, so is should be
seamless to migrate from one to the other.
