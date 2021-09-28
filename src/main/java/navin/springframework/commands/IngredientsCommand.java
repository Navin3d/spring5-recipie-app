package navin.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientsCommand {
    private Long id;
    private Long recipieId;

    private String description;
    private BigDecimal amount;

    private UnitOfMeasureCommand uom;

}
