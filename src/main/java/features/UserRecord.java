package features;

import java.time.LocalDateTime;
import java.util.Objects;

public record UserRecord(String email, String name, LocalDateTime createAt) {

    @Override
    public String toString() {
        String email = Objects.requireNonNullElse(this.email, "");
        String name = Objects.requireNonNullElse(this.name, "");
        LocalDateTime createAt = Objects.requireNonNullElse(this.createAt, LocalDateTime.now());
        return """
                UserRecord(email='%s', name='%s', createAt='%s')
                """.formatted(email, name, createAt);
    }
}
