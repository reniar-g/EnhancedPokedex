
public class Trainer {

    private int trainerId;
    private String trainerName;
    private String trainerBirthdate;
    private String trainerSex;
    private String trainerHometown;
    private String trainerDescription;
    private double trainerMoney;
    // add necessary array lists 

    public Trainer(int trainerId, String trainerName, String trainerBirthdate,
            String trainerSex, String trainerHometown, String trainerDescription) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerBirthdate = trainerBirthdate;
        this.trainerSex = trainerSex;
        this.trainerHometown = trainerHometown;
        this.trainerDescription = trainerDescription;
        this.trainerMoney = 1000000.00; // Initial money for the trainer (Requirement #4)
    }

    // Getters for trainer attributes
    public int getTrainerId() {
        return trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getTrainerBirthdate() {
        return trainerBirthdate;
    }

    public String getTrainerSex() {
        return trainerSex;
    }

    public String getTrainerHometown() {
        return trainerHometown;
    }

    public String getTrainerDescription() {
        return trainerDescription;
    }

    public double getTrainerMoney() {
        return trainerMoney;
    }

    public void displayTrainer() {
        System.out.println("Trainer ID: " + trainerId);
        System.out.println("Name: " + trainerName);
        System.out.println("Birthdate: " + trainerBirthdate);
        System.out.println("Sex: " + trainerSex);
        System.out.println("Hometown: " + trainerHometown);
        System.out.println("Description: " + trainerDescription);
        System.out.println("Money: P" + String.format("%.2f", trainerMoney));
    }
}
