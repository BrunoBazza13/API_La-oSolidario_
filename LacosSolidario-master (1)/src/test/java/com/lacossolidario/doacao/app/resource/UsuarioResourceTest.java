package com.lacossolidario.doacao.app.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacossolidario.doacao.infra.model.DadosCadastroUsuario;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	// Teste de sucesso no cadastro do usuário
	@Test
	void deveCadastrarUsuarioComSucesso() throws Exception {
		// Dados de entrada do usuário
		DadosCadastroUsuario novoUsuario = new DadosCadastroUsuario("Usuário Teste", "brunonbazza@gmail.com", "senha123",
				"tipoUsuario", "12345678909", "51859814824");

		// Converte o objeto para JSON
		String usuarioJson = objectMapper.writeValueAsString(novoUsuario);

		// Executa a requisição de cadastro e verifica o status de sucesso
		mockMvc.perform(post("/usuario/cadastro").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(usuarioJson)).andExpect(status().isOk()) // Verifica se o status de resposta é 200 OK
				.andExpect(jsonPath("$.nome").value("Usuário Teste"))
				.andExpect(jsonPath("$.login").value("brunonbazza@gmail.com"));
	}

	// Teste para verificar erro de validação (senha vazia)
	@Test
	void deveRetornarErroQuandoSenhaVazia() throws Exception {
		// Dados de entrada com senha vazia
		DadosCadastroUsuario usuarioInvalido = new DadosCadastroUsuario("Usuário Teste", "mari@gmail.com", "",
				"tipoUsuario", "12345678909", "38186096833");

		String usuarioJson = objectMapper.writeValueAsString(usuarioInvalido);

		mockMvc.perform(post("/usuario/cadastro").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(usuarioJson)).andExpect(status().isBadRequest()) // Espera um erro 400 Bad Request
				.andExpect(jsonPath("$.mensagem").value("A senha não pode estar vazia")); // Verifica a mensagem de erro
	}

	// Teste para verificar erro de validação (CPF inválido)
	@Test
	void deveRetornarErroQuandoCpfInvalido() throws Exception {
		// Dados de entrada com CPF inválido
		DadosCadastroUsuario usuarioInvalido = new DadosCadastroUsuario("Usuário Teste", "gustavo@gmail.com", "senha123",
				"tipoUsuario", "123", "");

		String usuarioJson = objectMapper.writeValueAsString(usuarioInvalido);

		mockMvc.perform(post("/usuario/cadastro").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(usuarioJson)).andExpect(status().isBadRequest()) // Espera um erro 400 Bad Request
				.andExpect(jsonPath("$.mensagem").value("CPF inválido")); // Verifica a mensagem de erro
	}

}
