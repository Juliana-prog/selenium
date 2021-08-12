package com.example.demopostman;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.annotations.Test;

import java.io.IOException;

public class PostmanTest {
    @Test
    public void pruebaPostman() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://postman-echo.com/get")
                .method("GET", null)
                .addHeader("Cookie", "sails.sid=s%3ApRi9MuPiHKY21kBbMBbeJzdQsZ987X7z.fKoZgLenGPvFgFkwhUum%2F2U0WUeFGlv5nwdanFAGO0Y")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
