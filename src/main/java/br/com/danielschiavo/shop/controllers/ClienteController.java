package br.com.danielschiavo.shop.controllers;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.danielschiavo.shop.infra.exceptions.MensagemErroDTO;
import br.com.danielschiavo.shop.infra.exceptions.ValidacaoException;
import br.com.danielschiavo.shop.models.cliente.AlterarClienteDTO;
import br.com.danielschiavo.shop.models.cliente.AlterarFotoPerfilDTO;
import br.com.danielschiavo.shop.models.cliente.CadastrarClienteDTO;
import br.com.danielschiavo.shop.models.cliente.MostrarClienteDTO;
import br.com.danielschiavo.shop.models.cliente.MostrarClientePaginaInicialDTO;
import br.com.danielschiavo.shop.models.filestorage.ArquivoInfoDTO;
import br.com.danielschiavo.shop.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/shop")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cliente", description = "Todos endpoints relacionados com o usuário")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@DeleteMapping("/cliente/foto-perfil")
	@Operation(summary = "Deleta foto do perfil do cliente")
	public ResponseEntity<?> deletarFotoPerfilClientePorIdToken() {
		try {
			clienteService.deletarFotoPerfilPorIdToken();
			return ResponseEntity.noContent().build();

		} catch (ValidacaoException e) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return ResponseEntity.status(status).body(new MensagemErroDTO(status, e));
		} catch (IOException e) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return ResponseEntity.status(status).body(new MensagemErroDTO(status.toString(), "Falha interna no servidor ao tentar excluir o arquivo."));
		}
	}
	
	@GetMapping("/cliente/pagina-inicial")
	public ResponseEntity<MostrarClientePaginaInicialDTO> detalharClientePaginaInicialPorIdToken() {
		MostrarClientePaginaInicialDTO clientePaginaInicialDTO = clienteService.detalharClientePorIdTokenPaginaInicial();
		
		return ResponseEntity.ok(clientePaginaInicialDTO);
	}

	@GetMapping("/cliente")
	@Operation(summary = "Mostra todos os dados do cliente")
	public ResponseEntity<?> detalharClientePorIdToken() {
		MostrarClienteDTO detalharClienteDTO = clienteService.detalharClientePorIdToken();
		
		return ResponseEntity.ok(detalharClienteDTO);
	}
	
	@PostMapping("/publico/cadastrar/cliente")
	@Operation(summary = "Cadastro de cliente")
	public ResponseEntity<MostrarClienteDTO> cadastrarCliente(@RequestBody @Valid CadastrarClienteDTO cadastrarClienteDTO,
			UriComponentsBuilder uriBuilder) {
		MostrarClienteDTO mostrarClienteDTO = clienteService.cadastrarCliente(cadastrarClienteDTO);
		
		var uri = uriBuilder.path("/shop/cliente/{id}").buildAndExpand(mostrarClienteDTO.id()).toUri();
		return ResponseEntity.created(uri).body(mostrarClienteDTO);
	}
	
	@PutMapping("/cliente")
	@Operation(summary = "Cliente altera seus próprios dados")
	public ResponseEntity<MostrarClienteDTO> alterarClientePorIdToken(@RequestBody @Valid AlterarClienteDTO alterarClienteDTO) {
		MostrarClienteDTO mostrarClienteDTO = clienteService.alterarClientePorIdToken(alterarClienteDTO);

		return ResponseEntity.ok(mostrarClienteDTO);
	}
	
	@PutMapping("/cliente/foto-perfil")
	@Operation(summary = "Alterar a foto do perfil do cliente")
	public ResponseEntity<?> alterarFotoPerfilPorIdToken(@RequestBody @Valid AlterarFotoPerfilDTO alterarFotoPerfilDTO) {
		try {
			ArquivoInfoDTO arquivoInfoDTO = clienteService.alterarFotoPerfilPorIdToken(alterarFotoPerfilDTO);
			return ResponseEntity.ok(arquivoInfoDTO);
			
		} catch (ValidacaoException e) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return ResponseEntity.status(status).body(new MensagemErroDTO(status, e));
		}
	}


//	------------------------------
//	------------------------------
//	ENDPOINTS PARA ADMINISTRADORES
//	------------------------------
//	------------------------------
	
	@DeleteMapping("/admin/cliente/{idCliente}")
	@Operation(summary = "Deleta o cliente pelo id fornecido no parametro da requisição")
	public ResponseEntity<?> adminDeletarClientePorId(@PathVariable Long idCliente) {
		clienteService.adminDeletarClientePorId(idCliente);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/admin/cliente")
	@Operation(summary = "Mostra todos os clientes cadastrados")
	public ResponseEntity<Page<MostrarClienteDTO>> adminDetalharTodosClientes(Pageable pageable) {
		var client = clienteService.adminDetalharTodosClientes(pageable);
		return ResponseEntity.ok(client);
	}
}
