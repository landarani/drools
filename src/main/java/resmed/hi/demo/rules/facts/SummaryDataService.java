package resmed.hi.demo.rules.facts;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class SummaryDataService {
    public List<SummaryData> getDataForPatient(String ecn, LocalDate fromDate, LocalDate toDate) {
        return Collections.emptyList();
    }
}
