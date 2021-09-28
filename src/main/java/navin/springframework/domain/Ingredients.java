package navin.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@ToString(exclude = {"recipie"})
@EqualsAndHashCode(exclude = {"recipie"})
@Entity
public class Ingredients {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private BigDecimal amount;

	@OneToOne
	private UnitOfMeasure unitOfMeasure;

	@ManyToOne
	private Recipie recipie;

	public Ingredients() {
	}

	public Ingredients(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recipie recipie) {
		this.description = description;
		this.amount = amount;
		this.unitOfMeasure = unitOfMeasure;
		this.recipie = recipie;
	}
}
