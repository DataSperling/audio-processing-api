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
#### Request Page of 10 Entries Sorted in Reverse Chronological
    URI: /waveforms?page=0&size=10&sort=recDate,desc
    HTTP Verb: GET
    Body: None

#### Example Response Body (Paging Data Omitted for Brevity)
    [        
        { "id" :  20, "recDate" : "2021-06-12", "location" : "biscay" },
        { "id" :  17, "recDate" : "2017-03-5", "location" : "windermere" },
        { "id" :  18, "recDate" : "2015-01-28", "location" : "monfragüe" },
        { "id" :  19, "recDate" : "2010-07-11", "location" : "comacchio" }        
    ]
    

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
    











