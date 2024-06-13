# Code Challenge Yape – Mobile Developer

## Introducción
Este proyecto fue desarrollado como parte de un code challenge para una posición de desarrollador Android. La aplicación tiene como objetivo principal mostrar una lista de recetas de cocina obtenidas desde una API externa, permitiendo al usuario ver detalles de cada receta seleccionada, incluyendo su origen geográfico mediante un mapa interactivo.

## Arquitectura y Patrones Utilizados
Para asegurar un desarrollo estructurado y mantenible, se implementó Clean Architecture junto con el patrón MVVM (Model-View-ViewModel). 
Decidi utilizar Clean Architecture, debido a que si este proyecto escala, podrimos tener los beneficios de este principio. Y que, conjuntamente a MVVM tenemos una arquitectura solida, facil de mantener, de extender y de testear.
A continuación, se describen brevemente cada uno de estos componentes:

### Clean Architecture
Divide el proyecto en capas bien definidas (presentation, domain, data), separando responsabilidades y facilitando la prueba y la evolución independiente de cada capa.

### MVVM (Model-View-ViewModel)
Separación clara de la lógica de negocio de la interfaz de usuario. El ViewModel actúa como intermediario entre los datos y la interfaz de usuario, utilizando componentes como Flow y StateFlow para una programación reactiva.
En el viewmodel se emite en un unico StateFlow el resultado de la lista completa de recetas o ademas se emite el resultado del filtro. Por lo que con unico StateFlow manejamos de manera reactiva los cambios de la vista.

[![N|Solid](https://miro.medium.com/v2/resize:fit:640/format:webp/0*mwVSPyoOCFtSufKh.png)](https://nodesource.com/products/nsolid)

## Tecnologías y Bibliotecas Clave
**Retrofit:** Biblioteca de cliente HTTP para comunicarse con la API de recetas.
**Hilt:** Biblioteca recomendada por Google para la inyección de dependencias en Android.
**Navigation Component:** Utilizado para la navegación entre las tres pantallas principales de la aplicación.
**Coroutines:** Para el manejo de operaciones asincrónicas de manera sencilla y eficiente.
**Flow y StateFlow:** Parte de la API de Kotlin Coroutines que facilita la programación reactiva. Flow es una construcción para emitir valores de manera asincrónica y continuamente, mientras que StateFlow es un contenedor de flujo de datos con un valor que puede ser observado y actualizado de manera reactiva.

Se incluyeron test para verificar el correcto funcionamiento del ViewModel. Para ello se creo un fake del repository para poder simular el llamado a la api.
Estas pruebas garantizan que los datos se carguen correctamente desde la API y se presenten adecuadamente en la interfaz de usuario, ademas que el filtro funciona correctamente.

### API Utilizada
Se utilizo la api Tasty (https://rapidapi.com/apidojo/api/tasty/), que ya nos provee una lista de recetas. Ella ademas nos provee el resultado paginado. En esta oportunidad por cuestiones de tiempo, no se hizo el paginado del resultado.
Cada receta posee muchisima informacion la cual le daria un valor agregado, pero por cuestiones de tiempo como mencione anteriormente, no se muestran en la app.

## La aplicacion consta de 3 fragments
Home Screen: Lista y buscador de recetas. Como la lista de recetas que obtengo de la api no tenia una lista de ingrediente, se decidio utilizar la lista instructions para el filtrado.
Detail Screen: Detalles de la receta seleccionada.
Map Screen: Mapa geolocalización del origen de la receta. Cabe aclarar que el pin es colocado en un location harcodeado, debido a que la API no nos provee un location.
