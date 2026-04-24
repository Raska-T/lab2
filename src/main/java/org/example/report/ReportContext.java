package org.example.report;

import org.example.model.Mission;


public class ReportContext {
    private ReportGenerator strategy;

    public void setStrategy(ReportGenerator strategy) {
        this.strategy = strategy;
    }

    public void showReport(Mission mission) {
        if (strategy != null) {
            strategy.generate(mission);
        } else {
            System.out.println("Ошибка: тип отчета не выбран");
        }
    }

    public ReportContext() {}
}