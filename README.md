# bidding-service
A code challenge task for Yieldlab. The idea of the challenge is to build a simplified version of a bidding system that connects suppliers (those who
have space to show ads, e.g. on their websites) to bidders (those who actually
want to show ads)

## Prerequisites
To run the test bidders and scripts you will need:
- Docker ([official installation docs][install-docker])
- A shell (or you'll need to carry out the tests manually)
- `diff` (e.g. from [GNU Diffutils][diffutils] package)
- `curl` ([official download link][curl-dl])

[install-docker]: https://docs.docker.com/engine/installation/
[diffutils]: https://www.gnu.org/software/diffutils/
[curl-dl]: https://curl.haxx.se/download.html

## The task

Build a bidding system behaving as following:

For every incoming request as described in [1], send out bid requests as
described in [2] to a configurable number of bidders [5]. Responses from these
bidders as described in [3] must be processed. The highest bidder wins, and
payload is sent out as described in [4].

Incoming and outgoing communication is to be done over HTTP. Message formats
are described below.

[1]: #1-incoming-requests
[2]: #2-bid-requests
[3]: #3-bid-response
[4]: #4-auction-response
[5]: #5-configuration

### 1 Incoming Requests

The application must listen to incoming HTTP requests on port 8080.

An incoming request is of the following format:

    http://localhost:8080/[id]?[key=value,...]

The URL will contain an ID to identify the ad for the auction, and a number of
query-parameters.

### 2 Bid Requests

The application must forward incoming bid requests by sending a corresponding
HTTP POST request to each of the configured bidders with the body in the
following JSON format:

```json
{
	"id": $id,
	"attributes" : {
		"$key": "$value",
		...
	}
}
```

The property `attributes` must contain all incoming query-parameters.
Multi-value parameters need not be supported.

### 3 Bid Response

The bidders' response will contain details of the bid(offered price), with `id` and `bid`
values in a numeric format:

```json
{
	"id" : $id,
	"bid": bid,
	"content": "the string to deliver as a response"
}
```

### 4 Auction Response

The response for the auction must be the `content` property of the winning bid,
with some tags that can be mentioned in the content replaced with respective values.

For now, only `$price$` must be supported, denoting the final price of the bid.

Example:


Following bid responses:
```json
{
	"id" : 123,
	"bid": 750,
	"content": "a:$price$"
}
```
and

```json
{
	"id" : 123,
	"bid": 500,
	"content": "b:$price$"
}	
```
will produce auction response as string:
a:750

### 5 Configuration

The application should have means to accept accept a number of configuration
parameters. For the scope of this task, only one parameter is to be supported:

| Parameter | Meaning                                                  |
|-----------|----------------------------------------------------------|
| `bidders` | a comma-separated list of URLs denoting bidder endpoints |


## How to run

### Build and Deployment
Make sure you are in the project root directory.

#### Using docker:
1. Run `docker build -t yieldlab/bidding-service .`

3. Make sure you have created the image in above step by running:
   `docker images yieldlab/bidding-service`

4. To run the container execute:
   `docker run --network="host" yieldlab/bidding-service`\
   Here container's `--network` is set to `"host"` in order to allow the application access to test bidders via localhost

#### Manual approach:
1. Run `./gradlew bootJar`
2.  Execute: `java -jar build/libs/bidding-service-0.0.1-SNAPSHOT.jar`


### Test environment
To start the test environment, either use the script `test-setup.sh` or run the
following commands one after the other from your shell:

```sh
docker run --rm -e server.port=8081 -e biddingTrigger=a -e initial=150 -p 8081:8081 yieldlab/recruiting-test-bidder &
docker run --rm -e server.port=8082 -e biddingTrigger=b -e initial=250 -p 8082:8082 yieldlab/recruiting-test-bidder &
docker run --rm -e server.port=8083 -e biddingTrigger=c -e initial=500 -p 8083:8083 yieldlab/recruiting-test-bidder &
```
This will set up three bidders on localhost, opening ports 8081, 8082 and 8083.

### Run the test
To run the test, execute the shell script `run-test.sh`. The script expects
your application to listen on `localhost:8080`. It will issue a number of bid
requests to your application and verify the responses to these requests. If
your application doesn't respond correctly, it will print out a diff between
the expected and the actual results.

