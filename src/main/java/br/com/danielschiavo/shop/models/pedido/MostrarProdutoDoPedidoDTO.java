package br.com.danielschiavo.shop.models.pedido;

import java.math.BigDecimal;

import br.com.danielschiavo.shop.models.pedido.itempedido.ItemPedido;

public record MostrarProdutoDoPedidoDTO(
		Long idProduto,
		String nomeProduto,
		BigDecimal preco,
		Integer quantidade,
		BigDecimal subTotal,
		byte[] primeiraImagem
		) {

	public MostrarProdutoDoPedidoDTO(ItemPedido itemPedido, byte[] primeiraImagem) {
		this(itemPedido.getProduto().getId(),
			 itemPedido.getNomeProduto(),
			 itemPedido.getPreco(),
			 itemPedido.getQuantidade(),
			 itemPedido.getSubTotal(),
			 primeiraImagem);
	}

}
