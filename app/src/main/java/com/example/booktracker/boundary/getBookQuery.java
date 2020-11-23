package com.example.booktracker.boundary;

import android.content.Context;
import android.net.Uri;

import com.example.booktracker.control.Callback;
import com.example.booktracker.entities.Book;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class getBookQuery extends BookQuery {
    private ArrayList<Book> outputBooks = new ArrayList();
    private Book output;
    private CountDownLatch done = new CountDownLatch(1);
    private boolean isDone = false;
    private BookCollection bookList;
    private Context context;

    /**
     * This will call its parent constructore from BookQuery
     *
     * @param userEmail
     * @param argBookList Book collectoin containing listView
     */
    public getBookQuery(String userEmail, BookCollection argBookList,
                        Context argContext) {
        super(userEmail);
        bookList = argBookList;
        context = argContext;
    }

    /**
     * This constructor will be used for querying a single book
     *
     * @param argContext
     */
    public getBookQuery(Context argContext) {
        super();
        context = argContext;
    }

    /**
     * This will query the data base and use Book Collection object to modify
     * the listView
     *
     * @throws RuntimeException
     */
    public void getMyBooks() throws RuntimeException {
        userDoc.collection("myBooks")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        outputBooks = new ArrayList<>();
                        for (QueryDocumentSnapshot document :
                                Objects.requireNonNull(task.getResult())) {
                            //Book(String argOwner,
                            // List<String>argAuthor, String argTitle,
                            // int argIsbn, String argDesc)
                            List<String> authors =
                                    (List<String>) document.get("author");
                            if (document.get("owner") instanceof String) {
                                String stringOwner =
                                        (String) document.get("owner");
                                Book ogBook = new Book(stringOwner,
                                        authors, (String) document.get(
                                        "title"),
                                        document.getId(),
                                        (String) document.get(
                                                "description"));
                                if (document.get("image_uri") != null) {
                                    Uri imageUri =
                                            Uri.parse((String) document.get(
                                                    "image_uri"));
                                    ogBook.setUri(imageUri.toString());
                                }
                                if (document.get("local_image_uri") != null) {
                                    Uri localImageUri =
                                            Uri.parse((String) document.get(
                                                    "local_image_uri"));
                                    ogBook.setLocalUri(localImageUri.toString());
                                }
                                outputBooks.add(ogBook);
                            } else {
                                HashMap<String, String> owner =
                                        (HashMap<String, String>) document.get("owner");
                                Book book = new Book(owner, authors,
                                        (String) document.get("title"),
                                        document.getId(),
                                        (String) document.get(
                                                "description"));
                                if (document.get("image_uri") != null) {
                                    Uri imageUri =
                                            Uri.parse((String) document.get(
                                                    "image_uri"));
                                    book.setUri(imageUri.toString());
                                }
                                if (document.get("local_image_uri") != null) {
                                    Uri localImageUri =
                                            Uri.parse((String) document.get(
                                                    "local_image_uri"));
                                    book.setLocalUri(localImageUri.toString());
                                }
                                outputBooks.add(book);
                            }
                        }
                        if (outputBooks.size() > 0) {
                            bookList.setBookList(outputBooks);
                            bookList.displayBooks();
                        } else {
                            //in case there no matches clear the current
                            // list
                            bookList.clearList();
                        }
                    } else {
                        throw new RuntimeException("Error getting books");
                    }

                });
    }

    /**
     * This will query the database and use the BookCollection object to
     * modify the listView. A category is specified to get specific list of
     * books.
     *
     * @param category this will be the status of the books
     * @throws RuntimeException
     */
    public void getMyBooks(String category) throws RuntimeException {
        userDoc.collection(category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        outputBooks = new ArrayList<>();
                        for (QueryDocumentSnapshot document :
                                Objects.requireNonNull(task.getResult())) {
                            //Book(String argOwner,
                            // List<String>argAuthor, String argTitle,
                            // int argIsbn, String argDesc)
                            List<String> authors =
                                    (List<String>) document.get("author");
                            if (document.get("owner") instanceof String) {
                                String stringOwner =
                                        (String) document.get("owner");
                                Book ogBook = new Book(stringOwner,
                                        authors, (String) document.get(
                                        "title"),
                                        document.getId(),
                                        (String) document.get(
                                                "description"));
                                if (document.get("image_uri") != null) {
                                    Uri imageUri =
                                            Uri.parse((String) document.get(
                                                    "image_uri"));
                                    ogBook.setUri(imageUri.toString());
                                }
                                if (document.get("local_image_uri") != null) {
                                    Uri localImageUri =
                                            Uri.parse((String) document.get(
                                                    "local_image_uri"));
                                    ogBook.setLocalUri(localImageUri.toString());
                                }
                                outputBooks.add(ogBook);
                            } else {
                                HashMap<String, String> owner =
                                        (HashMap<String, String>) document.get("owner");
                                Book book = new Book(owner, authors,
                                        (String) document.get("title"),
                                        document.getId(),
                                        (String) document.get(
                                                "description"));
                                if (document.get("image_uri") != null) {
                                    Uri imageUri =
                                            Uri.parse((String) document.get(
                                                    "image_uri"));
                                    book.setUri(imageUri.toString());
                                }
                                if (document.get("local_image_uri") != null) {
                                    Uri localImageUri =
                                            Uri.parse((String) document.get(
                                                    "local_image_uri"));
                                    book.setLocalUri(localImageUri.toString());
                                }
                                outputBooks.add(book);
                            }
                        }
                        if (outputBooks.size() > 0) {
                            bookList.setBookList(outputBooks);
                            bookList.displayBooks();
                        } else {
                            //in case there no matches clear the current
                            // list
                            bookList.clearList();
                        }

                    } else {
                        throw new RuntimeException("Error getting books");
                    }

                });
    }

    /**
     * This will fill up the contents of an empty book by querying firestore
     * for a book.
     *
     * @param isbn      isbn of the book were looking for.
     * @param emptyBook Book to be populated.
     * @param callback  callback is a method in the class that called this
     *                  method. Callback must
     *                  contain the code that depends on the result of this
     *                  query.
     * @author <ipenales@ualberta.ca>
     */
    public void getABook(String isbn, Book emptyBook, Callback callback) {
        db.collectionGroup("myBooks").whereEqualTo("isbn", isbn.trim())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> list =
                                Objects.requireNonNull(task.getResult()).getDocuments();
                        if (list.size() > 0) {
                            DocumentSnapshot res = list.get(0);
                            if (res != null) {
                                if (res.get("image_uri") != null) {
                                    Uri imageUri =
                                            Uri.parse((String) res.get(
                                                    "image_uri"));
                                    emptyBook.setUri(imageUri.toString());
                                }
                                if (res.get("local_image_uri") != null) {
                                    Uri localImageUri =
                                            Uri.parse((String) res.get(
                                                    "local_image_uri"));
                                    emptyBook.setLocalUri(localImageUri.toString());
                                }
                                List<String> authors =
                                        (List<String>) res.get("author");

                                if (res.get("owner") instanceof String) {
                                    String stringOwner =
                                            (String) res.get("owner");
                                    emptyBook.setStringOwner(stringOwner);
                                } else {
                                    HashMap<String, String> owner =
                                            (HashMap<String, String>) res.get("owner");
                                    emptyBook.setOwner(owner);
                                }
                                emptyBook.setAuthor(authors);
                                emptyBook.setIsbn(isbn);
                                emptyBook.setBorrower("none");
                                emptyBook.setTitle((String) res.get(
                                        "title"));
                                emptyBook.setDescription((String) res.get(
                                        "description"));
                                emptyBook.setStatus((String) res.get(
                                        "status"));
                                callback.executeCallback();
                            }
                        }
                    }
                });
    }
}