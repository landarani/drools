package resmed.hi.demo.rules.patients;

import lombok.Data;
import org.apache.commons.collections4.map.HashedMap;

import java.time.LocalDate;
import java.util.Map;

@Data
public class Patient {
    private final String ecn;
    private final LocalDate setupDate;
    private final Map<LocalDate, Device> deviceAssignment = new HashedMap<>();

    public Patient addDevice(LocalDate from, LocalDate to, Device device) {
        for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
            deviceAssignment.put(date, device);
        }
        return this;
    }
}
