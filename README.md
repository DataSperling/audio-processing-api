# GET Contract

#### Request
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

# POST Contract

#### Request
    URI: /waveforms/
    HTTP Verb: POST
    Body:
    {
      "recDate" : "15-07-2017",
      "location" : "windermere"
    }

#### Response
    Status Code: 201 CREATED
    Header: Location/waveforms/67
    











