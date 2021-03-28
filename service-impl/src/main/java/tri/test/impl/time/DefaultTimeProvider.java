package tri.test.impl.time;

import java.time.LocalDate;

import tri.test.api.time.TimeProvider;

public class DefaultTimeProvider implements TimeProvider {
    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
