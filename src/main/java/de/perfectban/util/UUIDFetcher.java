package de.perfectban.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class UUIDFetcher
{
    private static final HashMap<String, UUID> CACHE = new HashMap<>();
    private static final String UUID_URL = "https://minecraft-api.com/api/uuid/%s";

    public static void getUUIDbyName(String name, Consumer<UUID> callback) {
        if(CACHE.containsKey(name.toUpperCase())) {
            callback.accept(CACHE.get(name.toUpperCase()));
            return;
        }

        Executors.newCachedThreadPool().execute(() -> {
            try {
                String trimmedUUID = new HttpGetRequest(String.format(UUID_URL, name))
                        .send()
                        .getAsString();

                if(trimmedUUID.equalsIgnoreCase("Player not found !")){
                    callback.accept(null);
                    return;
                }

                UUID uuid = fromTrimmed(trimmedUUID);

                CACHE.put(name.toUpperCase(), uuid);
                callback.accept(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private static UUID fromTrimmed(String uuid) {
        Pattern pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
        return UUID.fromString(pattern.matcher(uuid.replace("-", ""))
                .replaceAll("$1-$2-$3-$4-$5"));
    }
}
