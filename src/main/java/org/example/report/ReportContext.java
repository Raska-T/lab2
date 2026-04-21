package org.example.report;

import org.example.model.Mission;


public class ReportContext {
    private ReportGenerator strategy; // Поле - strategy

    public void setStrategy(ReportGenerator strategy) { // Метод setStrategy
        this.strategy = strategy;
    }

    public void showReport(Mission mission) {
        if (strategy != null) {
            strategy.generate(mission); // Вызов метода стратегии
        } else {
            System.out.println("Ошибка: стратегия не выбрана");
        }
    }

    public ReportContext() {}

    public ReportContext(ReportGenerator strategy) {
        this.strategy = strategy;
    }
}