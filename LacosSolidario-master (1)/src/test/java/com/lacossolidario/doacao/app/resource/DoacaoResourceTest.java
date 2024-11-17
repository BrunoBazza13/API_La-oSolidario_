package com.lacossolidario.doacao.app.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacossolidario.doacao.domain.Categoria;
import com.lacossolidario.doacao.domain.Doacao;
import com.lacossolidario.doacao.domain.Instituicao;
import com.lacossolidario.doacao.domain.Usuario;
import com.lacossolidario.doacao.infra.model.DadosCadastroDoacao;
import com.lacossolidario.doacao.infra.model.DadosCadastroUsuario;
import com.lacossolidario.doacao.infra.repository.CategoriaRepository;
import com.lacossolidario.doacao.infra.repository.DoacaoRepository;
import com.lacossolidario.doacao.infra.repository.InstituicaoRepository;
import com.lacossolidario.doacao.infra.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;



class DoacaoResourceTest {

    @Mock
    private DoacaoRepository repository;

    @Mock
    private InstituicaoRepository instituicaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private DoacaoResource doacaoResource;

    @Autowired
    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON = MediaType.APPLICATION_JSON;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doacaoResource).build();
    }

    @Test
    void testRegistrarDoacao() throws Exception {

        DadosCadastroDoacao dados = new DadosCadastroDoacao(1L, "10/10/2024", 1L, 1L, "Doação de roupa");
        Long instituicaoId = 1L;
        Long usuarioId = 1L;
        Long categoriaId = 1L;


        Instituicao instituicao = new Instituicao();

        final var a = new DadosCadastroUsuario("Nome do Usuário", "loginUsuario", "senhaSegura",
                "tipo", "123456789", "12345678901");
        Usuario usuario = new Usuario(a);

        Categoria categoria = new Categoria("nome");
        categoria.setId(categoriaId);
        String descricao = "Descrição da doação";

        when(instituicaoRepository.findById(instituicaoId)).thenReturn(Optional.of(instituicao));
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(repository.save(any())).thenReturn(new Doacao(dados, instituicao, usuario, categoria, descricao));

        mockMvc.perform(post("/doacao")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dados)))
                .andExpect(status().isCreated());
    }

    @Test
    void testListarDoacoes() throws Exception {
        final var a = new DadosCadastroDoacao(1L, "10/10/2024", 1L, 1L, "Doação de roupa");
        final var dados = new DadosCadastroUsuario("Nome do Usuário", "loginUsuario", "senhaSegura",
                "tipo", "123456789", "12345678901");
        Doacao doacao1 = new Doacao(a, new Instituicao(), new Usuario(dados), new Categoria("Roupa"), "Doação de roupa" );
        Doacao doacao2 = new Doacao(a, new Instituicao(), new Usuario(dados), new Categoria("Brinquedo"), "Boneca" );
        List<Doacao> doacoes = Arrays.asList(doacao1, doacao2);

        when(repository.findAll()).thenReturn(doacoes);

        mockMvc.perform(get("/doacao"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDetalharPorId() throws Exception {
        final var a = new DadosCadastroDoacao(1L, "10/10/2024", 1L, 1L, "Doação de roupa");
        final var dados = new DadosCadastroUsuario("Nome do Usuário", "loginUsuario", "senhaSegura",
                "tipo", "123456789", "12345678901");

        Long doacaoId = 1L;
        Doacao doacao = new Doacao(a, new Instituicao(), new Usuario(dados), new Categoria("Roupa"), "Doação de roupa");
        when(repository.getReferenceById(doacaoId)).thenReturn(doacao);


        mockMvc.perform(get("/doacao/{id}", doacaoId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testExcluir() throws Exception {
        Long doacaoId = 1L;

        mockMvc.perform(delete("/doacao/{id}", doacaoId))
                .andExpect(status().isNoContent());

        verify(repository, times(1)).deleteById(doacaoId);
    }
}
