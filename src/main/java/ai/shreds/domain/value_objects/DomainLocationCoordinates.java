package ai.shreds.domain.value_objects;

import lombok.Builder;
import lombok.Value;
import java.time.Instant;

@Value
@Builder
public class DomainLocationCoordinates {
    Double latitude;
    Double longitude;
    Double altitude;
    Double speed;
    Integer heading;
    Integer satelliteCount;
    Instant timestamp;
    Double horizontalAccuracy;

    public boolean isValid() {
        return validateLatitude() &&
               validateLongitude() &&
               validateHeading() &&
               validateBasicFields() &&
               validateTimestamp();
    }

    private boolean validateLatitude() {
        return latitude != null && latitude >= -90 && latitude <= 90;
    }

    private boolean validateLongitude() {
        return longitude != null && longitude >= -180 && longitude <= 180;
    }

    private boolean validateHeading() {
        return heading == null || (heading >= 0 && heading <= 360);
    }

    private boolean validateBasicFields() {
        return speed == null || speed >= 0 &&
               satelliteCount == null || satelliteCount > 0 &&
               horizontalAccuracy == null || horizontalAccuracy > 0;
    }

    private boolean validateTimestamp() {
        return timestamp != null && !timestamp.isAfter(Instant.now());
    }

    public Integer calculateQualityScore() {
        if (!isValid()) {
            return 0;
        }

        int score = 0;

        // Basic coordinate validation (30 points)
        if (validateLatitude() && validateLongitude()) {
            score += 30;
        }

        // Satellite count contribution (up to 25 points)
        if (satelliteCount != null) {
            score += Math.min(satelliteCount * 5, 25);
        }

        // Horizontal accuracy contribution (up to 25 points)
        if (horizontalAccuracy != null && horizontalAccuracy <= 10) { // 10 meters or better
            score += 25;
        } else if (horizontalAccuracy != null && horizontalAccuracy <= 20) {
            score += 15;
        } else if (horizontalAccuracy != null) {
            score += 5;
        }

        // Additional data completeness (20 points)
        if (altitude != null) score += 5;
        if (speed != null) score += 5;
        if (heading != null) score += 5;
        if (timestamp != null && timestamp.isAfter(Instant.now().minusSeconds(30))) score += 5;

        return score;
    }
}
