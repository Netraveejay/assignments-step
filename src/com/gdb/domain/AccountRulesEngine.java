
package com.gdb.domain;

public class AccountRulesEngine {

    private static AccountRulesPropertiesLoader savingsLoader =
        new AccountRulesPropertiesLoader(
            "src/main/resources/config/rules/savings.properties"
        );

    private static AccountRulesPropertiesLoader currentLoader =
        new AccountRulesPropertiesLoader(
            "src/main/resources/config/rules/current.properties"
        );

    private static AccountRulesPropertiesLoader fdLoader =
        new AccountRulesPropertiesLoader(
            "src/main/resources/config/rules/fixeddeposit.properties"
        );

    private static AccountRulesPropertiesLoader salaryLoader =
        new AccountRulesPropertiesLoader(
            "src/main/resources/config/rules/salary.properties"
        );

    public static String getSavingsBucket(int tenureYears) {
        if (tenureYears >= 5) return "privilege";
        if (tenureYears >= 3) return "premium";
        if (tenureYears >= 1) return "standard";
        return "new";
    }

    public static double getSavingsMinBalance(int tenureYears) {
        return savingsLoader.getDouble(
            "min.balance." + getSavingsBucket(tenureYears),
            10000.0
        );
    }

    public static double getSavingsInterestRate(int tenureYears) {
        return savingsLoader.getDouble(
            "interest.rate." + getSavingsBucket(tenureYears),
            2.70
        );
    }

    public static double getCurrentOverdraftLimit(
            double monthlyTurnover) {

        double minimum = currentLoader.getDouble(
            "overdraft.minimum", 25000.0
        );

        double multiplier = currentLoader.getDouble(
            "overdraft.turnover.multiplier", 2.5
        );

        return Math.max(minimum, monthlyTurnover * multiplier);
    }

    public static double getFDInterestRate(int months) {

        int standardTenure = fdLoader.getInt(
            "tenure.standard", 12
        );

        int longTenure = fdLoader.getInt(
            "tenure.long", 36
        );

        if (months >= longTenure) {
            return fdLoader.getDouble(
                "interest.rate.long", 7.50
            );
        }

        if (months >= standardTenure) {
            return fdLoader.getDouble(
                "interest.rate.standard", 6.50
            );
        }

        return fdLoader.getDouble(
            "interest.rate.short", 5.00
        );
    }

    public static String getDefaultSalaryEmployer() {
        return salaryLoader.getProperty(
            "employer.default", "TechCorp"
        );
    }

    public static double getSalaryMinBalance() {
        return salaryLoader.getDouble(
            "min.balance", 0.0
        );
    }
}