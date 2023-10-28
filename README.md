## GET

#### Request Single By ID
    URI: /waveforms/{id}
    Http Verb: GET
    Body: (empty))

#### Response Codes
    200 OK              principal authorized and record retrieved
    403 UNAUTHORIZED    principal unauthenticated or unauthorized
    404 NOT FOUND       principal authenticated and authorized but not owner of record
    404 NOT FOUND       principal authenticated and authorized but record not found

#### Example Response Body
    {
      "id" : 67,
      "recDate" : "15-07-2019",
      "location" : "windermere"
    }
###
#### Request Page of 10 Entries Sorted in Reverse Chronological Order
    URI: /waveforms?page=0&size=10&sort=recDate,desc
    Http Verb: GET
    Body: (empty))

#### Response Codes
    200 OK              user authorized and record retrieved
    403 UNAUTHORIZED    unauthenticated or unauthorized user

#### Example Response Body (Paging Data Omitted for Brevity)
    [        
        { "id" :  20, "recDate" : "2021-06-12", "location" : "biscay" },
        { "id" :  17, "recDate" : "2017-03-5", "location" : "windermere" },
        { "id" :  18, "recDate" : "2015-01-28", "location" : "monfragüe" },
        { "id" :  19, "recDate" : "2010-07-11", "location" : "comacchio" }        
    ]
    
##
## POST (POST Implemented as Create, Require Resource URI Returned)
#### Request Body
    URI: /waveforms?page=1
    Http Verb: POST
    Body:
    {
      "recDate" : "15-07-2017",
      "location" : "windermere"
    }

#### Response Code
    201 CREATED         new record created

#### Example Response Body
    Header: Location/waveforms/67
    Body:
    {
      "recDate" : "15-07-2017",
      "location" : "windermere"
    }
##

## PUT (PUT Implemented as Update, PATCH Not Implemented)
#### Request Body
    URI: /waveforms/{id}
    Http Verb: PUT
    Body:
    {
      "recDate" : "15-07-2017",
      "location" : "windermere"
    }

#### Response Code
    204 NO CONTENT      recrod updated
    403 UNAUTHORIZED    principal unauthenticated or unauthorized
    404 NOT FOUND       principal authenticated and authorized but record not found

#### Response Body
    Body: (empty)

##
## DELETE

#### Request Body
    URI: /waveforms/{id}
    Http Verb: DELETE    
    Body: (empty)

#### Response Code
    204 NO CONTENT      princiapl authorized and record record exists and successfully deleted
    404 NOT FOUND       princiapl authorized but record does not exist (non-existant ID sent)
    404 NOT FOUND       record exists but principal not owner

#### Response Body
    Body: (empty)
    











