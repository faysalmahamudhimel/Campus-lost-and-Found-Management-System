package model;

// Abstraction: defines a reporting capability without prescribing its implementation.
public interface Reportable {

    // Any implementing class must provide this report-generation behavior.
    void generateReport();
}
