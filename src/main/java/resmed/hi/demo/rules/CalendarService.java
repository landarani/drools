package resmed.hi.demo.rules;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CalendarService {

    public LocalDate today() {
        return LocalDate.now();
    }
}
