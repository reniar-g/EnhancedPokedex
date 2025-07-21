
public class MenuArts {

    // Main menu arts
    // Menu Display Methods
    public static void printMainMenu() {
        System.out.println("\n                       === Enhanced Pokédex Menu ===");
        System.out.println("                            1. Pokémon Management");
        System.out.println("                            2. Moves Management");
        System.out.println("                            3. Item Management");
        System.out.println("                            4. Trainer Management");
        System.out.println("                            0. Exit");
    }

    public static void printPokemonMenu() {
        System.out.println("\n                          === Pokémon Management ===");
        System.out.println("                             1. Add New Pokémon");
        System.out.println("                             2. View All Pokémon");
        System.out.println("                             3. View Pokémon Details");
        System.out.println("                             4. Search Pokémon");
        System.out.println("                             0. Back to Main Menu");
    }

    public static void printMovesMenu() {
        System.out.println("\n                           === Moves Management ===");
        System.out.println("                              1. Add New Move");
        System.out.println("                              2. View All Moves");
        System.out.println("                              3. Search Moves");
        System.out.println("                              0. Back to Main Menu");
    }

    public static void printItemMenu() {
        System.out.println("\n                          === Item Management ===");
        System.out.println("                             1. View All Items");
        System.out.println("                             2. Search Items");
        System.out.println("                             0. Back to Main Menu");
    }

    public static void printTrainerMenu() {
        System.out.println("\n                        === Trainer Management ===");
        System.out.println("                           1. Add New Trainer");
        System.out.println("                           2. View All Trainers");
        System.out.println("                           3. Search Trainers");
        System.out.println("                           4. Manage Trainer");
        System.out.println("                           0. Back to Main Menu");
    }

    // ASCII art methods
    public static void pokedexArt() {
        System.out.println("===========================================================================");
        System.out.println("                 ______ _____ _   __ ___________ _______   __");
        System.out.println("                 | ___ \\  _  | | / /|  ___|  _  \\  ___\\ \\ / /");
        System.out.println("                 | |_/ / | | | |/ / | |__ | | | | |__  \\ V / ");
        System.out.println("                 |  __/| | | |    \\ |  __|| | | |  __| /   \\ ");
        System.out.println("                 | |   \\ \\_/ / |\\  \\| |___| |/ /| |___/ /^\\ \\");
        System.out.println("                 \\_|    \\___/\\_| \\_/\\____/|___/ \\____/\\/   \\/ \n");
        System.out.println("===========================================================================");
    }

    public static void pokemonArt() {
        System.out.println("===========================================================================");
        System.out.println("             ______ _____ _   __ ________  ________ _   _  _____ ");
        System.out.println("             | ___ \\  _  | | / /|  ___|  \\/  |  _  | \\ | |/  ___|");
        System.out.println("             | |_/ / | | | |/ / | |__ | .  . | | | |  \\| |\\ `--. ");
        System.out.println("             |  __/| | | |    \\ |  __|| |\\/| | | | | . ` | `--. \\");
        System.out.println("             | |   \\ \\_/ / |\\  \\| |___| |  | \\ \\_/ / |\\  |/\\__/ /");
        System.out.println("             \\_|    \\___/\\_| \\_/\\____/\\_|  |_/\\___/\\_| \\_/\\____/ \n");
        System.out.println("===========================================================================");
    }

    public static void movesArt() {
        System.out.println("===========================================================================");
        System.out.println("                      ___  ___  _____  _   _  _____  _____ ");
        System.out.println("                      |  \\/  | |  _  || | | ||  ___||  ___|");
        System.out.println("                      | .  . | | | | || | | || |__  | |___ ");
        System.out.println("                      | |\\/| | | | | || | | ||  __| |___  |");
        System.out.println("                      | |  | | \\ \\_/ /\\ \\_/ /| |___ /\\__/ /");
        System.out.println("                      \\_|  |_/  \\___/  \\___/ \\____/ \\____/ \n");
        System.out.println("===========================================================================");
    }

    public static void trainersArt() {
        System.out.println("===========================================================================");
        System.out.println("              ___________  ___  _____ _   _  ___________  _____ ");
        System.out.println("             |_   _| ___ \\/ _ \\|_   _| \\ | ||  ___| ___ \\/  ___|");
        System.out.println("               | | | |_/ / /_\\ \\ | | |  \\| || |__ | |_/ /\\ `--. ");
        System.out.println("               | | |    /|  _  | | | | . ` ||  __||    /  `--. \\");
        System.out.println("               | | | |\\ \\| | | |_| |_| |\\  || |___| |\\ \\ /\\__/ /");
        System.out.println("               \\_/ \\_| \\_\\_| |_/\\___/\\_| \\_/\\____/\\_| \\_|\\____/ \n");
        System.out.println("===========================================================================");
    }

    public static void itemsArt() {
        System.out.println("===========================================================================");
        System.out.println("                        _____ _____ ________  ___ _____ ");
        System.out.println("                       |_   _|_   _|  ___|  \\/  |/  ___|");
        System.out.println("                         | |   | | | |__ | .  . |\\ `--. ");
        System.out.println("                         | |   | | |  __|| |\\/| | `--. \\");
        System.out.println("                        _| |_  | | | |___| |  | |/\\__/ /");
        System.out.println("                        \\___/  \\_/ \\____/\\_|  |_/\\____/ \n");
        System.out.println("===========================================================================");
    }

    public static void allPokemon() {
        System.out.println("\n======================================================================================================================");
        System.out.println("                                                     All Pokemons");
        System.out.println("======================================================================================================================");

    }

    public static void allMoves() {
        System.out.println("\n======================================================================================================================================");
        System.out.println("                                                                 All Moves");
        System.out.println("======================================================================================================================================");
    }

    public static void allItems() {
        System.out.println("\n======================================================================================================================================");
        System.out.println("                                                                 All Items");
        System.out.println("======================================================================================================================================");
    }
}
