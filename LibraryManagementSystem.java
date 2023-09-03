import java.util.*;

class Book {
	private int bookId;
	private String title;
	private String author;
	private int totalCopies;
	private int availCopies;

	public Book(int bookId, String title, String author, int totalCopies) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.totalCopies = totalCopies;
		this.availCopies = totalCopies;
	}

	public int getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public int getAvailCopies() {
		return availCopies;
	}

	public void decreaseAvailCopies() {
		if (availCopies > 0) {
			availCopies--;
		}
	}

	public void increaseAvailableCopies() {
		if (availCopies < totalCopies) {
			availCopies++;
		}
	}
}

class Member {
	private String name;
	private int age;
	private String phoneNumber;
	private List<Integer> borrowedBooks;
	private double penalty;

	public Member(String name, int age, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.borrowedBooks = new ArrayList<>();
		this.penalty = 0.0;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public List<Integer> getBorrowedBooks() {
		return borrowedBooks;
	}

	public boolean canBorrowMoreBooks() {
		return borrowedBooks.size() < 2;
	}

	public double getPenalty() {
		return penalty;
	}

	public void addBorrowedBook(int bookId) {
		borrowedBooks.add(bookId);
	}

	public void removeBorrowedBook(int bookId) {
		borrowedBooks.remove(Integer.valueOf(bookId));
	}

	public void increasePenalty(double amount) {
		penalty += amount;
	}
}

class Library {
	private Map<Integer, Book> books;
	private Map<String, Member> members;
	private Member currentMember;
	private int nextBookId = 1;

	public Library() {
		books = new HashMap<>();
		members = new HashMap<>();
	}

	public void addBook(String title, String author, int totalCopies) {
		int bookId = nextBookId++;
		Book book = new Book(bookId, title, author, totalCopies);
		books.put(bookId, book);
	}

	public boolean removeBook(int bookId) {
		if (books.containsKey(bookId)) {
			Book book = books.get(bookId);
			if (book.getAvailCopies() == book.getTotalCopies()) {
				books.remove(bookId);
				return true;
			}
		}
		return false;
	}

	public void registerMember(String name, int age, String phoneNumber) {
		Member member = new Member(name, age, phoneNumber);
		members.put(phoneNumber, member);
	}

	public boolean removeMember(String phoneNumber) {
		if (members.containsKey(phoneNumber)) {
			Member member = members.get(phoneNumber);
			if (member.getBorrowedBooks().isEmpty()) {
				members.remove(phoneNumber);
				return true;
			}
		}
		return false;
	}

	public String issueBook(int bookId) {
		if (currentMember == null) {
			return "Please log in first.";
		}

		if (currentMember.canBorrowMoreBooks()) {
			return "You have reached the maximum limit of 2 books.";
		}

		if (books.containsKey(bookId)) {
			Book book = books.get(bookId);
			if (book.getAvailCopies() > 0) {
				book.decreaseAvailCopies();
				currentMember.addBorrowedBook(bookId);
				return "Book issued successfully.";
			} else {
				return "No available copies of this book.";
			}
		} else {
			return "Book not found.";
		}
	}

	public String returnBook(int bookId) {
		if (currentMember == null) {
			return "Please log in first.";
		}

		if (books.containsKey(bookId)) {
			Book book = books.get(bookId);
			if (currentMember.getBorrowedBooks().contains(bookId)) {
				book.increaseAvailableCopies();
				currentMember.removeBorrowedBook(bookId);
				double fine = calculateFine(bookId);
				if (fine > 0) {
					currentMember.increasePenalty(fine);
					return "Book returned successfully. Penalty: " + fine + " Rupees.";
				} else {
					return "Book returned successfully.";
				}
			} else {
				return "You did not borrow this book.";
			}
		} else {
			return "Book not found.";
		}
	}

	public double calculateFine(int bookId) {
		Book book = books.get(bookId);
		int daysLate = 10 - book.getAvailCopies();
		if (daysLate > 0) {
			return daysLate * 3.0;
		} else {
			return 0;
		}
	}

	public void viewAllMembersWithBooksAndFines() {
		for (Member member : members.values()) {
			System.out.println("Member Name: " + member.getName());
			System.out.println("Phone Number: " + member.getPhoneNumber());
			System.out.println("Borrowed Books: " + member.getBorrowedBooks());
			System.out.println("Penalty: " + member.getPenalty() + " Rupees");
			System.out.println("--------------------");
		}
	}

	public void viewAllBooks() {
		for (Book book : books.values()) {
			System.out.println("Book ID: " + book.getBookId());
			System.out.println("Title: " + book.getTitle());
			System.out.println("Author: " + book.getAuthor());
			System.out.println("Total Copies: " + book.getTotalCopies());
			System.out.println("Available Copies: " + book.getAvailCopies());
			System.out.println("--------------------");
		}
	}

	public void listAvailableBooks() {
		System.out.println("Available Books:");
		for (Book book : books.values()) {
			if (book.getAvailCopies() > 0) {
				System.out.println("Book ID: " + book.getBookId());
				System.out.println("Title: " + book.getTitle());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("Available Copies: " + book.getAvailCopies());
				System.out.println("--------------------");
			}
		}
	}

	public void listMemberBooks() {
		if (currentMember == null) {
			System.out.println("Please log in first.");
			return;
		}

		System.out.println("Your Borrowed Books:");
		List<Integer> borrowedBooks = currentMember.getBorrowedBooks();
		for (Integer bookId : borrowedBooks) {
			if (books.containsKey(bookId)) {
				Book book = books.get(bookId);
				System.out.println("Book ID: " + book.getBookId());
				System.out.println("Title: " + book.getTitle());
				System.out.println("Author: " + book.getAuthor());
				System.out.println("--------------------");
			}
		}
	}

	public double getCurrentMemberPenalty() {
		if (currentMember == null) {
			System.out.println("Please log in first.");
			return 0.0;
		}

		return currentMember.getPenalty();
	}

	public boolean loginMember(String name, String phoneNumber) {
		if (members.containsKey(phoneNumber)) {
			Member member = members.get(phoneNumber);
			if (member.getName().equals(name)) {
				currentMember = member;
				return true;
			}
		}
		return false;
	}
}

public class LibraryManagementSystem {
	public static void main(String[] args) {
		Library library = new Library();
		Scanner input = new Scanner(System.in);

		System.out.println("Library Portal Initialized....");
		System.out.println("--------------------");

		while (true) {
			System.out.println("1. Enter as a Librarian");
			System.out.println("2. Enter as a Member");
			System.out.println("3. Exit");
			System.out.println("--------------------");
			System.out.print("Enter your choice: ");
			int choice = input.nextInt();
			System.out.println("--------------------");

			if (choice == 1) {
				// Librarian Menu
				librarianMenu(library, input);
			} else if (choice == 2) {
				input.nextLine();
				System.out.print("Enter Your Name: ");
				String name = input.nextLine();
				System.out.print("Enter Your Phone Number: ");
				String phoneNumber = input.nextLine();
				if (library.loginMember(name, phoneNumber)) {
					// Member Menu
					memberMenu(library, input);
				} else {
					System.out
							.println("Member with Name: " + name + " and Phone No: " + phoneNumber + " doesn't exist.");
					System.out.println("--------------------");
				}
			} else if (choice == 3) {
				System.out.println("Thanks for visiting!");
				System.out.println("--------------------");
				input.close();
				System.exit(0);
			} else {
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void librarianMenu(Library library, Scanner input) {
		while (true) {
			System.out.println("1. Register a Member");
			System.out.println("2. Remove a Member");
			System.out.println("3. Add a Book");
			System.out.println("4. Remove a Book");
			System.out.println("5. View all members along with their books and fines to be paid");
			System.out.println("6. View all books");
			System.out.println("7. Back");
			System.out.println("--------------------");
			System.out.print("Enter your choice: ");
			int choice = input.nextInt();

			System.out.println("--------------------");

			switch (choice) {
				case 1:
					input.nextLine();
					System.out.print("Name: ");
					String memberName = input.nextLine();
					System.out.print("Age: ");
					int age = input.nextInt();
					input.nextLine();
					System.out.print("Phone no: ");
					String phoneNumber = input.nextLine();
					library.registerMember(memberName, age, phoneNumber);
					System.out.println("--------------------");
					System.out.println("Member Successfully Registered");
					System.out.println("--------------------");
					break;
				case 2:
					input.nextLine();
					System.out.print("Enter Member's Phone Number to remove: ");
					String phoneNumberToRemove = input.nextLine();
					if (library.removeMember(phoneNumberToRemove)) {
						System.out.println("--------------------");
						System.out.println("Member Successfully Removed");
						System.out.println("--------------------");
					} else {
						System.out.println("--------------------");
						System.out.println("Member removal failed. Make sure there are no borrowed books.");
						System.out.println("--------------------");
					}
					break;
				case 3:
					input.nextLine();
					System.out.print("Enter Title: ");
					String title = input.nextLine();
					System.out.print("Enter Author: ");
					String author = input.nextLine();
					System.out.print("Enter Total Copies: ");
					int totalCopies = input.nextInt();
					library.addBook(title, author, totalCopies);
					System.out.println("--------------------");
					System.out.println("Book added successfully.");
					System.out.println("--------------------");
					break;
				case 4:
					System.out.print("Enter Book ID to remove: ");
					int bookIdToRemove = input.nextInt();
					if (library.removeBook(bookIdToRemove)) {
						System.out.println("Book removed successfully.");
						System.out.println("--------------------");
					} else {
						System.out.println("Book removal failed. Make sure there are no borrowed copies.");
						System.out.println("--------------------");
					}
					break;
				case 5:
					library.viewAllMembersWithBooksAndFines();
					break;
				case 6:
					library.viewAllBooks();
					break;
				case 7:
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
					System.out.println("--------------------");
					break;
			}
		}
	}

	private static void memberMenu(Library library, Scanner input) {
		while (true) {
			System.out.println("1. List Available Books");
			System.out.println("2. List My Books");
			System.out.println("3. Issue Book");
			System.out.println("4. Return Book");
			System.out.println("5. Pay Fine");
			System.out.println("6. Back");
			System.out.println("--------------------");
			System.out.print("Enter your choice: ");
			int choice = input.nextInt();

			System.out.println("--------------------");

			switch (choice) {
				case 1:
					library.listAvailableBooks();
					break;
				case 2:
					library.listMemberBooks();
					break;
				case 3:
					System.out.print("Enter Book ID to borrow: ");
					int bookIdToBorrow = input.nextInt();
					String result = library.issueBook(bookIdToBorrow);
					System.out.println(result);
					break;
				case 4:
					System.out.print("Enter Book ID to return: ");
					int bookIdToReturn = input.nextInt();
					String returnResult = library.returnBook(bookIdToReturn);
					System.out.println(returnResult);
					break;
				case 5:
					System.out.println("Your current penalty amount: " + library.getCurrentMemberPenalty());
					break;
				case 6:
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
			}
		}
	}
}

