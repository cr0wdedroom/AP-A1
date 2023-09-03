I used import java.util.* as it takes all the packages starting with java.util.... in account and you dont have to specify them each.
The program starts with the class Book and its fields and method of the contents like bookId, title, author, totalCopies, availCopies, and them making methods for each of them to call them later from the librarian and member menu that is used in the LibraryManagementSystem public class.
Now coming to Member class using the contents like name, age, phoneNumber, borrowedBooks(which later is used for different purposes like add, remove or even to see if we can borrow a book or not cause we might be exceeding the two books limit for a member) and the penalty. Doing the same as we did in the class Book, made the methods for each of them so as to call them later on.
After then to the class Library, we created books, members, currentMember and nextBookId to count the Id of the following books(that is later used to borrow or return books), 
then creating method for addBook taking the req contents like name, author and copies that the librarian wants to add
then a method for the removal of books that requires bookId for it, this checks if the book is present or not, if yes then it removes the book(s)
the next step registers a member with their name, age and phone number 
then to remove a member, you need to have their name and phone number 
to issue a book, you will need to login first and can only borrow two books atmost cause of the library policy of borrowing books, to issue you just have to provide the bookId of the book
to return a book, it checks if you have penalty or not(if you have exceeded the due date, each day attracts a fine of 3 rupees per day), so it shows the amount of fine that is pending by your side 
the next step calculates the fine for the members who have exceeded the due date limit
the next method shows all the members with their current borrowed book and their penalty that they have to give
viewAllBooks method shows all the books with its id, author, title and available copies
the next method that is viewAvailableBooks, prints the avail books with id, title, author
listMemberBooks provides the books that the member has borrowed with its id, title, author 
the next step checks the login details i.e. member name and phone number 
Now coming to the class LibraryManagementSystem, this class covers the outer interface of the assignment, that is it initialises the library portal and asks for the choice to enter as a librarian or a member, and then gives further choice as their sub-menu, the while loops runs until the user chooses option 3 that exits them out of the system.
Now if the user chooses option 1(Librarian Menu), they then come across different choices like to register a member, remove a member, addition of a book, removal of a book, view all members along with their books and fine to be paid and view all books option
If the user chooses option 2(Member Menu), they then have to login as a member i.e. filling out their name and phone number, then only they can acess the part that shows options like list available books, list my books, issue book, return book and pay the fine that has been upon them.
