# Space Mining App

La **Space Mining App** es una aplicación móvil desarrollada en **Android Studio** utilizando el lenguaje de programación **Kotlin**. Su objetivo principal es visualizar datos de objetos en órbita y proporcionar una interfaz intuitiva para que los usuarios comprendan este fascinante mundo. 
Dicha app utiliza datos de objetos en órbita que se procesan en un servidor, el objetivo principal es que por medio de visualizaciones, una persona que no se encuentre involucrada en este mundo pueda entender fácilmente del tema, adicionalmente se realiza la implementación de un modelo de regresión lineal, cuya variable a predecir es el periodo de un satélite, mientras que las variables independientes son apogee y perigee. A demás se cuenta con una pantalla que funciona como un diccionario con conceptos y definiciones relacionados con todo este ámbito.
A continuación, se detallan los aspectos clave de la aplicación:

![](app/src/main/res/mipmap-xxxhdpi/ic_launcher_foreground.webp)

## Descripción

- **Visualización de Datos Espaciales**: Específicamente, se traen las imágenes que se generan en nuestro api (alojado en el proyecto [space-mining-api](https://github.com/MarcosDPG/space-mining-api.git)) y se consumen directamente desde la app de manera tal que el usuario simplemente tenga que elegir qué tipo de gráfico quiere ver a su vez que variables. Todo esto haciendo uso de la librería de **Glide** que nos proporciona el mismo Android studio

- **Modelo de Regresión Lineal**: Se entrenó directamente dentro de Python haciendo uso de los mismos datos espaciales cuyas graficas mostramos, luego de obtener los valores de coeficientes e intercepto, los introducimos directamente dentro del código de la aplicación, cabe resaltar que el modelo cuenta con una precisión superior al 95% con valores de apogeo y perigeo que no sean extremadamente grandes. Para el usuario se creó una interfaz sencilla de usar en la cual debe ingresar únicamente los valores correspondientes al apogeo y perigeo, y con tan solo un click puede obtener una predicción altamente precisa.

- **Diccionario Espacial**: Gracias al web scraping que se realiza desde nuestro api, podemos implementar dentro de nuestra app un diccionario de palabras relacionadas en el ámbito espacial, esto por medio de la librería de retrofit que se nos brinda en el ambiente de Android studio. El apartado grafico que hace uso de dicha información es altamente intuitivo, inclusive permitiéndole al usuario buscar conceptos muy puntuales dentro de una barra de búsqueda. 

## Capturas de Pantalla

Aquí tienes algunas capturas de pantalla de la aplicación:

1. **Visualización de Datos**:

   <img src="https://github.com/codeuler/SpaceMiningApp/assets/159579558/de283402-cfd4-490b-b926-a5a8d5efc9e2" alt="Descripción de la imagen" width="400" height="300">

2. **Predicción del Período**:

   <img src="https://github.com/codeuler/SpaceMiningApp/assets/159579558/c4b77be1-2d94-4e36-b2e7-6e090d0936d7" alt="Descripción de la imagen" width="400" height="830">

3. **Diccionario Espacial**:

    <img src="https://github.com/codeuler/SpaceMiningApp/assets/159579558/5c4474bc-78ec-4cad-b314-e7490565c5bc" alt="Descripción de la imagen" width="400" height="830">

## Uso

1. Abre la aplicación en tu dispositivo Android.
2. Observa diferentes objetos que se encuentran en órbita actulamente.
3. Ingresa al apartado de visualización donde te podrás encontrar con una gran variedad de graficas las cuales puedes observar a detalle
4. Haz clic en el botón de predicción para obtener el período estimado del satélite dentro de la pantalla de predicciones
5. ¿No entiendes un concepto? Tranquilo, con nuestro lexicon cósmico quedarás al día con toda esta nomenclatura tan interesante
