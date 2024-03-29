package br.com.danielschiavo.shop.models.pedido.validacoes;

import org.springframework.stereotype.Service;

import br.com.danielschiavo.shop.infra.exceptions.ValidacaoException;
import br.com.danielschiavo.shop.models.cliente.Cliente;
import br.com.danielschiavo.shop.models.pedido.CriarPedidoDTO;
import br.com.danielschiavo.shop.models.pedido.TipoEntrega;

@Service
public class ValidadorTipoEntrega implements ValidadorCriarNovoPedido {

	@Override
	public void validar(CriarPedidoDTO pedidoDTO, Cliente cliente) {
		TipoEntrega tipoEntregaDTO = pedidoDTO.entrega().tipoEntrega();
		Long idEnderecoDTO = pedidoDTO.entrega().idEndereco();
		if ((tipoEntregaDTO == TipoEntrega.CORREIOS || tipoEntregaDTO == TipoEntrega.ENTREGA_EXPRESSA) && idEnderecoDTO == null) {
			throw new ValidacaoException("O tipo de entrega definido foi " + tipoEntregaDTO + ", portanto, é necessário que envie o endereço do local de entrega.");
		}
		
		if (tipoEntregaDTO == TipoEntrega.ENTREGA_DIGITAL || tipoEntregaDTO == TipoEntrega.RETIRADA_NA_LOJA) {
			if (idEnderecoDTO != null) {
				throw new ValidacaoException("O tipo de entrega definido foi " + tipoEntregaDTO + ", portanto, não é necessário que envie o idEndereco.");
			}
		}
	}

}
