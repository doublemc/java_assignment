Application uses gradle for build process. Can be started via `./gradlew bootRun` command.
Application server starts on default port 8080.

Endpoint:
`GET @ /api/hotels/room-occupancy`

Request params:
`availableEconomyRooms` - if not passed default 0 will be used
`availablePremiumRooms`  - if not passed default 0 will be used

Above endpoint returns room occupancy calculation in JSON format.

Example:
`GET @ /api/hotels/room-occupancy?availablePremiumRooms=7&availableEconomyRooms=1`
will return JSON:

`{
  "economyRoomUsage": {
    "totalSpending": 45,
    "usedRooms": 1
  },
  "premiumRoomUsage": {
    "totalSpending": 1153,
    "usedRooms": 7
  }
}`

