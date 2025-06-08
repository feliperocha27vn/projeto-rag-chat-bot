package br.felipe.com.felipe_kaike_rag.view.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiService {

    private final Context context;
    private final Handler mainHandler;
    private final Gson gson;
    private final String baseUrl;

    public ApiService(Context context, Handler mainHandler, Gson gson, String baseUrl) {
        this.context = context;
        this.mainHandler = mainHandler;
        this.gson = gson;
        this.baseUrl = baseUrl;
    }

    // Interfaces para callbacks
    public interface OnSuccessCallback {
        void onSuccess(String response);
    }

    public interface OnErrorCallback {
        void onError(String errorMessage);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void fazerRequisicao(String pergunta, ProgressDialog progressDialog,
                                OnSuccessCallback onSuccess, OnErrorCallback onError) {

        if (pergunta == null || pergunta.trim().isEmpty()) {
            mainHandler.post(() -> Toast.makeText(context, "Por favor, digite uma pergunta", Toast.LENGTH_SHORT).show());
            return;
        }

        if (!isNetworkAvailable()) {
            mainHandler.post(() -> Toast.makeText(context, "Sem conexão com a internet", Toast.LENGTH_SHORT).show());
            return;
        }

        mainHandler.post(() -> progressDialog.show());

        new Thread(() -> {
            HttpURLConnection conexaoComApi = null;
            try {
                URL url = new URL(baseUrl);
                conexaoComApi = (HttpURLConnection) url.openConnection();
                conexaoComApi.setRequestMethod("POST");
                conexaoComApi.setRequestProperty("Content-Type", "application/json");
                conexaoComApi.setDoOutput(true);
                conexaoComApi.setConnectTimeout(15000);
                conexaoComApi.setReadTimeout(15000);

                String jsonInputString = "{\"pergunta\": \"" + pergunta + "\"}";

                try (OutputStream os = conexaoComApi.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = conexaoComApi.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conexaoComApi.getInputStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine);
                        }

                        final String respostaFinal = response.toString();
                        mainHandler.post(() -> {
                            try {
                                gson.fromJson(respostaFinal, Object.class);
                                onSuccess.onSuccess(respostaFinal);
                            } catch (Exception e) {
                                onError.onError("Resposta inválida do servidor: " + respostaFinal);
                            }
                        });
                    }
                } else {
                    mainHandler.post(() -> onError.onError("Erro no servidor: " + responseCode));
                }

            } catch (java.net.ConnectException e) {
                e.printStackTrace();
                mainHandler.post(() -> onError.onError("Não foi possível conectar ao servidor. Verifique se o servidor está rodando."));
            } catch (java.net.SocketTimeoutException e) {
                e.printStackTrace();
                mainHandler.post(() -> onError.onError("Tempo de conexão esgotado. O servidor pode estar sobrecarregado ou indisponível."));
            } catch (Exception e) {
                e.printStackTrace();
                mainHandler.post(() -> onError.onError("Erro: " + e.getMessage()));
            } finally {
                mainHandler.post(() -> {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                });
                if (conexaoComApi != null) {
                    conexaoComApi.disconnect();
                }
            }
        }).start();
    }
}