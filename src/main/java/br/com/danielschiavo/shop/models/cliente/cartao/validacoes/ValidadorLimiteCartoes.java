package br.com.danielschiavo.shop.models.cliente.cartao.validacoes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.danielschiavo.shop.infra.exceptions.ValidacaoException;
import br.com.danielschiavo.shop.models.cliente.Cliente;
import br.com.danielschiavo.shop.models.cliente.cartao.CadastrarCartaoDTO;
import br.com.danielschiavo.shop.models.cliente.cartao.Cartao;
import br.com.danielschiavo.shop.repositories.cliente.CartaoRepository;

@Service
public class ValidadorLimiteCartoes implements ValidadorCadastrarNovoCartao {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Override
	public void validar(CadastrarCartaoDTO cartaoDTO, Cliente cliente) {
		List<Cartao> todosCartoesCliente = cartaoRepository.findAllByCliente(cliente);
		
		if (todosCartoesCliente.size() == 10) {
			throw new ValidacaoException("Limite de quantidade de cartões por cliente atingido, que são 10 cartões");
		}
	}

}