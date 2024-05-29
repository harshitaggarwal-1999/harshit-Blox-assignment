package com.example.api.ObjectMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonParserService {
    private final ObjectMapper objectMapper;

    public JsonParserService() {
        objectMapper = new ObjectMapper();
    }

    public Library parseJson(String jsonString) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonString);
        String serialNumber = rootNode.path("serialNumber").asText();
        float srNum = Float.valueOf(serialNumber);
        String year = rootNode.path("Year").asText();
        int yr = Integer.parseInt(year);


        String libraryName = rootNode.path("libraryname").asText();
        List<Music> musicList = new ArrayList<>();

        JsonNode myMusicNode = rootNode.path("mymusic");
        if (myMusicNode.isArray()) {
            for (JsonNode musicNode : myMusicNode) {
                String artistName = musicNode.path("Artist Name").asText();
                String songName = musicNode.path("Song Name").asText();
                musicList.add(new Music(artistName, songName));
            }
        }

        return new Library(srNum, yr, libraryName, musicList);
    }
}
