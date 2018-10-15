package br.les.opus.dengue.core.domain.enumeration;

public enum PoiStatus {
    OPEN("Open"), IN_ANALYSIS("In analysis"), TREATED("Treated");

    private String status;

    PoiStatus(String status) {
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}