package edu.rx.http;

import static java.lang.Thread.sleep;
import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;
import static org.asynchttpclient.Dsl.get;

import io.reactivex.Maybe;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.asynchttpclient.extras.rxjava2.RxHttpClient;
import org.junit.jupiter.api.Test;

@Log4j2
public class RunHttpTests {

    @Test
    public void simpleHttpTest() throws InterruptedException, ExecutionException {
        AsyncHttpClient asyncHttpClient = asyncHttpClient(config().setIoThreadsCount(1).build());
        RxHttpClient rxHttpClient = RxHttpClient.create(asyncHttpClient);
        asyncHttpClient.executeRequest(get("http://localhost:8080/reset")).get();
        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < 1000; i++) {
            Maybe<Response> maybe = rxHttpClient.prepare(get("http://localhost:8080/users")
                    .addQueryParam("_sleep", "2000")
                    .addHeader("TraceId", generateTraceId())
                    .build());
            maybe.subscribe(response -> {
                log.info("Response Status {}", response.getStatusCode());
                log.info(counter.getAndIncrement());
                Thread.sleep(2000);
            });
        }

        sleep(20000);


    }

    @Test
    public void okHttpClient() throws InterruptedException, IOException {
        OkHttpClient httpClient = new Builder()
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .writeTimeout(10000L, TimeUnit.MILLISECONDS)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        Request resetRequest = new Request.Builder()
                .get()
                .header("TraceId", generateTraceId())
                .url("http://localhost:8080/reset").build();
        httpClient.newCall(resetRequest).execute();
        for (int i = 0; i < 1000; i++) {

            Request request = buildRequest();
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    log.info("failure {}", e.getMessage());
                }

                @SneakyThrows
                @Override
                public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response)
                        throws IOException {
                    log.info(response.code());
                    Thread.sleep(2000);
                }
            });
        }

        sleep(100000);

    }

    private Request buildRequest() {
        return new Request.Builder()
                .get()
                .header("TraceId", generateTraceId())
                .url("http://localhost:8080/users?_sleep=2000").build();
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }


}
