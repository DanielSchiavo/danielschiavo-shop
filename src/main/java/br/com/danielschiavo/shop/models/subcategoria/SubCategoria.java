package br.com.danielschiavo.shop.models.subcategoria;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.danielschiavo.shop.models.categoria.Categoria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "sub_categorias")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SubCategoria {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 80, unique = true)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JsonBackReference
	private Categoria categoria;

}

