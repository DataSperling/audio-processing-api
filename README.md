# GET Contract

#### Request Single By ID
    URI: /waveforms/{id}
    HTTP Verb: GET
    Body: None

#### Response
    200 OK              user authorized and waveform retrieved
    403 UNAUTHORIZED    unauthenticated or unauthorized user
    404 NOT FOUND       user authenticated and authorized but waveform not found

#### Example Response Body
    {
      "id" : 67,
      "recDate" : "15-07-2019",
      "location" : "windermere"
    }
##
#### Request Page of 10 Entries in Reverse Chronological Order
    URI: /waveforms
    HTTP Verb: GET
    Body: None

#### Response (Paging Data Omitted for Brevity)
    

# POST Contract

#### Request
    URI: /waveforms?page=1
    HTTP Verb: POST
    Body:
    {
      "recDate" : "15-07-2017",
      "location" : "windermere"
    }

#### Response
    Status Code: 201 CREATED
    Header: Location/waveforms/67
    











