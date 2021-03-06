package puw.controller;

import puw.model.Business;
import puw.model.Developer;
import puw.model.Employee;
import puw.model.Profession;
import puw.view.Menu;
import puw.view.MenuItem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private Menu currentMenu;
    private Menu mainMenu;
    private Menu manageMenu;
    private Menu listMenu;
    private Menu filterMenu;
    private Menu projectsMenu;
    private Menu vatsMenu;
    private Scanner scanner;
    private boolean isTheEnd;
    private Business business;

    public Controller() {
        isTheEnd = false;
        scanner = new Scanner(System.in);
        createMenus();
        currentMenu = mainMenu;
        business = new Business(Constants.COMPANY_NAME, Constants.NIP, 0);
    }

    public Controller(Business business) {
        this();
        this.business = business;
    }

    public void start() {
        System.out.println(Constants.WELCOME_MESSAGE);
        if (Files.exists(Path.of(Constants.FILE_NAME))) {
            System.out.println("Wczytuję dane z pliku " + Constants.FILE_NAME);
            readFromFile(new File(Constants.FILE_NAME));
        }
        System.out.println();
        while (!isTheEnd) {
            currentMenu.display();
            System.out.print(Constants.CHOICE_MESSAGE);
            int choice = scanner.nextInt() - 1;
            scanner.nextLine();
            System.out.println();
            currentMenu.getItem(choice).doAction();
        }
    }

    private void createMenus() {
        mainMenu = new Menu();
        manageMenu = new Menu();
        listMenu = new Menu();
        filterMenu = new Menu();
        projectsMenu = new Menu();
        vatsMenu = new Menu();

        mainMenu.addItem(new MenuItem.MenuItemBuilder("Zarządzanie pracownikami")
                .addSubMenu(manageMenu).addAction((o) -> currentMenu = manageMenu).build());
        mainMenu.addItem(new MenuItem.MenuItemBuilder("Lista pracowników")
                .addSubMenu(listMenu).addAction((o) -> currentMenu = listMenu).build());
        mainMenu.addItem(new MenuItem.MenuItemBuilder("Projekty").addSubMenu(projectsMenu)
                .addAction((o) -> currentMenu = projectsMenu).build());
        mainMenu.addItem(new MenuItem.MenuItemBuilder("Faktury").addSubMenu(vatsMenu)
                .addAction((o) -> currentMenu = vatsMenu).build());
        mainMenu.addItem(new MenuItem.MenuItemBuilder("Zakończ program")
                .addAction(this::closeApplication).build());


        manageMenu.addItem(new MenuItem.MenuItemBuilder("Zatrudnij")
                .addAction(this::hireNewEmployee).build());
        manageMenu.addItem(new MenuItem.MenuItemBuilder("Zwolnij")
                .addAction(this::fireEmployee).build());
        manageMenu.addItem(new MenuItem.MenuItemBuilder("Dodaj notatkę")
                .addAction(this::addNoteToEmployee).build());
        manageMenu.addItem(new MenuItem.MenuItemBuilder("Zmień pensję")
                .addAction(this::changeEmployeeSalary).build());
        manageMenu.addItem(new MenuItem.MenuItemBuilder("Wstecz")
                .addAction((o) -> currentMenu = mainMenu).build());

        listMenu.addItem(new MenuItem.MenuItemBuilder("Kompletna")
                .addAction(this::listEmployees).build());
        listMenu.addItem(new MenuItem.MenuItemBuilder("Filtruj").addSubMenu(filterMenu)
                .addAction((o) -> currentMenu = filterMenu).build());
        listMenu.addItem(new MenuItem.MenuItemBuilder("Wstecz")
                .addAction((o) -> currentMenu = mainMenu).build());

        filterMenu.addItem(new MenuItem.MenuItemBuilder("Zawód")
                .addAction(this::filterEmployeesBy).build());
        filterMenu.addItem(new MenuItem.MenuItemBuilder("Pensja")
                .addAction(this::filterEmployeesBy).build());
        filterMenu.addItem(new MenuItem.MenuItemBuilder("Doświadczenie")
                .addAction(this::filterEmployeesBy).build());
        filterMenu.addItem(new MenuItem.MenuItemBuilder("Umiejętności programistów")
                .addAction(this::filterEmployeesBy).build());
        filterMenu.addItem(new MenuItem.MenuItemBuilder("Wstecz")
                .addAction((o) -> currentMenu = listMenu).build());
        filterMenu.addItem(new MenuItem.MenuItemBuilder("Menu główne")
                .addAction((o) -> currentMenu = mainMenu).build());

        projectsMenu.addItem(new MenuItem.MenuItemBuilder("Nowy")
                .addAction(this::addNewProject).build());
        projectsMenu.addItem(new MenuItem.MenuItemBuilder("Lista")
                .addAction(this::listAllProjects).build());
        projectsMenu.addItem(new MenuItem.MenuItemBuilder("Wstecz")
                .addAction((o) -> currentMenu = mainMenu).build());

        vatsMenu.addItem(new MenuItem.MenuItemBuilder("Wystaw")
                .addAction(this::addNewVat).build());
        vatsMenu.addItem(new MenuItem.MenuItemBuilder("Lista")
                .addAction(this::listAllVats).build());
        vatsMenu.addItem(new MenuItem.MenuItemBuilder("Wstecz")
                .addAction((o) -> currentMenu = mainMenu).build());
    }

    private void showEmployeesListMenu(List<Employee> employeeList) {
        Menu listMenu = new Menu();
        Employee employee;
        for (int i = 0; i < employeeList.size(); i++) {
            employee = employeeList.get(i);
            listMenu.addItem(new MenuItem.MenuItemBuilder(employee).build());
        }
        listMenu.addItem(new MenuItem.MenuItemBuilder("Wstecz").build());
        listMenu.display();
    }

    private Employee chooseEmployeeFromListMenu(List<Employee> employeeList) {
        showEmployeesListMenu(employeeList);
        System.out.print(Constants.CHOICE_MESSAGE);
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        if (employeeList.size() >= choice)
            return employeeList.get(--choice);
        else
            return null;
    }

    private void listAllVats(Object object) {
        System.out.println("Do zaimplementowania");
        showContinueMessage();
    }

    private void addNewVat(Object object) {
        System.out.println("Do zaimplementowania");
        showContinueMessage();
    }

    private void listAllProjects(Object object) {
        System.out.println("Do zaimplementowania");
        showContinueMessage();
    }

    private void addNewProject(Object object) {
        System.out.println("Do zaimplementowania");
        showContinueMessage();
    }

    private void filterEmployeesBy(Object object) {
        System.out.println("Do zaimplementowania");
        showContinueMessage();
    }

    private void listEmployees(Object object) {
        Employee employee = chooseEmployeeFromListMenu(business.getEmployees());
        if (employee != null)
            System.out.println(employee.fullInfo());
        showContinueMessage();
    }

    private void changeEmployeeSalary(Object object) {
        Employee employee = chooseEmployeeFromListMenu(business.getEmployees());
        if (employee != null) {
            System.out.println("Nowa pensja: ");
            employee.changeSalary(scanner.nextDouble());
            scanner.nextLine();
        }
        showContinueMessage();
    }

    private void addNoteToEmployee(Object object) {
        Employee employee = chooseEmployeeFromListMenu(business.getEmployees());
        if (employee != null) {
            System.out.print("Notatka: ");
            employee.addNote(scanner.nextLine());
        }
        showContinueMessage();
    }

    private void fireEmployee(Object object) {
        Employee employee = chooseEmployeeFromListMenu(business.getEmployees());
        if (employee != null) {
            business.fireEmployee(employee);
        }
        showContinueMessage();
    }

    private void hireNewEmployee(Object object) {
        for (int i = 0; i < Profession.values().length; i++)
            System.out.printf("%d. %s%n", i + 1, Profession.values()[i]);
        System.out.print("Stanowisko: ");
        Employee employee;
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 5:
                employee = new Developer();
                break;
            default:
                employee = new Employee(Profession.values()[--choice]);
        }
        employee.readFromConsole(scanner);
        business.hireEmployee(employee);
        showContinueMessage();
    }

    private void showContinueMessage() {
        System.out.println(Constants.CONTINUE_MESSAGE);
        scanner.nextLine();
    }

    private void closeApplication(Object object) {
        System.out.print(Constants.EXIT_CONFIRMATION + " (t|n): ");
        char choice = scanner.nextLine().charAt(0);
        if (Character.toLowerCase(choice) == 't') {
            writeToFile(new File(Constants.FILE_NAME));
            scanner.close();
            isTheEnd = true;
        }
    }

    private void writeToFile(File file) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(business);
        } catch (IOException e) {
            System.out.println("Błąd wejścia/wyjścia!");
        }
    }

    private void readFromFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            business = (Business) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Nie można odnaleźć pliku " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Błąd wejścia/wyjścia!");
        } catch (ClassNotFoundException e) {
            System.out.println("Plik w nieznanym formacie!");
        }
    }
}
