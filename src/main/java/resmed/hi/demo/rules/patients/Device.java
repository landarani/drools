package resmed.hi.demo.rules.patients;

import lombok.Data;

@Data
public class Device {
    private final String serialNumber;
    private final Integer mid;
    private final Integer vid;
}
