package main.model;

public class Band {
    private final Integer bandId;
    private final String bandName;

    public Band(Integer bandId, String bandName) {
        this.bandId = bandId;
        this.bandName = bandName;
    }

    public Integer getBandId() {
        return bandId;
    }

    public String getBandName() {
        return bandName;
    }
}
