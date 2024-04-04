package br.com.danielschiavo.shop.controllers.filestorage;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.danielschiavo.shop.models.filestorage.ArquivoInfoDTO;
import br.com.danielschiavo.shop.models.filestorage.PostImagemPedidoDTO;
import br.com.danielschiavo.shop.services.filestorage.FileStoragePedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/shop")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Serviço de Armazenamento de Arquivos (Imagens e Vídeos)", description = "Para fazer upload de imagens e videos antes de enviar para cadastro de produto, cadastro de pedido, foto de perfil do usuário.")
public class FileStoragePedidoController {

	@Autowired
	private FileStoragePedidoService fileStoragePedidoService;
	
	@GetMapping("/admin/filestorage/pedido/{nomeImagemPedido}")
	@Operation(summary = "Recupera os bytes da imagem do pedido dado o nome no parametro da requisição")
	public ResponseEntity<ArquivoInfoDTO> pegarImagemPedidoPorNome(@PathVariable String nomeImagemPedido) {
		ArquivoInfoDTO arquivo = fileStoragePedidoService.pegarImagemPedidoPorNome(nomeImagemPedido);
		
		return ResponseEntity.ok(arquivo);
	}
	
	@PostMapping("/admin/filestorage/pedido")
	@Operation(summary = "Cadastra uma nova imagem do pedido e devolve o nome e os bytes da imagem, ou, se já tiver uma imagem cadastrada devolve o nome e os bytes da imagem já cadastrada")
	public ResponseEntity<?> persistirOuRecuperarImagemPedido(
			@RequestBody PostImagemPedidoDTO postImagemPedidoDTO,
			UriComponentsBuilder uriBuilder
			) {
		String nomeArquivo = fileStoragePedidoService.persistirOuRecuperarImagemPedido(postImagemPedidoDTO.nomePrimeiraImagemProduto(), postImagemPedidoDTO.idProduto());
		ArquivoInfoDTO arquivoInfoDTO = new ArquivoInfoDTO(nomeArquivo, null);
		
		URI uri = uriBuilder.path("/filestorage/pedido/" + arquivoInfoDTO.nomeArquivo()).build().toUri();
		return ResponseEntity.created(uri).body(arquivoInfoDTO);
	}
	
}