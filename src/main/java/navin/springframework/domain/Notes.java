package navin.springframework.domain;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(exclude = {"recipie"})
@EqualsAndHashCode(exclude = {"recipie"})
@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Recipie recipie;
	
	private String recipieNotes;


}
