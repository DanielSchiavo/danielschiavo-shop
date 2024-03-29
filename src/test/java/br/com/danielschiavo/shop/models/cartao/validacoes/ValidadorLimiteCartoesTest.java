package br.com.danielschiavo.shop.models.cartao.validacoes;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.danielschiavo.shop.infra.exceptions.ValidacaoException;
import br.com.danielschiavo.shop.models.cartao.CadastrarCartaoDTO;
import br.com.danielschiavo.shop.models.cartao.Cartao;
import br.com.danielschiavo.shop.models.cartao.validacoes.ValidadorLimiteCartoes;
import br.com.danielschiavo.shop.models.cliente.Cliente;
import br.com.danielschiavo.shop.repositories.CartaoRepository;

@ExtendWith(MockitoExtension.class)
class ValidadorLimiteCartoesTest {

	@InjectMocks
	private ValidadorLimiteCartoes validador;
	
	@Mock
	private CadastrarCartaoDTO cadastrarCartaoDTO;
	
	@Mock
	private Cliente cliente;
	
	@Mock
	private CartaoRepository cartaoRepository;
	
	@Mock
	private List<Cartao> listaCartoes;
	
	@Test
	@DisplayName("Validador limite cartões não deve lançar ValidacaoException quando cliente tem 9 ou menos cartões cadastrados")
	void ValidadorLimiteCartoes_ClienteTem9CartoesCadastrados_NaoDeveLancarExcecao() {
		BDDMockito.given(cartaoRepository.findAllByCliente(cliente)).willReturn(listaCartoes);
		BDDMockito.given(listaCartoes.size()).willReturn(9);
		
		Assertions.assertDoesNotThrow(() -> validador.validar(cadastrarCartaoDTO, cliente));
	}
	
	@Test
	@DisplayName("Validador limite cartões deve lançar ValidacaoException quando cliente tem 10 cartões cadastrados")
	void ValidadorLimiteCartoes_ClienteTem10CartoesCadastrados_DeveLancarExcecao() {
		BDDMockito.given(cartaoRepository.findAllByCliente(cliente)).willReturn(listaCartoes);
		BDDMockito.given(listaCartoes.size()).willReturn(10);
		
		Assertions.assertThrows(ValidacaoException.class, () -> validador.validar(cadastrarCartaoDTO, cliente));
	}

}
