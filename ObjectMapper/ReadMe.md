
**Project** - **ObjectMapper **

**Question - Write a function to parse any valid json string into a corresponding Object, List, or Map object. You can use C,C++, Java, Scala, Kotlin, Python, Node. Note that the integer and floating point should be arbitrary precision.**

**Language Used** - Java

**FrameWork Used** - Spring Framework(SpringBoot)

Hi, Here I have created an API to parse Json String to Object and read different type of values String, Float, Nested Strings etc.

API curl - 



```sh
 curl --location 'http://localhost:8080/api/json/parse' \
--header 'Content-Type: application/json' \
--data '{
    "serialNumber": 1.0,
    "Year": 2024,
  "libraryname": "My Library",
  "mymusic": [
    {
      "Artist Name": "Arijit Singh",
      "Song Name": "Chaleya"
    },
    {
      "Artist Name": "Mohit Chauhan",
      "Song Name": "Tum se hi"
    },
    {
      "Artist Name": "Jagjit Singh",
      "Song Name": "Tum jo Itna muskura rahe ho"
    }
  ]
}'
