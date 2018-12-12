package br.les.opus.gamification.domain;

import br.les.opus.dengue.core.domain.PointOfInterest;
import org.springframework.data.domain.Page;

public class DashboardResults {
    private Page<PointOfInterest> pointOfInterestPage;
    private Object totalReportBase;
    private float percentByStates;
    private int focusPerStates;

    public Page<PointOfInterest> getPointOfInterestPage() {
        return pointOfInterestPage;
    }

    public void setPointOfInterestPage(Page<PointOfInterest> pointOfInterestPage) {
        this.pointOfInterestPage = pointOfInterestPage;
    }

    public Object getTotalReportBase() {
        return totalReportBase;
    }

    public void setTotalReportBase(Object totalReportBase) {
        this.totalReportBase = totalReportBase;
    }

    public float getPercentByStates() {
        return percentByStates;
    }

    public void setPercentByStates(float percentByStates) {
        this.percentByStates = percentByStates;
    }

    public int getFocusPerStates() {
        return focusPerStates;
    }

    public void setFocusPerStates(int focusPerStates) {
        this.focusPerStates = focusPerStates;
    }
}
