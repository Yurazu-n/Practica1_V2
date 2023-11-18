# Practica 1.V2
##  __DACD -Desarollo de Aplicaciones para Ciencia de Datos__ 
==
- _Course:_ 2nd Year
- _Titulation:_ Engineering & Data Science
- _School:_ School of Informatic Engineering and Mathematics
- _College:_ ULPGC - University of Las Palmas of Gran Canaria

#
#
#
-------------

## Functionality resume 

The code of this practice takes a path and an apikey from the OpenWeatherMap service which provides 
the weather predictions of the next 5 days in a certain location in a 3 hour lap, in order to get the predictions done 
at 12:00 to one city of each Canary Island. Afterwards, it takes the obtain information and save it on a SQLite local
database created automatically at the given path to save and update every 6 hours the weather predicctions of 
every island

## How to use

For the corrects usage of this app it is requiered to have an apiKey for the OpenWeather [5 day Weather Forecast](https://openweathermap.org/forecast5) as well 
as a path to a local .db database or just the path were the user wants it to be located (if will be created automatically like said)
After getting the conection to the database and creating/updating all the island tables, the app will provide the option to consult
an islands information or to exit the textual interface
The resoulting table will look like this:

| temperature | humidity | windSpeed | clouds | precipitationProb | location | instant
| ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| 289.1 | 75 | 3.42|0|0|GranCanaria| 2023-11-19
| 283.0 | 68 | 4.63|89|0|GranCanaria|2023-11-20
| 122.1 | 53 | 3.8 |55|1|GranCanaria|2023-11-21
| 290.09 | 50 | 3.68|72|0|GranCanaria|2023-11-22
|300.11  | 45 | 2.70|80|1|GranCanaria|2023-11-23


## Resources

The main resources used to the correct development of the app were

- IntelliJ Idea as programming environment
- Gson Library on Maven for Json treatment
- JDBC library on Maven for SQL sentences
- OkHttp library on Maven for Http requests
- OpenWeatherMap to get the predictions
- Concurrent Java library to do the task every 6 hours
- Git as version controler

## Development & Design

For the Organization and Design of the app the followed styles were MVC model and the Hexagonal Architecture.
The code is seperated into 3 functionallity layers, model, view and controller. Separating the code involving the textual 
user interface from the code in charge of processing the information and the ones that provide the structure of that data.
It also has many functions and parts that works as adapters for the upcoming data, and ports that gives it to the Resorces for
its processing.
All this classes are related like this:

![Captura de pantalla 2023-11-18 195456](https://github.com/Yurazu-n/Practica1_V2/assets/90729313/1a34dbb5-5622-4a5a-af9a-258f351a56dc)

