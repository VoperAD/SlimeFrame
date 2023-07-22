package me.voper.slimeframe.data.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.UUID;

public class WarframePlayerAdapter extends TypeAdapter<WarframePlayer> {

    @Override
    public void write(JsonWriter jsonWriter, WarframePlayer warframePlayer) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Player");
        jsonWriter.value(warframePlayer.getPlayerName());
        jsonWriter.name("UUID");
        jsonWriter.value(warframePlayer.getUuid().toString());
        jsonWriter.name("masteryLevel");
        jsonWriter.value(warframePlayer.getMasteryLevel());
        jsonWriter.name("masteryExperience");
        jsonWriter.value(warframePlayer.getMasteryExp());
        jsonWriter.endObject();
    }

    @Override
    public WarframePlayer read(JsonReader jsonReader) throws IOException {
        String playerName = null;
        UUID uuid = null;
        int masteryLevel = 0, masteryXP = 0;

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case "Player" -> playerName = jsonReader.nextString();
                case "UUID" -> uuid = UUID.fromString(jsonReader.nextString());
                case "masteryLevel" -> masteryLevel = jsonReader.nextInt();
                case "masteryExperience" -> masteryXP = jsonReader.nextInt();
                default -> jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        return new WarframePlayer(uuid, playerName, masteryLevel, masteryXP);
    }
}
