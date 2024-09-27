package colak;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * See <a href="https://stackoverflow.com/questions/57260220/a-connection-to-was-leaked-did-you-forget-to-close-a-response-body">...</a>
 */
@Slf4j
class GetTest {

    private final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws Exception {
        GetTest dateGetter = new GetTest();
        dateGetter.getData("http://www.google.com");
    }

    private void getData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        // To avoid the message below close the Response. Closing Response closes the underlying ResponseBody
        // A connection to http://localhost:34496/ was leaked. Did you forget to close a response body?
        try (Response response = call.execute()) {

            // This is just to show that even if we close ResponseBody one more time it is harmless
            try (ResponseBody responseBody = response.body()) {
                int code = response.code();
                log.info("Code : {}", code);

                if (!response.isSuccessful()) {
                    log.info("response is not successful");
                } else {

                    // text/html; charset=ISO-8859-1
                    assert responseBody != null;
                    MediaType mediaType = responseBody.contentType();
                    assert mediaType != null;
                    log.info(mediaType.toString());
                }
            }
        }
    }
}
