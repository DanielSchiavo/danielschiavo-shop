package br.com.danielschiavo.shop.models.cliente;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.danielschiavo.shop.models.cartao.MostrarCartaoDTO;
import br.com.danielschiavo.shop.models.endereco.MostrarEnderecoDTO;

public record MostrarClienteDTO(
		Long id,
		String cpf,
		String nome,
		String sobrenome,
		LocalDate dataNascimento,
		String email,
		String senha,
		String celular,
		List<MostrarEnderecoDTO> enderecos,
		List<MostrarCartaoDTO> cartoes
		) {

	public MostrarClienteDTO(Cliente cliente) {
		this(
			cliente.getId(),
			cliente.getCpf(),
			cliente.getNome(),
			cliente.getSobrenome(),
			cliente.getDataNascimento(),
			cliente.getEmail(),
			cliente.getSenha(),
			cliente.getCelular(),
			cliente.getEnderecos().stream()
	        .map(MostrarEnderecoDTO::converterParaMostrarEnderecoDTO)
	        .collect(Collectors.toList()),
			cliente.getCartoes().stream()
	        .map(MostrarCartaoDTO::converterParaMostrarCartaoDTO)
	        .collect(Collectors.toList()));
	}

}