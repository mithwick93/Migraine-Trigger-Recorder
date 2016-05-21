package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Location {
    private int locationId;
    private String locationName;

    public Location(int locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof Location) {
            isEqual = (this.locationId == ((Location) object).locationId);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.locationId;
    }

    @Override
    public String toString() {
        return locationName != null ? locationName : "Location : " + locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
