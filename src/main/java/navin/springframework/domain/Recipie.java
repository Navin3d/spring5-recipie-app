package navin.springframework.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Data
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

	@Lob
	private String directions;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipie")
	private Set<Ingredients> ingredients = new HashSet<>();

	@Lob
	private Byte[] image;

	@Enumerated(value = EnumType.STRING)
	private Difficulty difficulty;

	@OneToOne(cascade = CascadeType.ALL)
	private Notes notes;

	@ManyToMany
	@JoinTable(name = "Recipiecategory", joinColumns = @JoinColumn(name = "recipieid"), inverseJoinColumns = @JoinColumn(name = "categoryid"))
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
