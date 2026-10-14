package service;

import model.Reportable;

// Interface implementation: ReportGenerator supplies the behavior promised by Reportable.
public class ReportGenerator implements Reportable {

    // Interface implementation: this class provides Reportable's required behavior.
    @Override
    public void generateReport() {
        System.out.println("===== Campus Lost & Found Report =====");
        System.out.println("Lost and Found items report generated.");
    }
}
