
**Project** - **ObjectMapper **

**Question** - There is an API that one must call to get data. The trouble is it will not let you cross the
limit of call - say 15 calls per minute. If you cross the limit, the system penalizes you by one
additional minute of penalty where you can not make any call. Here is how the API looks
like: function string call_me(string input).
Propose a solution by which:
1. You would be able to use the API within the safe limit.
2. What happens if you are supposed to call the API 20 times per minute? Is there
any way to accomplish this?
3. If you were the API designer, what would you do to implement this behavior?**

**Language Used** - Java

**FrameWork Used** - Spring Framework(SpringBoot), Bucket4j

 In this Project I have created 2 APIs 
1. 1st is the random number generator and 
2. 2nd is the random String generator,

in which both of them have Rate Limiter implemented in themselves . here I have implemented using Token Bucket algorithm using bucket4j java library to implement server side rate limiter and ,
say a person wants to Hit the APIs he can make upto 10 calls per minute and the output will be shown to the user according to the API hit

Secondly, If the User hit more than 10 API calls(say random Number generator) per minute at 11th call the user won't be able to make 11th call for that particular API and will be waiting for **Time to refill the bucket again + Penalty time(1 Minute) this is for random Number and for randomString there is no penalty**.

Thirdly, Being an API designer I have implemented Token Bucket algorithm using bucket4j Java Library. Also for this I have made controller for the API. where I have described EndPoints. I have introduced Config folder and Interceptor folders so as to config the headers and the request coming and response going outside.

**How To Start The Application :-**
1. Fork/Clone this repository.
2. Install java 21
3. Extract the file on the desired path
4. Open the project on IDE
5. Load the maven and wait till the indexing happens
6. Just run the application
7. For Testing copy the curLs below and import it on the postman
8. Hit the APIs multiple time after asll token in the bucket exhausted you'll see the error 429(TOO_MANY_REQUESTS)


**Note**: to check the working open the endpoint on the browser and inspect the file when it is giving error

you'll Notice headers **X-rate-limit : exceeded** ,
 also **X-rate-limit-token-left: (no of tokens left(id not exhausted))** also,
  **X-retry-after-seconds: (seconds left if all the token have exhausted)**

API curls - 

Random Number
   
    curl --location 'http://localhost:8080/api1/random-number'
Random String

    curl --location 'http://localhost:8080/api2/random-string'

Cases Handled:

1. if one APi has exhausted it's token then it other API should still work correctly even even if the first token has already been exhausted.
2. token should refill after each and every interval without any delay


