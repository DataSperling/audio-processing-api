# API Contracts

## Request
    URI: /waveforms/{id}
    HTTP Verb: GET
    Body: None

## Response
    200 OK              user authorized and waveform retrieved
    403 UNAUTHORIZED    unauthenticated or unauthorized user
    404 NOT FOUND       user authenticated and authorized but waveform not found

## Example Response Body
    `{
       "id" : 67,
        "date" : "15-07-2019",
        "location" : "windermere"
     }`
    











