package navin.springframework.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Data
@ToString(exclude = {"ingredients"})
@Entity
public class Recipie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;

	private String directions;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipie")
	private Set<Ingredients> ingredients = new HashSet<>();

	private Byte[] image;

	@Enumerated(value = EnumType.STRING)
	private Difficulty difficulty;

	@OneToOne(cascade = CascadeType.ALL)
	private Notes notes;

	@ManyToMany
	private Set<Category> category = new HashSet<>();

	public void setNotes(Notes notes) {
		this.notes = notes;
		notes.setRecipie(this);
	}

	public Recipie addIngredients(Ingredients ingredients) {
		ingredients.setRecipie(this);
		this.ingredients.add(ingredients);
		return this;
	}

}
