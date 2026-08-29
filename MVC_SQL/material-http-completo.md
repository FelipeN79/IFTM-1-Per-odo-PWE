# HTTP — Material Completo de Estudo
### Para a disciplina de Projeto Sistema Web MVC e SQL

---

## 1. O que é HTTP

**HTTP** (HyperText Transfer Protocol) é o protocolo de comunicação usado entre **clientes** (geralmente navegadores) e **servidores** na web. É a base de qualquer aplicação Spring MVC/Thymeleaf: toda vez que você acessa `http://localhost:8080/sorteio`, o navegador está enviando uma **requisição HTTP** e o `HomeController` está devolvendo uma **resposta HTTP**.

Características principais:

- **Baseado em texto** (na versão 1.1, a mais usada didaticamente) — apesar de HTTP/2 e HTTP/3 serem binários.
- **Sem estado (stateless)**: cada requisição é independente; o servidor não "lembra" da anterior por padrão (por isso existem cookies/sessão).
- **Modelo cliente-servidor**: o cliente sempre inicia a comunicação.
- **Baseado em texto/recursos**: tudo é tratado como um "recurso" identificado por uma URL.

---

## 2. Ciclo Requisição → Resposta

```
[Navegador/Cliente]                         [Servidor Spring Boot]
       |                                              |
       |----------- 1. Requisição HTTP -------------->|
       |     GET /sorteio HTTP/1.1                     |
       |     Host: localhost:8080                      |
       |                                              |
       |                                    2. Controller trata
       |                                       (HomeController)
       |                                              |
       |                                    3. Monta o Model
       |                                       e escolhe a View
       |                                              |
       |<----------- 4. Resposta HTTP ----------------|
       |     HTTP/1.1 200 OK                           |
       |     Content-Type: text/html                   |
       |     <html>...sorteio.html renderizado...</html>|
```

No Spring MVC, esse ciclo passa pelo **DispatcherServlet** (front controller), que:
1. Recebe a requisição.
2. Consulta o mapeamento (`@GetMapping`, `@RequestMapping`, etc.) para achar o Controller certo.
3. O Controller processa e devolve um nome de View (ou um `@ResponseBody`).
4. O `ViewResolver` (com Thymeleaf, usando prefix/suffix) localiza o arquivo HTML.
5. A View é renderizada com os dados do `Model` e devolvida como resposta.

---

## 3. Estrutura de uma Requisição HTTP

```
GET /produtos/3?desconto=true HTTP/1.1          ← Linha de requisição
Host: localhost:8080                             ← Headers
User-Agent: Mozilla/5.0
Accept: text/html
Cookie: JSESSIONID=ABC123

nome=Notebook&preco=2500                         ← Corpo (body) — só em POST/PUT/PATCH
```

**Linha de requisição** = `MÉTODO` + `caminho (path)` + `versão HTTP`.

### Partes de uma URL

```
http://localhost:8080/produtos/3?desconto=true#detalhes
└─┬─┘   └─────┬─────┘└────┬────┘└──────┬──────┘└───┬──┘
esquema     host:porta   path      query string   fragment
```

- **Path variable**: `/produtos/{id}` → `@PathVariable` no Spring (`/produtos/3`).
- **Query string/parameter**: `?desconto=true` → `@RequestParam` no Spring.

---

## 4. Métodos HTTP (Verbos)

| Método | Uso | Idempotente? | Tem body? | Anotação Spring |
|---|---|---|---|---|
| **GET** | Buscar/ler um recurso | Sim | Não (normalmente) | `@GetMapping` |
| **POST** | Criar um recurso / enviar dados de formulário | Não | Sim | `@PostMapping` |
| **PUT** | Atualizar um recurso por completo | Sim | Sim | `@PutMapping` |
| **PATCH** | Atualizar parcialmente um recurso | Não | Sim | `@PatchMapping` |
| **DELETE** | Remover um recurso | Sim | Não (normalmente) | `@DeleteMapping` |
| **HEAD** | Como GET, mas só retorna headers (sem body) | Sim | Não | — |
| **OPTIONS** | Descobre métodos permitidos em um recurso | Sim | Não | — |

> **Idempotente** = fazer a mesma requisição várias vezes tem o mesmo efeito que fazer uma vez (ex.: `DELETE /produtos/3` duas vezes ainda deixa o produto 3 deletado). **POST não é idempotente** (POST duas vezes pode criar dois registros).

### GET vs POST (pegadinha clássica de prova)

| | GET | POST |
|---|---|---|
| Dados | Vão na URL (query string) | Vão no corpo da requisição |
| Visibilidade | Visível na URL, fica no histórico | Não aparece na URL |
| Tamanho | Limitado (~2000 caracteres) | Praticamente sem limite |
| Cacheável | Sim | Não, por padrão |
| Uso típico | Buscar dados, navegação | Enviar formulários, criar dados |
| Em formulário Thymeleaf | `th:action` com `method="get"` | `th:action` com `method="post"` (padrão para forms que alteram dados) |

---

## 5. Status Codes (Códigos de Resposta)

Divididos em 5 classes, pelo primeiro dígito:

### 1xx — Informational
- **100 Continue** — servidor recebeu os headers, cliente pode enviar o body.

### 2xx — Sucesso
- **200 OK** — requisição bem-sucedida (padrão para GET que deu certo).
- **201 Created** — recurso criado com sucesso (típico de POST).
- **204 No Content** — sucesso, mas sem corpo na resposta (típico de DELETE).

### 3xx — Redirecionamento
- **301 Moved Permanently** — recurso mudou de URL definitivamente.
- **302 Found** — redirecionamento temporário (muito comum: `return "redirect:/produtos"` no Spring gera um 302).
- **304 Not Modified** — usado em cache (o recurso não mudou desde a última vez).

### 4xx — Erro do Cliente
- **400 Bad Request** — requisição malformada (ex.: JSON inválido, parâmetro faltando).
- **401 Unauthorized** — falta autenticação (não informou quem é).
- **403 Forbidden** — está autenticado, mas não tem permissão.
- **404 Not Found** — recurso/rota não existe (erro clássico de mapeamento errado no Controller).
- **405 Method Not Allowed** — usou o verbo errado (ex.: fez GET numa rota que só aceita POST).

### 5xx — Erro do Servidor
- **500 Internal Server Error** — erro genérico no servidor (uma exception não tratada no Controller, por exemplo — é o erro que aparece quando o código do `Model`/View quebra).
- **502 Bad Gateway** — servidor agindo como proxy recebeu resposta inválida.
- **503 Service Unavailable** — servidor sobrecarregado ou em manutenção.

> **Dica prática:** um erro `500` no seu projeto Spring geralmente indica exceção no Java (ex.: `NullPointerException`, atributo do Model não encontrado no HTML, erro de sintaxe Thymeleaf). Sempre olhe o **console/log** do terminal, que mostra o *stack trace* completo.

---

## 6. Headers (Cabeçalhos) mais importantes

### Headers de Requisição
| Header | Função |
|---|---|
| `Host` | Domínio/porta de destino (obrigatório em HTTP/1.1) |
| `User-Agent` | Identifica o navegador/cliente |
| `Accept` | Que tipos de conteúdo o cliente aceita receber (`text/html`, `application/json`) |
| `Content-Type` | Tipo do corpo enviado (ex.: `application/x-www-form-urlencoded` em formulários) |
| `Cookie` | Envia cookies armazenados (ex.: `JSESSIONID`) |
| `Authorization` | Credenciais de autenticação (ex.: token) |

### Headers de Resposta
| Header | Função |
|---|---|
| `Content-Type` | Tipo do conteúdo devolvido (`text/html`, `application/json`, `image/png`) |
| `Content-Length` | Tamanho do corpo em bytes |
| `Set-Cookie` | Servidor define um cookie no navegador |
| `Location` | Para onde redirecionar (usado com 301/302) |
| `Cache-Control` | Regras de cache |

---

## 7. Stateless, Cookies e Sessão

HTTP **não guarda estado** entre requisições. Mas aplicações web precisam "lembrar" do usuário (ex.: login, carrinho de compras). Soluções:

- **Cookies**: pequeno dado enviado pelo servidor (`Set-Cookie`) e devolvido pelo cliente em toda requisição seguinte (`Cookie`).
- **Sessão (Session)**: no Spring, o container gera um `JSESSIONID` (cookie) que identifica um objeto `HttpSession` no servidor, guardando dados entre requisições do mesmo usuário.
- **Token/JWT**: alternativa moderna e mais usada em APIs REST, sem depender de sessão no servidor.

---

## 8. Content-Type e Formatos de Dados

O `Content-Type` diz **como interpretar** o corpo da mensagem:

| Content-Type | Uso |
|---|---|
| `text/html` | Página HTML (o que Thymeleaf gera) |
| `application/json` | Dados em JSON (comum em APIs REST) |
| `application/x-www-form-urlencoded` | Formulário HTML padrão (`name=valor&name2=valor2`) |
| `multipart/form-data` | Formulário com upload de arquivo |
| `text/plain` | Texto puro |
| `image/png`, `image/jpeg` | Imagens |

---

## 9. HTTP no contexto do Spring MVC (ligação direta com sua matéria)

```java
@Controller
public class HomeController {

    @GetMapping("/sorteio")
    public String sorteio(Model model) {
        // 1. Requisição GET chega em /sorteio
        // 2. Lógica de negócio roda aqui
        model.addAttribute("numeros", gerarNumeros());
        // 3. Retorna o nome lógico da view
        return "sorteio"; // ViewResolver: prefix + "sorteio" + suffix
    }
}
```

- **Prefix/Suffix**: configurados em `application.properties`
  ```
  spring.thymeleaf.prefix=classpath:/templates/
  spring.thymeleaf.suffix=.html
  ```
  Isso faz o Spring transformar `"sorteio"` em `classpath:/templates/sorteio.html` — ligação direta entre a *string* retornada pelo Controller e a URL do recurso físico.

- **`@PathVariable` vs `@RequestParam`**:
  ```java
  @GetMapping("/produtos/{id}")          // URL: /produtos/3
  public String detalhe(@PathVariable Long id) { ... }

  @GetMapping("/produtos")               // URL: /produtos?id=3
  public String detalhe(@RequestParam Long id) { ... }
  ```

- **Formulário Thymeleaf enviando POST**:
  ```html
  <form th:action="@{/produtos}" th:object="${produto}" method="post">
      <input type="text" th:field="*{nome}" />
      <button type="submit">Salvar</button>
  </form>
  ```
  Isso gera uma requisição **POST** com `Content-Type: application/x-www-form-urlencoded`, tratada por um método `@PostMapping`.

- **`redirect:` gera um status 302**: quando o Controller retorna `"redirect:/produtos"`, o Spring manda uma resposta HTTP com status **302 Found** e header `Location: /produtos`, e o navegador faz uma **nova requisição GET** para essa URL (padrão Post-Redirect-Get, evita reenvio de formulário ao apertar F5).

---

## 10. Erros comuns (e como isso vira HTTP)

| Sintoma no navegador | Causa provável | Status HTTP |
|---|---|---|
| "Whitelabel Error Page" | Exceção não tratada no Controller/View | 500 |
| Página em branco / "Not Found" | Rota/mapeamento incorreto (`@GetMapping` errado ou nome de arquivo HTML diferente) | 404 |
| Erro ao clicar em link que deveria ser POST | Método HTTP errado no `th:action`/formulário | 405 |
| Loop de redirecionamento | `redirect:` apontando para si mesmo | 3xx repetido |

Isso conecta diretamente com o seu **bug pendente do `/sorteio` (500 Internal Server Error)**: um 500 sempre indica uma exceção Java durante o processamento — o próximo passo é olhar o *stack trace* no console do VS Code para achar a linha exata que falhou (geralmente atributo do `Model` não batendo com o que o HTML espera, ou erro de sintaxe no Thymeleaf).

---

## 11. Resumo rápido para revisão (flashcards mentais)

- HTTP é **stateless** → sessão/cookie resolve isso.
- **GET** busca, não deveria alterar dados; **POST** envia dados/cria.
- **2xx** = sucesso, **3xx** = redirecionamento, **4xx** = erro seu (cliente), **5xx** = erro do servidor.
- **404** = rota não existe; **500** = exceção no código do servidor.
- `Content-Type` diz o formato do corpo.
- No Spring MVC: Controller recebe a requisição → devolve nome de View → ViewResolver monta o caminho físico com prefix/suffix → Thymeleaf renderiza com os dados do Model.
- `redirect:` = resposta 302 + nova requisição GET do navegador.

---

## 12. Fontes recomendadas para aprofundar

- MDN Web Docs — HTTP (https://developer.mozilla.org/pt-BR/docs/Web/HTTP)
- Documentação oficial do Spring MVC (https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- RFC 9110 (especificação oficial atual do HTTP semantics)
