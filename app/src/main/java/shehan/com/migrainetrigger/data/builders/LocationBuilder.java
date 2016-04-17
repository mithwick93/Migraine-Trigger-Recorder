package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.Location;

public class LocationBuilder {
    private int locationId;
    private String locationName;

    public LocationBuilder setLocationId(int locationId) {
        this.locationId = locationId;
        return this;
    }

    public LocationBuilder setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public Location createLocation() {
        return new Location(locationId, locationName);
    }
}