package com.lacossolidario.doacao.domain;

import com.lacossolidario.doacao.infra.model.DadosEndereco;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String logradouro;
	private String cep;
	private String numero;

	
	

	public Endereco() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Endereco(DadosEndereco dados) {
		this.logradouro = dados.logradouro();
		this.cep = dados.cep();
		this.numero = dados.numero();
	}

	public void atualizarEndereco(DadosEndereco dados) {
		if (dados.logradouro() != null) {
			this.logradouro = dados.logradouro();
		}
		if (dados.cep() != null) {
			this.cep = dados.cep();
		}
		if (dados.numero() != null) {
			this.numero = dados.numero();
		}
	}
}
