<div align="center">

# AnalisadorPDF ![Java](https://img.shields.io/badge/Java-25-blue?style=for-the-badge&logo=java&logoColor=white)
[![GitHub license](https://img.shields.io/github/license/brennerfonseca/analisador-pdf?style=for-the-badge)](LICENSE)
[![ChatPDF API](https://img.shields.io/badge/ChatPDF-API-v1-orange?style=for-the-badge&logo=chatpdf&logoColor=white)](https://chatpdf.com)

🤖 **Chat com PDFs usando IA** - 100% **Java Puro** (sem frameworks!)

</div>

---

## ✨ **O que faz?**

Faz perguntas sobre **qualquer PDF** usando a API do ChatPDF!

**Exemplo real**: Analisa seu exame laboratorial e responde:
> "O exame laboratorial do paciente Brenner da Fonseca Souza... HbA1c 5,5%... bom controle glicêmico..."

## 🎯 **Funcionalidades v1.0**

| ✅ **Core** | ✅ **Segurança** | ✅ **Qualidade** |
|---|---|---|
| Chat com PDFs | API Key externa | Tratamento erros |
| HttpClient nativo | SourceId seguro | Código limpo |
| JSON Gson | Zero segredos no código | Estrutura MVC |
| Respostas IA | System Properties | IntelliJ ready |

## 🚀 **Como Usar (3 minutos)**

### **1. Pré-requisitos**
☕ Java 11+ (Feito com Java 25 ✅)
🔗 ChatPDF API Key (chatpdf.com)
📄 SourceId do PDF

### **2. Baixar Gson**
```bash
mkdir lib
# Windows:
curl -L https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar -o lib/gson-2.10.1.jar
# Ou baixe direto: https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
```

3. Compilar e Executar
bash
# Compile
javac -cp "lib/gson-2.10.1.jar" -d bin src/*.java src/*/*.java

# Execute (SEUS dados!)
java -Dchatpdf.api.key=sec_u455RyzsK8cg06gJ5u6Bg21r9D3YdMM0 ^
     -Dchatpdf.source.id=SEU_SOURCEID ^
     -cp "bin:lib/*" analisadorpdf2.cli.Main
📱 Exemplo Saída
text
🔍 Iniciando Analisador PDF com ChatPDF...
⏳ Enviando pergunta para ChatPDF...
✅ RESPOSTA DO CHATPDF:
📄 O exame laboratorial do paciente Brenner da Fonseca Souza, coletado em 12/02/2026...
🔑 Como Obter Credenciais
API Key (ChatPDF)
text
1. chatpdf.com → Login
2. Dashboard → API Keys → "Create API Key"  
3. Copie: sec_xxxxxxxxxxxxxxxxxxxxxx
SourceId (PDF)
text
1. Faça UPLOAD de PDF no ChatPDF
2. URL mostra: ?p=src_XXXXXXXXXX ← COPIE ISTO!

🏗️ Estrutura do Projeto
text
analisadorPDF/
├── README.md                 # 📖 Você está aqui!
├── .gitignore               # 🗑️ Ignora binários
├── lib/
│   └── gson-2.10.1.jar      # 📦 JSON
└── src/
├── cli/
│   └── Main.java        # 🚪 Entry point
├── modelo/              # 📦 Dados
│   ├── Message.java
│   ├── ChatRequest.java
│   └── ChatResponse.java
├── servico/             # ⚙️ Lógica
│   └── ChatPdfService.java
└── configuracao/        # 🔧 Config
└── ApiConfig.java
🎓 Conceitos Aprendidos
java.net.http.HttpClient (Java 11+)

Gson bidirecional (JSON ↔ Objetos)

System.getProperty() segura

POJOs com getters/setters

Package by Feature

Tratamento exceções

🛠️ Camadas da Arquitetura
text
graph TB
A[Main.java] --> B[ApiConfig]
B --> C[ChatRequest]
C --> D[ChatPdfService]
D --> E[HttpClient]
E --> F[ChatPDF API]
F --> G[ChatResponse]
G --> H[Console]
✅ Status: Produção
Item	Status
Código	✅ Funcional
Segurança	✅ Sem credenciais
Documentação	✅ Completa
Testado	✅ Brenner 11/03/2026
📈 Roadmap Futuro
text
v2.0 → Upload PDF automático
v2.1 → Menu interativo  
v2.2 → Histórico chat
v3.0 → Maven + GUI
👨‍💻 Autor
Brenner da Fonseca Souza
Business Analyst | Java Developer
Catalão, GO - Brasil 🇧🇷
Criado: 11 Março 2026

📄 Licença
MIT License - Use livremente!