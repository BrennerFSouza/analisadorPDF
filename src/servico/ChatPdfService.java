package servico;

import com.google.gson.Gson;
import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;
import repositorio.ChatPdfRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatPdfService {
    private ChatPdfRepository chatPdfRepository = new ChatPdfRepository();

    //METODS
    public ChatResponse enviarPergunta(ChatRequest request) {
        return chatPdfRepository.enviarPergunta(request);
    }
}
