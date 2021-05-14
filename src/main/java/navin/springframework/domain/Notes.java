package navin.springframework.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Data
@EqualsAndHashCode(exclude = {"recipie"})
@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Recipie recipie;
	
	@Lob
	private String recipieNotes;


}
