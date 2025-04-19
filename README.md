# Weather stations system - Backend

This project contains backend for weather stations system. The run project can store measurements, weather stations and live updates via WebsocketAPI. It also provides basic security with users divided onto different roles.

## Technologies

The whole project is based on Java Spring Boot technology. It uses MySQL as a primary database and both containers can be run in docker compose with predefined file.

## Initialization

There are two initialization scripts. For windows:

```
.\init.ps1
```

For Bash:

```
.\init.sh
```

Those scripts are kicking off multiple initialization steps:

- Generating secrets and other variables for MySQL database
- Saving those secrets onto .env file
- Launching the Docker Compose project

## Usage

After initialization, a file named `.admin_credentials` will be created in main root directory. This file contains username and password for admin account.

NOTE: This file should be deleted immediately after saving credentials outside the application.

The application runs on docker compose, so putting it down and up is as simple as:

```
docker compose down
```

and

```
docker compose up # (with --build optionaly)
```

Secured endpoints are using Basic Authentication to authenticate the user. Some endpoint can be reached only if the user is authenticated as admin, some are available only for weather stations and the rest is publicly available.

## Endpoints

- (admin restricted) POST /weather/station - adds a new weather station to database

Example body:

```
{
  "name": "field_1",
  "sensors_list": "temperature,pressure",
  "app_user": {
    "username": "user",
    "password": "password"
  }
}
```

- GET /weather/station - returns list of stations

Example response:

```
[ 
    {
        "id": 1,
        "name": "field_1"
    }
]
```

- (admin restricted) DELETE /weather/station/{id} - Deletes weather station by it's ID

- (weather station restricted) POST /weather/measure - Adds a new measurement to the database

- GET /weather/measure - Returns measurements for a station in a period of time. Query params:
  - (Optional) `from` - timestamp starting the search period of time. Format: `yyyy-MM-ff HH:mm:ss.SSS`
  - (Optional) `to` - timestamp finishing the search period of time. Format: `yyyy-MM-ff HH:mm:ss.SSS`
  - (Optional) `station_id` - ID of station

Example response:

```
[ 
    {
        "measuredQuantityName": "temperature",
        "value": 20.0,
        "timestamp": "2025-04-19T20:43:33.501+00:00",
        "unit": "C",
        "shortWeatherStationDTO": {
            "id": 1,
            "name": "field_1"
        }
    }
]
```

- (admin restricted) DELETE /weather/measure/{id} - Deletes a weather station with matching ID

## WebSocketAPI

To provide functionality of live updates, the WebsocketAPI endpoint is exposed:

```
/ws-weather
```

This endpoint is listening on topic `/topic/measurements`

Example response:

```
[ 
    {
        "measuredQuantityName": "temperature",
        "value": 20.0,
        "timestamp": "2025-04-19T20:43:33.501+00:00",
        "unit": "C",
        "shortWeatherStationDTO": {
            "id": 1,
            "name": "field_1"
        }
    }
]
```