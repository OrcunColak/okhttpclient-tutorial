package colak;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class AsyncPostTest {

    private final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        AsyncPostTest dataSender = new AsyncPostTest();
        // Example data to send
        String data = """
                {”sensor”: ”temperature”, ”value”:25.5}
                """;
        // Send data asynchronously
        dataSender.sendDataAsync(data);
        log.info("Hello world!");
    }

    public void sendDataAsync(String data) {
        // Create request body with the data
        RequestBody requestBody = RequestBody.create(data, MediaType.parse("application / json"));

        // Create HTTP request
        Request request = new Request.Builder()
                .url("https:// example.com/api/data")
                .post(requestBody)
                .build();

        // Asynchronously send the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException exception) {
                // Handle failure
                log.error("Exception ", exception);
            }

            @Override
            public void onResponse(Call call, Response response) {
                // Handle success
                if (response.isSuccessful()) {
                    log.info("Data sent successfully");
                } else {
                    log.info("Failed to send data: " + response.code());
                }
                response.close();
            }
        });
    }
}
