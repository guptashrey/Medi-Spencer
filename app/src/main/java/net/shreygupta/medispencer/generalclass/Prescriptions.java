package net.shreygupta.medispencer.generalclass;

public class Prescriptions {

    private String Medicines;
    private String Symptoms;
    private String DocumentId;

    public Prescriptions() {}

    public Prescriptions(String documentId, String medicines, String symptoms) {

        Medicines = medicines;
        Symptoms = symptoms;
        DocumentId = documentId;
    }

    public String getMedicines() {
        return Medicines;
    }

    public void setMedicines(String medicines) {
        Medicines = medicines;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }
}