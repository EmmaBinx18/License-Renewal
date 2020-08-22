# Vehicle License Renewal API
This API can be used to renew a South African vehicle license, as well as perform other functions surrounding the domain of License Renewal. All responses and request bodies my be in a valid JSON format. Standard HTTP responses will be returned, and appropriate error codes and responses will be returned where necessary. JWT tokens are used for authorization purposes and you will need to be authenticated before you will be allowed to use the API.

Swaggar Documentation:

## Response Codes 
### Response Codes
```
200: Success
201: Successfully Created Resource
400: Bad request
401: Unauthorized
404: Cannot be found
405: Method not allowed
422: Unprocessable Entity 
50X: Server Error
```
### Example Error Message
```json
{
  "error": {
    "message": "Unauthorized",
    "code": 401,
  }
}
```

## Login
**You send:**  Your  login credentials.
**You get:** A `JWT-Token` with which you can make further API calls.

**Request:**
```json
POST /authenticate HTTP/1.1
Accept: application/json
Date: Mon, 17 Aug 2020 09:32:01 GMT
Content-Type: application/json
Server: Heroku-server
{
    "username": "foo",
    "password": "1234567" 
}
```
**Successful Response:**
```json
HTTP/1.1 200 OK
Date: Mon, 17 Aug 2020 09:32:12 GMT
Content-Type: application/json
Content-Length: 113
Server: Heroku-server
{
   "token": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
}
```

## Additional Information
### Helpful References:

**Identification Types:**
```
1. Traffic Register Number
2. RSA ID
3. Foreign Id
4. Business Register Number
```

**License Types:**   
```
1. Code 1
2. Code 2
3. Code 3
```

**License Status Options:**
```
1. Pending Approval
2. Active
3. Suspended
```

**Owner Types:**
```
1. Owner
2. Organisation Proxy
3. Organisation Representative
```

**Renewal Actions:**
```
1. Renewal
2. Suspended
```

**Vehicle Types:**
```
1. Drawn
2. Left Steering
3. Right Steering
4. Middle Steering
```

