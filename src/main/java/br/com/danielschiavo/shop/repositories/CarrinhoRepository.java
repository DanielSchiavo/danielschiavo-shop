package br.com.danielschiavo.shop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.danielschiavo.shop.models.carrinho.Carrinho;
import br.com.danielschiavo.shop.models.cliente.Cliente;

@Repository
public interface CarrinhoRepository extends JpaRepository <Carrinho, Long>{

	Optional<Carrinho> findByCliente(Cliente cliente);
	
}
