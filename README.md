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

