package resmed.hi.demo.rules.facts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SummaryDataService {

    public List<SummaryData> getDataForPatient(String ecn, LocalDate fromDate, LocalDate toDate) {
        if (ecn.equalsIgnoreCase("No-Data")) {
            return Collections.emptyList();
        }
        List<SummaryData> result = new ArrayList<>();
        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            result.add(createSummaryData(date));
        }
        return result;
    }

    private SummaryData createSummaryData(LocalDate sessionDate) {
        SummaryData data = new SummaryData();
        data.setSessionDate(sessionDate);
        data.setSerialNumber("12345678901");
        data.put("Val.Duration", 200d); // good usage
        data.put("Val.AHI", 30d); // above 25
        // below 20% of usual value in last 5 days
        data.put("Val.Pressure.50", sessionDate.isAfter(LocalDate.now().minusDays(5)) ? 20d : 30d);
        return data;
    }
}
