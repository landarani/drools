package resmed.hi.demo.rules.facts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SummaryData extends HashMap<String, Double> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private LocalDate sessionDate;
    private String serialNumber;
    private Integer program;
}
